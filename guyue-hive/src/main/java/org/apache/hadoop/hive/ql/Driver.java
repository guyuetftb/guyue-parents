/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.hive.common.ValidTxnList;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.conf.HiveVariableSource;
import org.apache.hadoop.hive.conf.VariableSubstitution;
import org.apache.hadoop.hive.metastore.HiveMetaStore;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.exec.*;
import org.apache.hadoop.hive.ql.exec.tez.TezContext;
import org.apache.hadoop.hive.ql.exec.tez.TezSessionState;
import org.apache.hadoop.hive.ql.gy.auth.UserAuthDataMode;
import org.apache.hadoop.hive.ql.gy.logs.LogUtil;
import org.apache.hadoop.hive.ql.history.HiveHistory.Keys;
import org.apache.hadoop.hive.ql.hooks.Entity;
import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.Hook;
import org.apache.hadoop.hive.ql.hooks.HookContext;
import org.apache.hadoop.hive.ql.hooks.HookUtils;
import org.apache.hadoop.hive.ql.hooks.PostExecute;
import org.apache.hadoop.hive.ql.hooks.PreExecute;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.lockmgr.HiveLock;
import org.apache.hadoop.hive.ql.lockmgr.HiveTxnManager;
import org.apache.hadoop.hive.ql.lockmgr.LockException;
import org.apache.hadoop.hive.ql.log.PerfLogger;
import org.apache.hadoop.hive.ql.metadata.AuthorizationException;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.Partition;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.metadata.formatting.JsonMetaDataFormatter;
import org.apache.hadoop.hive.ql.metadata.formatting.MetaDataFormatUtils;
import org.apache.hadoop.hive.ql.metadata.formatting.MetaDataFormatter;
import org.apache.hadoop.hive.ql.optimizer.ppr.PartitionPruner;
import org.apache.hadoop.hive.ql.parse.*;
import org.apache.hadoop.hive.ql.parse.ExplainConfiguration.AnalyzeState;
import org.apache.hadoop.hive.ql.plan.*;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.security.authorization.AuthorizationUtils;
import org.apache.hadoop.hive.ql.security.authorization.HiveAuthorizationProvider;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveOperationType;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HivePrivilegeObject;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HivePrivilegeObject.HivePrivObjectActionType;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HivePrivilegeObject.HivePrivilegeObjectType;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzContext;
import org.apache.hadoop.hive.ql.session.OperationLog;
import org.apache.hadoop.hive.ql.session.OperationLog.LoggingLevel;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.hive.serde2.ByteStream;
import org.apache.hadoop.hive.shims.Utils;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hive.common.util.ShutdownHookManager;
import org.apache.hive.service.cli.TableSchema;
import org.apache.tez.client.TezClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class Driver implements CommandProcessor {

    private static final String CLASS_NAME = Driver.class.getName();

    static final String LOG_GY_PREFIX = " MY_TEST .... ";
    static final String LOG_GY_BEGIN  = " Beginninggggggggggg ";
    static final String LOG_GY_END    = " Endingggggggggggggg ";

    // 2018-05-03 by lipeng
    private String username = "";
    private String password = "";

    private static final Logger    LOG                    = LoggerFactory.getLogger(CLASS_NAME);
    private static final Logger    GUYUE_COMMAND_LOGGER = LoggerFactory.getLogger("GUYUE_COMMAND_LOGGER");
    private static final LogHelper console                = new LogHelper(LOG);
    static final         int       SHUTDOWN_HOOK_PRIORITY = 0;
    private              Runnable  shutdownRunner         = null;

    private int maxRows = 100;
    ByteStream.Output bos = new ByteStream.Output();

    private final HiveConf      conf;
    private final boolean       isParallelEnabled;
    private       DataInput     resStream;
    private       Context       ctx;
    private       DriverContext driverCxt;
    private       QueryPlan     plan;
    private       Schema        schema;
    private       String        errorMessage;
    private       String        SQLState;
    private       Throwable     downstreamError;

    private FetchTask fetchTask;
    List<HiveLock> hiveLocks = new ArrayList<HiveLock>();

    // A list of FileSinkOperators writing in an ACID compliant manner
    private Set<FileSinkDesc> acidSinks;
    // whether any ACID table is involved in a query
    private boolean           acidInQuery;

    // A limit on the number of threads that can be launched
    private int maxthreads;
    private int tryCount = Integer.MAX_VALUE;

    private String userName;

    // HS2 operation handle guid string
    private String operationId;

    // For WebUI.  Kept alive after queryPlan is freed.
    private final QueryDisplay queryDisplay = new QueryDisplay();

    // Query specific info
    private QueryState queryState;

    // a lock is used for synchronizing the state transition and its associated
    // resource releases
    private final ReentrantLock stateLock   = new ReentrantLock();
    private       DriverState   driverState = DriverState.INITIALIZED;

    private enum DriverState {
        INITIALIZED,
        COMPILING,
        COMPILED,
        EXECUTING,
        EXECUTED,
        // a state that the driver enters after close() has been called to interrupt its running
        // query in the query cancellation
        INTERRUPT,
        // a state that the driver enters after close() has been called to clean the query results
        // and release the resources after the query has been executed
        CLOSED,
        // a state that the driver enters after destroy() is called and it is the end of driver life
        // cycle
        DESTROYED,
        ERROR
    }

    private boolean checkConcurrency() {
        boolean supportConcurrency = conf.getBoolVar(HiveConf.ConfVars.HIVE_SUPPORT_CONCURRENCY);
        if (!supportConcurrency) {
            LOG.info(LOG_GY_PREFIX + " \tcheckConcurrency() Concurrency mode is disabled, not creating a lock manager");
            return false;
        }
        return true;
    }

    @Override
    public void init() {
        // Nothing for now.
    }

    /**
     * Return the status information about the Map-Reduce cluster
     */
    public ClusterStatus getClusterStatus() throws Exception {
        ClusterStatus cs;
        try {
            JobConf job = new JobConf(conf);
            JobClient jc = new JobClient(job);
            cs = jc.getClusterStatus();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        LOG.info("Returning cluster status: " + cs.toString());
        return cs;
    }

    public Schema getSchema() {
        return schema;
    }

    /**
     * Get a Schema with fields represented with native Hive types
     */
    private static Schema getSchema(BaseSemanticAnalyzer sem,
                                    HiveConf conf) {
        Schema schema = null;

        // If we have a plan, prefer its logical result schema if it's
        // available; otherwise, try digging out a fetch task; failing that,
        // give up.
        if (sem == null) {
            // can't get any info without a plan
        } else if (sem.getResultSchema() != null) {
            List<FieldSchema> lst = sem.getResultSchema();
            schema = new Schema(lst, null);
        } else if (sem.getFetchTask() != null) {
            FetchTask ft = sem.getFetchTask();
            TableDesc td = ft.getTblDesc();
            // partitioned tables don't have tableDesc set on the FetchTask. Instead
            // they have a list of PartitionDesc objects, each with a table desc.
            // Let's
            // try to fetch the desc for the first partition and use it's
            // deserializer.
            if (td == null && ft.getWork() != null && ft.getWork().getPartDesc() != null) {
                if (ft.getWork().getPartDesc().size() > 0) {
                    td = ft.getWork().getPartDesc().get(0).getTableDesc();
                }
            }

            if (td == null) {
                LOG.info("No returning schema.");
            } else {
                String tableName = "result";
                List<FieldSchema> lst = null;
                try {
                    lst = MetaStoreUtils.getFieldsFromDeserializer(tableName, td.getDeserializer(conf));
                } catch (Exception e) {
                    LOG.warn("Error getting schema: " + org.apache.hadoop.util.StringUtils.stringifyException(e));
                }
                if (lst != null) {
                    schema = new Schema(lst, null);
                }
            }
        }
        if (schema == null) {
            schema = new Schema();
        }
        LOG.info(LOG_GY_PREFIX + " \t Returning Hive schema: " + schema.getFieldSchemas());
        return schema;
    }

    /**
     * Get a Schema with fields represented with Thrift DDL types
     */
    public Schema getThriftSchema() {
        Schema schema;
        try {
            schema = getSchema();
            if (schema != null) {
                List<FieldSchema> lst = schema.getFieldSchemas();
                // Go over the schema and convert type to thrift type
                if (lst != null) {
                    for (FieldSchema f : lst) {
                        f.setType(MetaStoreUtils.typeToThriftType(f.getType()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        LOG.info("Returning Thrift schema: " + schema);
        return schema;
    }

    /**
     * Return the maximum number of rows returned by getResults
     */
    public int getMaxRows() {
        return maxRows;
    }

    /**
     * Set the maximum number of rows returned by getResults
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public Driver() {
        this(new QueryState((SessionState.get() != null) ? SessionState.get().getConf() : new HiveConf()), null);
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 1 Driver() ");
    }

    public Driver(HiveConf conf) {
        this(new QueryState(conf), null);
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 2 Driver(HiveConf) ");
    }

    public Driver(HiveConf conf,
                  Context ctx) {
        this(new QueryState(conf), null);
        this.ctx = ctx;
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 3 Driver(HiveConf, Context) conf = " + conf.getQueryString()
                         + ", userName = null ");
    }

    public Driver(HiveConf conf,
                  String userName) {
        this(new QueryState(conf), userName);
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 4 Driver(HiveConf, String) conf = " + conf.getQueryString()
                         + ", userName = " + userName);
    }

    public Driver(QueryState queryState,
                  String userName) {
        this.queryState = queryState;
        this.conf = queryState.getConf();
        isParallelEnabled = (conf != null) && HiveConf.getBoolVar(conf, ConfVars.HIVE_SERVER2_PARALLEL_COMPILATION);
        this.userName = userName;
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 5 Driver(QueryState, String) userName = " + userName);
    }

    /**
     * Compile a new query. Any currently-planned query associated with this Driver is discarded. Do
     * not reset id for inner queries(index, etc). Task ids are used for task identity check.
     *
     * @param command The SQL query to compile.
     */
    public int compile(String command) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 1 compile(String) command = " + command);
        int res = compile(command, true);
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 1 compile(String) command = " + command);
        return res;
    }

    /**
     * Compile a new query, but potentially reset taskID counter. Not resetting task counter is useful
     * for generating re-entrant QL queries.
     *
     * @param command      The HiveQL query to compile
     * @param resetTaskIds Resets taskID counter if true.
     *
     * @return 0 for ok
     */
    public int compile(String command,
                       boolean resetTaskIds) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 2 compile(String,boolean) command = " + command + ", resetTaskIds = "
                         + resetTaskIds);
        int res = compile(command, resetTaskIds, false);
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 2 compile(String,boolean) command = " + command + ", resetTaskIds = "
                         + resetTaskIds);
        return res;
    }

    // deferClose indicates if the close/destroy should be deferred when the process has been
    // interrupted, it should be set to true if the compile is called within another method like
    // runInternal, which defers the close to the called in that method.<br/>
    // deferClose 表示当进程补中断，是否延时调用 close/destory方法, <br/>
    // 当在另一个像runInternal方法中调用compile方法时应该将值设置为true, 该方法将延迟调用close/destory.
    public int compile(String command,
                       boolean resetTaskIds,
                       boolean deferClose) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " compile(String,boolean,boolean) ");
        LOG.info(LOG_GY_PREFIX + " \t ##### compile(String,boolean,boolean) command = " + command + ", exec.model = " + conf.getVar(ConfVars.GUYUE_EXEC_MODEL));
        LOG.info(LOG_GY_PREFIX + " \t ##### compile(String,boolean,boolean) command = " + command + ", command.monitoring = " + conf.getVar(ConfVars.GUYUE_COMMAND_MONITORING));
        LOG.info(LOG_GY_PREFIX + " \t ##### ");
        LOG.info(LOG_GY_PREFIX + " \t ##### ");
        LOG.info(LOG_GY_PREFIX + " \t ##### ");
        LOG.info(LOG_GY_PREFIX + " \t ##### ");
        LOG.info(LOG_GY_PREFIX + " \t 1 compile(String,boolean,boolean) command = " + command + ", resetTaskIds = " + resetTaskIds + ", deferClose=" + deferClose);
        PerfLogger perfLogger = SessionState.getPerfLogger(true);
        perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.DRIVER_RUN);
        perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.COMPILE);
        stateLock.lock();
        try {
            LOG.info(LOG_GY_PREFIX + " \t 1-1 compile(String,boolean,boolean) 1-1  driverState = " + driverState);
            driverState = DriverState.COMPILING;
            LOG.info(LOG_GY_PREFIX + " \t 1-2 compile(String,boolean,boolean) 1-2  driverState = " + driverState);
        } finally {
            stateLock.unlock();
        }

        LOG.info(LOG_GY_PREFIX + " \t 2 compile(String,boolean,boolean) command = " + command);
        command = new VariableSubstitution(new HiveVariableSource() {

            @Override
            public Map<String, String> getHiveVariable() {
                return SessionState.get().getHiveVariables();
            }
        }).substitute(conf, command);

        String queryStr = command;
        LOG.info(LOG_GY_PREFIX + " \t 3 compile(String,boolean,boolean) command = " + queryStr);

        try {
            // command should be redacted to avoid to logging sensitive data
            // 命令应该被编辑,避免日志中记录了敏感信息
            queryStr = HookUtils.redactLogString(conf, command);
        } catch (Exception e) {
            LOG.warn("WARNING! Query command could not be redacted." + e);
        }

        if (ctx != null && ctx.getExplainAnalyze() != AnalyzeState.RUNNING) {
            closeInProcess(false);
        }
        if (isInterrupted()) {
            return handleInterruption("at beginning of compilation."); // indicate if need clean resource
        }

        if (resetTaskIds) {
            TaskFactory.resetId();
        }

        String queryId = conf.getVar(HiveConf.ConfVars.HIVEQUERYID);
        LOG.info(LOG_GY_PREFIX + " \t 3-1 compile(String,boolean,boolean) queryId = " + queryId);

        // save some info for webUI for use after plan is freed
        // 保存一些信息，为了 webUI 在 plan被释放后 还可以使用.
        this.queryDisplay.setQueryStr(queryStr);
        this.queryDisplay.setQueryId(queryId);

        LOG.info(LOG_GY_PREFIX + " \t 3-2 compile(String,boolean,boolean) Compiling command(queryId=" + queryId + "): "
                         + queryStr);

        SessionState.get().setupQueryCurrentTimestamp();

        boolean compileError = false;
        try {
            // Initialize the transaction manager.  This must be done before analyze is called.
            // 在调用 analyze(分析)函数之前，必须先初始化 Transaction-Manager.
            final HiveTxnManager txnManager = SessionState.get().initTxnMgr(conf);
            // In case when user Ctrl-C twice to kill Hive CLI JVM, we want to release locks
            // 假如用户按了2次 Ctrl-C 关掉了 Hive-ClI JVM，我们需要释放锁.

            // if compile is being called multiple times, clear the old shutdownhook
            // 如果编译被调用了多次, 清理 旧的 shutdown-钩子函数
            ShutdownHookManager.removeShutdownHook(shutdownRunner);
            shutdownRunner = new Runnable() {

                @Override
                public void run() {
                    try {
                        releaseLocksAndCommitOrRollback(false, txnManager);
                    } catch (LockException e) {
                        LOG.warn("Exception when releasing locks in ShutdownHook for Driver: " + e.getMessage());
                    }
                }
            };
            ShutdownHookManager.addShutdownHook(shutdownRunner, SHUTDOWN_HOOK_PRIORITY);

            if (isInterrupted()) {
                return handleInterruption("before parsing and analysing the query");
            }

            if (ctx == null) {
                ctx = new Context(conf);
            }

            ctx.setTryCount(getTryCount());
            ctx.setCmd(command);
            ctx.setHDFSCleanup(true);

            perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.PARSE);
            LOG.info(LOG_GY_PREFIX + " \t 4-1 compile(String,boolean,boolean) ++++++++++++++++++++++++++++++++++++++ ");

            // sql 解析成 AST
            ASTNode tree = ParseUtils.parse(command, ctx);

            LOG.info(LOG_GY_PREFIX + " \t 4-2 compile(String,boolean,boolean) ++++++++++++++++++++++++++++++++++++++ ");
            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.PARSE);

            // 初始化语义分析器.
            perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.ANALYZE);
            BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);

            // google-翻译
            // HiveSemanticAnalyzerHook允许Hive通过自定义逻辑进行扩展，以便对QL语句进行语义分析。
            // 这个接口和它公开的任何Hive内部组件目前都是“有限的私有的和不断发展的”（除非另有说明），
            // 主要用于哈尔项目。
            List<HiveSemanticAnalyzerHook> saHooks = getHooks(HiveConf.ConfVars.SEMANTIC_ANALYZER_HOOK,
                                                              HiveSemanticAnalyzerHook.class);

            // Flush the metastore cache.  This assures that we don't pick up objects from a previous
            // query running in this same thread.  This has to be done after we get our semantic
            // analyzer (this is when the connection to the metastore is made) but before we analyze,
            // because at that point we need access to the objects.
            // 刷新 metastore的缓存. 这个保证不获得同一个线程 之前运行的 Query 留下的 object 对象。
            // 在获得 semantic之后，开始分析之前，必须进行此操作。因为那时我们需要访问这些对象。
            Hive.get().getMSC().flushCache();

            // Do semantic analysis and plan generation
            // 进行语义分析 生成 执行计划.
            if (saHooks != null && !saHooks.isEmpty()) {
                LOG.info(LOG_GY_PREFIX + " \t 4 compile(String,boolean,boolean) semantic. saHooks != null ");
                HiveSemanticAnalyzerHookContext hookCtx = new HiveSemanticAnalyzerHookContextImpl();
                hookCtx.setConf(conf);
                hookCtx.setUserName(userName);
                hookCtx.setIpAddress(SessionState.get().getUserIpAddress());
                hookCtx.setCommand(command);
                for (HiveSemanticAnalyzerHook hook : saHooks) {
                    tree = hook.preAnalyze(hookCtx, tree);
                }
                // 进行语法转义分析, 调用BaseSemanticAnalyzer.analyze
                sem.analyze(tree, ctx);
                hookCtx.update(sem);
                for (HiveSemanticAnalyzerHook hook : saHooks) {
                    hook.postAnalyze(hookCtx, sem.getAllRootTasks());
                }
            } else {
                LOG.info(LOG_GY_PREFIX + " \t 5 compile(String,boolean,boolean) saHooks == null ");
                // 进行语法转义分析, 调用BaseSemanticAnalyzer.analyze
                sem.analyze(tree, ctx);
            }
            // Record any ACID compliant FileSinkOperators we saw so we can add our transaction ID to
            // them later.
            acidSinks = sem.getAcidFileSinks();

            LOG.info(LOG_GY_PREFIX + " \t 6 compile(String,boolean,boolean) Semantic Analysis Completed. GuYue. ");

            // validate the plan
            sem.validate();
            acidInQuery = sem.hasAcidInQuery();
            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.ANALYZE);

            if (isInterrupted()) {
                return handleInterruption("after analyzing query.");
            }

            // get the output schema
            schema = getSchema(sem, conf);
            plan = new QueryPlan(queryStr,
                                 sem,
                                 perfLogger.getStartTime(PerfLogger.DRIVER_RUN),
                                 queryId,
                                 queryState.getHiveOperation(),
                                 schema);

            conf.setQueryString(queryStr);

            conf.set("mapreduce.workflow.id", "hive_" + queryId);
            conf.set("mapreduce.workflow.name", queryStr);

            // initialize FetchTask right here
            if (plan.getFetchTask() != null) {
                plan.getFetchTask().initialize(queryState, plan, null, ctx.getOpContext());
            }

            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) command = " + command);
            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) queryState = " + queryState.toString());
            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) commandType = " + queryState.getCommandType());
            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) QueryId = " + queryState.getQueryId());
            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) QueryString = " + queryState.getQueryString());
            LOG.info(LOG_GY_PREFIX + " \t compile(String,boolean,boolean) HiveOperation = " + queryState.getHiveOperation());

            try {
                perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.DO_AUTHORIZATION);
                doAuthorization(queryState.getHiveOperation(), sem, command);
            } catch (AuthorizationException authExp) {
                console.printError(
                        "Authorization failed:" + authExp.getMessage() + ". Use SHOW GRANT to get more details.");
                errorMessage = authExp.getMessage();
                SQLState = "42000";
                return 403;
            } finally {
                perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.DO_AUTHORIZATION);
            }

            try {
                // TODO 2018-07-10
                // 2018-05-18 by lipeng
                LogUtil.logUserAction("1", this.conf, this, queryState, command, "Local_Mode", "runInternal", 0);
            } catch (Exception e) {
                GUYUE_COMMAND_LOGGER.error(" Writern Log Error : ", e.getStackTrace());
                LOG.error(" Writern Log Error : ", e.getStackTrace());
            }

            // 2018-05-08 by lipeng. don't run sql, output explain only.
            if ("explain".equalsIgnoreCase(HiveConf.getVar(this.conf, ConfVars.GUYUE_EXEC_MODEL))) {
                // TODO 2018-07-10
                // do Explain
                LogUtil.doExplain(queryState, sem, command);
                return 0;
            }

            // TODO 2018-05-08 by lipeng. guyue Auth Properties.
            if (HiveConf.getBoolVar(this.conf, ConfVars.GUYUE_AUTHORIZATION_ENABLED)) {
                try {
                    // TODO 2018-07-10
                    doAuthorizationExtend(command, sem);
                } catch (AuthorizationException authExp) {
                    errorMessage = "FAILED:Guyue Authorization failed:" + authExp.getMessage()
                            + " Please contact blg-team-dataman@guyuecn for your information.";
                    console.printError("Guyue Authorization failed:" + authExp.getMessage()
                                               + " Please contact blg-team-dataman@guyuecn for your information.");
                    return 403;
                }
            }

            if (conf.getBoolVar(ConfVars.HIVE_LOG_EXPLAIN_OUTPUT)) {
                String explainOutput = getExplainOutput(sem, plan, tree);
                LOG.info(LOG_GY_PREFIX + " \tcompile(String,boolean,boolean) explainOutput = " + explainOutput);

                if (explainOutput != null) {
                    if (conf.getBoolVar(ConfVars.HIVE_LOG_EXPLAIN_OUTPUT)) {
                        LOG.info("EXPLAIN output for queryid " + queryId + " : " + explainOutput);
                    }
                    if (conf.isWebUiQueryInfoCacheEnabled()) {
                        queryDisplay.setExplainPlan(explainOutput);
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            if (isInterrupted()) {
                return handleInterruption("during query compilation: " + e.getMessage());
            }
            compileError = true;
            ErrorMsg error = ErrorMsg.getErrorMsg(e.getMessage());
            errorMessage = "FAILED: " + e.getClass().getSimpleName();
            if (error != ErrorMsg.GENERIC_ERROR) {
                errorMessage += " [Error " + error.getErrorCode() + "]:";
            }

            // HIVE-4889
            if ((e instanceof IllegalArgumentException) && e.getMessage() == null && e.getCause() != null) {
                errorMessage += " " + e.getCause().getMessage();
            } else {
                errorMessage += " " + e.getMessage();
            }

            if (error == ErrorMsg.TXNMGR_NOT_ACID) {
                errorMessage += ". Failed command: " + queryStr;
            }

            SQLState = error.getSQLState();
            downstreamError = e;
            console.printError(errorMessage, "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
            return error.getErrorCode(); // todo: this is bad if returned as cmd shell exit
            // since it exceeds valid range of shell return values
        } finally {
            double duration = perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.COMPILE) / 1000.00;
            ImmutableMap<String, Long> compileHMSTimings = dumpMetaCallTimingWithoutEx("compilation");
            queryDisplay.setHmsTimings(QueryDisplay.Phase.COMPILATION, compileHMSTimings);

            boolean isInterrupted = isInterrupted();
            if (isInterrupted && !deferClose) {
                closeInProcess(true);
            }
            stateLock.lock();
            try {
                if (isInterrupted) {
                    driverState = deferClose ? DriverState.EXECUTING : DriverState.ERROR;
                } else {
                    driverState = compileError ? DriverState.ERROR : DriverState.COMPILED;
                }
            } finally {
                stateLock.unlock();
            }

            if (isInterrupted) {
                LOG.info(LOG_GY_PREFIX + " \tcompile(String,boolean,boolean) Compiling command(queryId=" + queryId
                                 + ") has been interrupted after " + duration + " seconds");
            } else {
                LOG.info(LOG_GY_PREFIX + " \tcompile(String,boolean,boolean) Completed compiling command(queryId="
                                 + queryId + "); Time taken: " + duration + " seconds");
            }
        }
    }

    private int handleInterruption(String msg) {
        SQLState = "HY008"; // SQLState for cancel operation
        errorMessage = "FAILED: command has been interrupted: " + msg;
        console.printError(errorMessage);
        return 1000;
    }

    private boolean isInterrupted() {
        stateLock.lock();
        try {
            if (driverState == DriverState.INTERRUPT) {
                Thread.currentThread().interrupt();
                return true;
            } else {
                return false;
            }
        } finally {
            stateLock.unlock();
        }
    }

    private ImmutableMap<String, Long> dumpMetaCallTimingWithoutEx(String phase) {
        try {
            return Hive.get().dumpAndClearMetaCallTiming(phase);
        } catch (HiveException he) {
            LOG.warn("Caught exception attempting to write metadata call information " + he, he);
        }
        return null;
    }

    /**
     * Returns EXPLAIN EXTENDED output for a semantically analyzed query.
     *
     * @param sem     semantic analyzer for analyzed query
     * @param plan    query plan
     * @param astTree AST tree dump
     *
     * @throws java.io.IOException
     */
    private String getExplainOutput(BaseSemanticAnalyzer sem,
                                    QueryPlan plan,
                                    ASTNode astTree) {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " getExplainOutput(BaseSemanticAnalyzer, QueryPlan, ASTNode) ");

        String ret = null;
        ExplainTask task = new ExplainTask();
        task.initialize(queryState, plan, null, ctx.getOpContext());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            List<Task<?>> rootTasks = sem.getAllRootTasks();
            task.getJSONPlan(ps, rootTasks, sem.getFetchTask(), false, true, true);
            ret = baos.toString();
        } catch (Exception e) {
            LOG.warn("Exception generating explain output: " + e, e);
        }

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " getExplainOutput(BaseSemanticAnalyzer, QueryPlan, ASTNode) ret = "
                         + ret);
        return ret;
    }

    /**
     * Do authorization using post semantic analysis information in the semantic analyzer The original
     * command is also passed so that authorization interface can provide more useful information in
     * logs.
     *
     * @param sem     SemanticAnalyzer used to parse input query
     * @param command input query
     *
     * @throws HiveException
     * @throws AuthorizationException
     */
    public static void doAuthorization(HiveOperation op,
                                       BaseSemanticAnalyzer sem,
                                       String command)
            throws HiveException, AuthorizationException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) ");
        SessionState ss = SessionState.get();
        Hive db = sem.getDb();
        LOG.info(LOG_GY_PREFIX
                         + " \t 1 doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) db.getDatabaseCurrent = "
                         + db.getDatabaseCurrent());

        Set<ReadEntity> additionalInputs = new HashSet<ReadEntity>();
        for (Entity e : sem.getInputs()) {
            if (e.getType() == Entity.Type.PARTITION) {
                try {
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.location = "
                                     + e.getLocation().toString());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getD = "
                                     + e.getD().toString());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getT = "
                                     + e.getT());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getTable = "
                                     + e.getTable());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getDatabase = "
                                     + e.getDatabase());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getPartition = "
                                     + e.getPartition());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getCompleteName = "
                                     + e.getTable().getCompleteName());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getPartCols = "
                                     + e.getTable().getPartCols());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getPartColNames = "
                                     + e.getTable().getPartColNames());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getCols = "
                                     + e.getTable().getCols());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getOwner = " + e
                            .getTable()
                            .getOwner());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getDbName = "
                                     + e.getTable().getDbName());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getPartitionKeys = "
                                     + e.getTable().getPartitionKeys());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getOutputFormatClass = "
                                     + e.getTable().getOutputFormatClass());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getNumBuckets = "
                                     + e.getTable().getNumBuckets());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getDataLocation = "
                                     + e.getTable().getDataLocation());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getCreateTime = "
                                     + e.getTable().getCreateTime());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getInputFormatClass = "
                                     + e.getTable().getInputFormatClass());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getViewExpandedText = "
                                     + e.getTable().getViewExpandedText());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getViewOriginalText = "
                                     + e.getTable().getViewOriginalText());
                    LOG.info(LOG_GY_PREFIX
                                     + " \t\t\t -doAuthorization(HiveOperation, BaseSemanticAnalyzer, String) e.getLastAccessTime = "
                                     + e.getTable().getLastAccessTime());
                    additionalInputs.add(new ReadEntity(e.getTable()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        Set<WriteEntity> additionalOutputs = new HashSet<WriteEntity>();
        for (WriteEntity e : sem.getOutputs()) {
            if (e.getType() == Entity.Type.PARTITION) {
                additionalOutputs.add(new WriteEntity(e.getTable(), e.getWriteType()));
            }
        }

        // The following union operation returns a union, which traverses over the
        // first set once and then  then over each element of second set, in order,
        // that is not contained in first. This means it doesn't replace anything
        // in first set, and would preserve the WriteType in WriteEntity in first
        // set in case of outputs list.
        Set<ReadEntity> inputs = Sets.union(sem.getInputs(), additionalInputs);
        Set<WriteEntity> outputs = Sets.union(sem.getOutputs(), additionalOutputs);

        if (ss.isAuthorizationModeV2()) {
            // get mapping of tables to columns used
            ColumnAccessInfo colAccessInfo = sem.getColumnAccessInfo();
            // colAccessInfo is set only in case of SemanticAnalyzer
            Map<String, List<String>> selectTab2Cols =
                    colAccessInfo != null ? colAccessInfo.getTableToColumnAccessMap() : null;
            Map<String, List<String>> updateTab2Cols =
                    sem.getUpdateColumnAccessInfo() != null ? sem.getUpdateColumnAccessInfo()
                            .getTableToColumnAccessMap() : null;
            doAuthorizationV2(ss, op, inputs, outputs, command, selectTab2Cols, updateTab2Cols);
            return;
        }
        if (op == null) {
            throw new HiveException("Operation should not be null");
        }
        HiveAuthorizationProvider authorizer = ss.getAuthorizer();
        if (op.equals(HiveOperation.CREATEDATABASE)) {
            authorizer.authorize(op.getInputRequiredPrivileges(), op.getOutputRequiredPrivileges());
        } else if (op.equals(HiveOperation.CREATETABLE_AS_SELECT) || op.equals(HiveOperation.CREATETABLE)) {
            authorizer.authorize(db.getDatabase(SessionState.get().getCurrentDatabase()),
                                 null,
                                 HiveOperation.CREATETABLE_AS_SELECT.getOutputRequiredPrivileges());
        } else {
            if (op.equals(HiveOperation.IMPORT)) {
                ImportSemanticAnalyzer isa = (ImportSemanticAnalyzer) sem;
                if (!isa.existsTable()) {
                    authorizer.authorize(db.getDatabase(SessionState.get().getCurrentDatabase()),
                                         null,
                                         HiveOperation.CREATETABLE_AS_SELECT.getOutputRequiredPrivileges());
                }
            }
        }
        if (outputs != null && outputs.size() > 0) {
            for (WriteEntity write : outputs) {
                if (write.isDummy() || write.isPathType()) {
                    continue;
                }
                if (write.getType() == Entity.Type.DATABASE) {
                    if (!op.equals(HiveOperation.IMPORT)) {
                        // We skip DB check for import here because we already handle it above
                        // as a CTAS check.
                        authorizer.authorize(write.getDatabase(), null, op.getOutputRequiredPrivileges());
                    }
                    continue;
                }

                if (write.getType() == WriteEntity.Type.PARTITION) {
                    Partition part = db.getPartition(write.getTable(), write.getPartition().getSpec(), false);
                    if (part != null) {
                        authorizer.authorize(write.getPartition(), null, op.getOutputRequiredPrivileges());
                        continue;
                    }
                }

                if (write.getTable() != null) {
                    authorizer.authorize(write.getTable(), null, op.getOutputRequiredPrivileges());
                }
            }
        }

        if (inputs != null && inputs.size() > 0) {
            Map<Table, List<String>> tab2Cols = new HashMap<Table, List<String>>();
            Map<Partition, List<String>> part2Cols = new HashMap<Partition, List<String>>();

            // determine if partition level privileges should be checked for input tables
            Map<String, Boolean> tableUsePartLevelAuth = new HashMap<String, Boolean>();
            for (ReadEntity read : inputs) {
                if (read.isDummy() || read.isPathType() || read.getType() == Entity.Type.DATABASE) {
                    continue;
                }
                Table tbl = read.getTable();
                if ((read.getPartition() != null) || (tbl != null && tbl.isPartitioned())) {
                    String tblName = tbl.getTableName();
                    if (tableUsePartLevelAuth.get(tblName) == null) {
                        boolean usePartLevelPriv = (tbl.getParameters().get("PARTITION_LEVEL_PRIVILEGE") != null
                                && ("TRUE".equalsIgnoreCase(tbl.getParameters()
                                                                    .get("PARTITION_LEVEL_PRIVILEGE"))));
                        if (usePartLevelPriv) {
                            tableUsePartLevelAuth.put(tblName, Boolean.TRUE);
                        } else {
                            tableUsePartLevelAuth.put(tblName, Boolean.FALSE);
                        }
                    }
                }
            }

            // column authorization is checked through table scan operators.
            getTablePartitionUsedColumns(op, sem, tab2Cols, part2Cols, tableUsePartLevelAuth);

            // cache the results for table authorization
            Set<String> tableAuthChecked = new HashSet<String>();
            for (ReadEntity read : inputs) {
                // if read is not direct, we do not need to check its autho.
                if (read.isDummy() || read.isPathType() || !read.isDirect()) {
                    continue;
                }
                if (read.getType() == Entity.Type.DATABASE) {
                    authorizer.authorize(read.getDatabase(), op.getInputRequiredPrivileges(), null);
                    continue;
                }
                Table tbl = read.getTable();
                if (tbl.isView() && sem instanceof SemanticAnalyzer) {
                    tab2Cols.put(tbl, sem.getColumnAccessInfo().getTableToColumnAccessMap().get(tbl.getCompleteName()));
                }
                if (read.getPartition() != null) {
                    Partition partition = read.getPartition();
                    tbl = partition.getTable();
                    // use partition level authorization
                    if (Boolean.TRUE.equals(tableUsePartLevelAuth.get(tbl.getTableName()))) {
                        List<String> cols = part2Cols.get(partition);
                        if (cols != null && cols.size() > 0) {
                            authorizer.authorize(partition.getTable(),
                                                 partition,
                                                 cols,
                                                 op.getInputRequiredPrivileges(),
                                                 null);
                        } else {
                            authorizer.authorize(partition, op.getInputRequiredPrivileges(), null);
                        }
                        continue;
                    }
                }

                // if we reach here, it means it needs to do a table authorization
                // check, and the table authorization may already happened because of other
                // partitions
                if (tbl != null && !tableAuthChecked.contains(tbl.getTableName()) && !(Boolean.TRUE.equals(
                        tableUsePartLevelAuth.get(tbl.getTableName())))) {
                    List<String> cols = tab2Cols.get(tbl);
                    if (cols != null && cols.size() > 0) {
                        authorizer.authorize(tbl, null, cols, op.getInputRequiredPrivileges(), null);
                    } else {
                        authorizer.authorize(tbl, op.getInputRequiredPrivileges(), null);
                    }
                    tableAuthChecked.add(tbl.getTableName());
                }
            }
        }
    }

    private static void getTablePartitionUsedColumns(HiveOperation op,
                                                     BaseSemanticAnalyzer sem,
                                                     Map<Table, List<String>> tab2Cols,
                                                     Map<Partition, List<String>> part2Cols,
                                                     Map<String, Boolean> tableUsePartLevelAuth) throws HiveException {
        // for a select or create-as-select query, populate the partition to column
        // (par2Cols) or
        // table to columns mapping (tab2Cols)
        if (op.equals(HiveOperation.CREATETABLE_AS_SELECT) || op.equals(HiveOperation.QUERY)) {
            SemanticAnalyzer querySem = (SemanticAnalyzer) sem;
            ParseContext parseCtx = querySem.getParseContext();

            for (Map.Entry<String, TableScanOperator> topOpMap : querySem.getParseContext().getTopOps().entrySet()) {
                TableScanOperator tableScanOp = topOpMap.getValue();
                if (!tableScanOp.isInsideView()) {
                    Table tbl = tableScanOp.getConf().getTableMetadata();
                    List<Integer> neededColumnIds = tableScanOp.getNeededColumnIDs();
                    List<FieldSchema> columns = tbl.getCols();
                    List<String> cols = new ArrayList<String>();
                    for (int i = 0; i < neededColumnIds.size(); i++) {
                        cols.add(columns.get(neededColumnIds.get(i)).getName());
                    }
                    // map may not contain all sources, since input list may have been
                    // optimized out
                    // or non-existent tho such sources may still be referenced by the
                    // TableScanOperator
                    // if it's null then the partition probably doesn't exist so let's use
                    // table permission
                    if (tbl.isPartitioned() && Boolean.TRUE.equals(tableUsePartLevelAuth.get(tbl.getTableName()))) {
                        String alias_id = topOpMap.getKey();

                        PrunedPartitionList partsList = PartitionPruner.prune(tableScanOp, parseCtx, alias_id);
                        Set<Partition> parts = partsList.getPartitions();
                        for (Partition part : parts) {
                            List<String> existingCols = part2Cols.get(part);
                            if (existingCols == null) {
                                existingCols = new ArrayList<String>();
                            }
                            existingCols.addAll(cols);
                            part2Cols.put(part, existingCols);
                        }
                    } else {
                        List<String> existingCols = tab2Cols.get(tbl);
                        if (existingCols == null) {
                            existingCols = new ArrayList<String>();
                        }
                        existingCols.addAll(cols);
                        tab2Cols.put(tbl, existingCols);
                    }
                }
            }
        }
    }

    private static void doAuthorizationV2(SessionState ss,
                                          HiveOperation op,
                                          Set<ReadEntity> inputs,
                                          Set<WriteEntity> outputs,
                                          String command,
                                          Map<String, List<String>> tab2cols,
                                          Map<String, List<String>> updateTab2Cols) throws HiveException {

    /* comment for reviewers -> updateTab2Cols needed to be separate from tab2cols because if I
    pass tab2cols to getHivePrivObjects for the output case it will trip up insert/selects,
    since the insert will get passed the columns from the select.
     */

        HiveAuthzContext.Builder authzContextBuilder = new HiveAuthzContext.Builder();
        authzContextBuilder.setUserIpAddress(ss.getUserIpAddress());
        authzContextBuilder.setForwardedAddresses(ss.getForwardedAddresses());
        authzContextBuilder.setCommandString(command);

        HiveOperationType hiveOpType = getHiveOperationType(op);
        List<HivePrivilegeObject> inputsHObjs = getHivePrivObjects(inputs, tab2cols);
        List<HivePrivilegeObject> outputHObjs = getHivePrivObjects(outputs, updateTab2Cols);

        ss.getAuthorizerV2().checkPrivileges(hiveOpType, inputsHObjs, outputHObjs, authzContextBuilder.build());
    }

    private static List<HivePrivilegeObject> getHivePrivObjects(Set<? extends Entity> privObjects,
                                                                Map<String, List<String>> tableName2Cols) {
        List<HivePrivilegeObject> hivePrivobjs = new ArrayList<HivePrivilegeObject>();
        if (privObjects == null) {
            return hivePrivobjs;
        }
        for (Entity privObject : privObjects) {
            HivePrivilegeObjectType privObjType = AuthorizationUtils.getHivePrivilegeObjectType(privObject.getType());
            if (privObject.isDummy()) {
                // do not authorize dummy readEntity or writeEntity
                continue;
            }
            if (privObject instanceof ReadEntity && !((ReadEntity) privObject).isDirect()) {
                // In case of views, the underlying views or tables are not direct dependencies
                // and are not used for authorization checks.
                // This ReadEntity represents one of the underlying tables/views, so skip it.
                // See description of the isDirect in ReadEntity
                continue;
            }
            if (privObject instanceof WriteEntity && ((WriteEntity) privObject).isTempURI()) {
                // do not authorize temporary uris
                continue;
            }
            // support for authorization on partitions needs to be added
            String dbname = null;
            String objName = null;
            List<String> partKeys = null;
            List<String> columns = null;
            switch (privObject.getType()) {
                case DATABASE:
                    dbname = privObject.getDatabase().getName();
                    break;
                case TABLE:
                    dbname = privObject.getTable().getDbName();
                    objName = privObject.getTable().getTableName();
                    columns =
                            tableName2Cols == null ? null : tableName2Cols.get(Table.getCompleteName(dbname, objName));
                    break;
                case DFS_DIR:
                case LOCAL_DIR:
                    objName = privObject.getD().toString();
                    break;
                case FUNCTION:
                    if (privObject.getDatabase() != null) {
                        dbname = privObject.getDatabase().getName();
                    }
                    objName = privObject.getFunctionName();
                    break;
                case DUMMYPARTITION:
                case PARTITION:
                    // not currently handled
                    continue;
                default:
                    throw new AssertionError("Unexpected object type");
            }
            HivePrivObjectActionType actionType = AuthorizationUtils.getActionType(privObject);
            HivePrivilegeObject hPrivObject = new HivePrivilegeObject(privObjType,
                                                                      dbname,
                                                                      objName,
                                                                      partKeys,
                                                                      columns,
                                                                      actionType,
                                                                      null);
            hivePrivobjs.add(hPrivObject);
        }
        return hivePrivobjs;
    }

    private static HiveOperationType getHiveOperationType(HiveOperation op) {
        return HiveOperationType.valueOf(op.name());
    }

    /**
     * @return The current query plan associated with this Driver, if any.
     */
    public QueryPlan getPlan() {
        return plan;
    }

    /**
     * @return The current FetchTask associated with the Driver's plan, if any.
     */
    public FetchTask getFetchTask() {
        return fetchTask;
    }

    // Write the current set of valid transactions into the conf file so that it can be read by
    // the input format.
    private void recordValidTxns() throws LockException {
        HiveTxnManager txnMgr = SessionState.get().getTxnMgr();
        ValidTxnList txns = txnMgr.getValidTxns();
        String txnStr = txns.toString();
        conf.set(ValidTxnList.VALID_TXNS_KEY, txnStr);
        if (plan.getFetchTask() != null) {
            /**
             * This is needed for {@link HiveConf.ConfVars.HIVEFETCHTASKCONVERSION} optimization which
             * initializes JobConf in FetchOperator before recordValidTxns() but this has to be done after
             * locks are acquired to avoid race conditions in ACID.
             */
            plan.getFetchTask().setValidTxnList(txnStr);
        }
        LOG.debug("Encoding valid txns info " + txnStr + " txnid:" + txnMgr.getCurrentTxnId());
    }

    /**
     * Acquire read and write locks needed by the statement. The list of objects to be locked are
     * obtained from the inputs and outputs populated by the compiler. The lock acquisition scheme is
     * pretty simple. If all the locks cannot be obtained, error out. Deadlock is avoided by making
     * sure that the locks are lexicographically sorted.
     * <p>This method also records the list of valid transactions. This must be done after any
     * transactions have been opened and locks acquired.
     *
     * @param startTxnImplicitly in AC=false, the 1st DML starts a txn
     */
    private int acquireLocksAndOpenTxn(boolean startTxnImplicitly) {
        PerfLogger perfLogger = SessionState.getPerfLogger();
        perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.ACQUIRE_READ_WRITE_LOCKS);

        SessionState ss = SessionState.get();
        HiveTxnManager txnMgr = ss.getTxnMgr();
        assert !startTxnImplicitly || !txnMgr.getAutoCommit();

        try {
            // Don't use the userName member, as it may or may not have been set.  Get the value from
            // conf, which calls into getUGI to figure out who the process is running as.
            String userFromUGI;
            try {
                userFromUGI = conf.getUser();
            } catch (IOException e) {
                errorMessage = "FAILED: Error in determining user while acquiring locks: " + e.getMessage();
                SQLState = ErrorMsg.findSQLState(e.getMessage());
                downstreamError = e;
                console.printError(errorMessage, "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
                return 10;
            }

            boolean initiatingTransaction = false;
            boolean readOnlyQueryInAutoCommit = false;
            if ((txnMgr.getAutoCommit() && haveAcidWrite()) || plan.getOperation() == HiveOperation.START_TRANSACTION
                    || (!txnMgr.getAutoCommit() && startTxnImplicitly)) {
                if (txnMgr.isTxnOpen()) {
                    throw new RuntimeException("Already have an open transaction txnid:" + txnMgr.getCurrentTxnId());
                }
                // We are writing to tables in an ACID compliant way, so we need to open a transaction
                txnMgr.openTxn(ctx, userFromUGI);
                initiatingTransaction = true;
            } else {
                readOnlyQueryInAutoCommit =
                        txnMgr.getAutoCommit() && plan.getOperation() == HiveOperation.QUERY && !haveAcidWrite();
            }
            // Set the transaction id in all of the acid file sinks
            if (haveAcidWrite()) {
                for (FileSinkDesc desc : acidSinks) {
                    desc.setTransactionId(txnMgr.getCurrentTxnId());
                    // it's possible to have > 1 FileSink writing to the same table/partition
                    // e.g. Merge stmt, multi-insert stmt when mixing DP and SP writes
                    desc.setStatementId(txnMgr.getWriteIdAndIncrement());
                }
            }
      /*Note, we have to record snapshot after lock acquisition to prevent lost update problem
      consider 2 concurrent "update table T set x = x + 1".  1st will get the locks and the
      2nd will block until 1st one commits and only then lock in the snapshot, i.e. it will
      see the changes made by 1st one.  This takes care of autoCommit=true case.
      For multi-stmt txns this is not sufficient and will be managed via WriteSet tracking
      in the lock manager.*/
            txnMgr.acquireLocks(plan, ctx, userFromUGI);
            if (initiatingTransaction || (readOnlyQueryInAutoCommit && acidInQuery)) {
                // For multi-stmt txns we should record the snapshot when txn starts but
                // don't update it after that until txn completes.  Thus the check for {@code
                // initiatingTransaction}
                // For autoCommit=true, Read-only statements, txn is implicit, i.e. lock in the snapshot
                // for each statement.
                recordValidTxns();
            }

            return 0;
        } catch (Exception e) {
            errorMessage = "FAILED: Error in acquiring locks: " + e.getMessage();
            SQLState = ErrorMsg.findSQLState(e.getMessage());
            downstreamError = e;
            console.printError(errorMessage, "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
            return 10;
        } finally {
            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.ACQUIRE_READ_WRITE_LOCKS);
        }
    }

    private boolean haveAcidWrite() {
        return acidSinks != null && !acidSinks.isEmpty();
    }

    /**
     * @param commit     if there is an open transaction and if true, commit, if false rollback. If there
     *                   is no open transaction this parameter is ignored.<br>
     *                   如果是true 并且有一个 打开的事务，则提交事务。 如果是false，则回滚。如果没有打开的事务，该参数将被忽略。
     * @param txnManager an optional existing transaction manager retrieved earlier from the session
     */
    private void releaseLocksAndCommitOrRollback(boolean commit,
                                                 HiveTxnManager txnManager) throws LockException {
        PerfLogger perfLogger = SessionState.getPerfLogger();
        perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.RELEASE_LOCKS);

        HiveTxnManager txnMgr;
        if (txnManager == null) {
            SessionState ss = SessionState.get();
            txnMgr = ss.getTxnMgr();
        } else {
            txnMgr = txnManager;
        }
        // If we've opened a transaction we need to commit or rollback rather than explicitly
        // releasing the locks.
        // 如果我 打开一个事务，我们应该 提交 或者 回滚事件，而不是 隐式 的释放了锁。
        if (txnMgr.isTxnOpen()) {
            if (commit) {
                if (conf.getBoolVar(ConfVars.HIVE_IN_TEST) && conf.getBoolVar(ConfVars.HIVETESTMODEROLLBACKTXN)) {
                    txnMgr.rollbackTxn();
                } else {
                    txnMgr.commitTxn(); // both commit & rollback clear ALL locks for this tx
                }
            } else {
                txnMgr.rollbackTxn();
            }
        } else {
            // since there is no tx, we only have locks for current query (if any)
            // 由于没有 事务，我们仅仅持有 当前 查询的锁。
            if (ctx != null && ctx.getHiveLocks() != null) {
                hiveLocks.addAll(ctx.getHiveLocks());
            }
            txnMgr.releaseLocks(hiveLocks);
        }
        hiveLocks.clear();
        if (ctx != null) {
            ctx.setHiveLocks(null);
        }

        perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.RELEASE_LOCKS);
    }

    /**
     * Release some resources after a query is executed while keeping the result around.
     */
    private void releaseResources() {
        releasePlan();
        releaseDriverContext();
        if (SessionState.get() != null) {
            SessionState.get().getLineageState().clear();
        }
    }

    @Override
    public CommandProcessorResponse run(String command) throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " run(String) run-1  command = " + command
                         + ", alreadyCompiled = false ");
        CommandProcessorResponse cpr = run(command, false);
        LOG.info(
                LOG_GY_PREFIX + LOG_GY_END + " run(String) run-1  command = " + command + ", alreadyCompiled = false ");
        return cpr;
    }

    public CommandProcessorResponse run() throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " run() run-2  command = null, alreadyCompiled = true");
        CommandProcessorResponse cpr = run(null, true);
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " run() run-2  command = null, alreadyCompiled = true");
        return cpr;
    }

    public CommandProcessorResponse run(String command,
                                        boolean alreadyCompiled) throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " run(String, boolean) run-3  command = " + command
                         + ", alreadyCompiled = " + alreadyCompiled);

        // 2018-05-02 by lipeng read auth username and pwd
        this.username = HiveConf.getVar(conf, ConfVars.GUYUE_USER);
        this.password = HiveConf.getVar(conf, ConfVars.GUYUE_PASSWORD);

        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3  Process Begining ..............runInternal().....................");
        CommandProcessorResponse cpr = runInternal(command, alreadyCompiled);
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3  Process Endding  ..............runInternal().....................");

        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getSQLState = " + cpr.getSQLState());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getSchema = " + cpr.getSchema());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getConsoleMessages = " + cpr.getConsoleMessages());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getErrorMessage = " + cpr.getErrorMessage());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getErrorCode = " + cpr.getErrorCode());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getResponseCode = " + cpr.getResponseCode());
        LOG.info(LOG_GY_PREFIX + " \t run(String, boolean) run-3 CommandProcessorResponse.getSQLState = " + cpr.getSQLState());

        if (cpr.getResponseCode() == 0) {
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + "-1 run(String, boolean) run-3  command = " + command + ", alreadyCompiled = " + alreadyCompiled);
            return cpr;
        }
        SessionState ss = SessionState.get();
        if (ss == null) {
            return cpr;
        }
        MetaDataFormatter mdf = MetaDataFormatUtils.getFormatter(ss.getConf());
        if (!(mdf instanceof JsonMetaDataFormatter)) {
            return cpr;
        }
        /*Here we want to encode the error in machine readable way (e.g. JSON)
         * Ideally, errorCode would always be set to a canonical error defined in ErrorMsg.
         * In practice that is rarely the case, so the messy logic below tries to tease
         * out canonical error code if it can.  Exclude stack trace from output when
         * the error is a specific/expected one.
         * It's written to stdout for backward compatibility (WebHCat consumes it).*/
        try {
            if (downstreamError == null) {
                mdf.error(ss.out, errorMessage, cpr.getResponseCode(), SQLState);
                return cpr;
            }
            ErrorMsg canonicalErr = ErrorMsg.getErrorMsg(cpr.getResponseCode());
            if (canonicalErr != null && canonicalErr != ErrorMsg.GENERIC_ERROR) {
        /*Some HiveExceptions (e.g. SemanticException) don't set
        canonical ErrorMsg explicitly, but there is logic
        (e.g. #compile()) to find an appropriate canonical error and
        return its code as error code. In this case we want to
        preserve it for downstream code to interpret*/
                mdf.error(ss.out, errorMessage, cpr.getResponseCode(), SQLState, null);
                return cpr;
            }
            if (downstreamError instanceof HiveException) {
                HiveException rc = (HiveException) downstreamError;
                mdf.error(ss.out,
                          errorMessage,
                          rc.getCanonicalErrorMsg().getErrorCode(),
                          SQLState,
                          rc.getCanonicalErrorMsg()
                                  == ErrorMsg.GENERIC_ERROR ? org.apache.hadoop.util.StringUtils.stringifyException(rc) : null);
            } else {
                ErrorMsg canonicalMsg = ErrorMsg.getErrorMsg(downstreamError.getMessage());
                mdf.error(ss.out,
                          errorMessage,
                          canonicalMsg.getErrorCode(),
                          SQLState,
                          org.apache.hadoop.util.StringUtils.stringifyException(downstreamError));
            }
        } catch (HiveException ex) {
            console.printError("Unable to JSON-encode the error",
                               org.apache.hadoop.util.StringUtils.stringifyException(ex));
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + "-2 run(String, boolean) run-3  command = " + command + ", alreadyCompiled = " + alreadyCompiled);
        return cpr;
    }

    private void doAuthorizationExtend(String command,
                                       BaseSemanticAnalyzer sem) throws AuthorizationException {
        // 获取用户权限信息
        UserAuthDataMode ua = null;
        String userName = null;
        boolean logSwitch = this.conf.getBoolVar(ConfVars.GUYUE_EXEC_LOG_SWTICH);
        try {
            userName = StringUtils.isEmpty(SessionState.get().getUserName()) ? this.conf.getUser() : SessionState.get().getUserName();
            String userProxy = this.conf.getVar(ConfVars.GUYUE_AUTHORIZATION_USER_PROXY).trim();
            ua = new UserAuthDataMode(StringUtils.isEmpty(userProxy) ? userName : userProxy, this.password, this.conf);
            ua.run();
        } catch (Exception e) {
            throw new AuthorizationException(e.getMessage());
        }
        if (ua.isSuperUser()) {
            LOG.warn("current user is super user,do not check authorization.");
            return;
        }

        LOG.warn("current user is [" + userName + "] execute command [" + command + "].");

        SessionState ss = SessionState.get();
        HiveOperation op = sem.getQueryState().getHiveOperation();
        if (op != null) {
            // 不处理这种方式，hiveserver并不提供写入操作	LOG.debug("---------auth KUXUN--------------");
        }

        if (logSwitch) {
            LOG.error("----------> 2 \t Hive_Operation = " + op);
        }

        if (op.equals(HiveOperation.CREATETABLE_AS_SELECT) || op.equals(HiveOperation.QUERY)) {

            /**
             * ----------------------------------------------------------------- save select table info.
             * -----------------------------------------------------------------
             */
            HashSet<ReadEntity> readEntityByQuery = sem.getInputs();
            // LOG.error("----------> 3 \t readEntityByQuery = " + readEntityByQuery);
            if (null != readEntityByQuery && !readEntityByQuery.isEmpty()) {

                if (logSwitch) {
                    LOG.error("----------> 4 \t readEntityByQuery.size = " + readEntityByQuery.size());
                }

                // 1. Check DB, TABLE auth.
                //      对 DB, Table的权限是 '正向授权'，即，允许你访问哪些资源
                // ----------- Begin FOR
                for (ReadEntity read : readEntityByQuery) {
                    int flag = 0;
                    Table tbl = read.getTable();
                    String tblName = tbl.getTableName();
                    String databaseName = tbl.getDbName();
                    String tableFullName = databaseName + "." + tblName;

                    if (logSwitch) {
                        LOG.error("---------->  4- \t ReadEntity.tableName = " + tblName);
                        LOG.error("---------->  4- \t ReadEntity.name = " + read.getName());
                        LOG.error("---------->  4- \t ReadEntity.D = " + read.getD());
                        LOG.error("---------->  4- \t dbName.tableName = " + tbl.getDbName() + "." + tblName);
                        LOG.error("---------->  4- \t databaseName = " + databaseName);
                        LOG.error("---------->  4- \t tableFullName = " + tableFullName);
                    }

                    // 没有 DB 权限, 再判断一下是否有表权限
                    if (!ua.getUserDBSet().contains(tbl.getDbName())) {

                        // 没有 Table 权限, 直接报异常
                        if (!ua.getUserTableSet().contains(tableFullName)) {
                            flag = 1;
                        }
                    }

                    LOG.error("----------> \t userName = " + userName + ", flag = " + flag + ", tableName = " + tblName);
                    // 没有 DB & Table 权限, 直接报异常
                    if (flag > 0) {
                        throw new AuthorizationException(" table [" + tableFullName + "] Pemission denied.");
                    }
                }
                // ----------- END FOR

                SemanticAnalyzer querySem = (SemanticAnalyzer) sem;
                ParseContext parseContext = querySem.getParseContext();
                QB queryBlock = ((SemanticAnalyzer) sem).getQB();
                QBMetaData metaData = queryBlock.getMetaData();
                QBParseInfo parseInfo = queryBlock.getParseInfo();
                Map<String, TableScanOperator> tsoTopMap = parseContext.getTopOps();

                if (logSwitch) {
                    LOG.error("----------> 5 \t tsoTopMap = " + tsoTopMap);
                }


                // 2. Check Column auth
                //      列 是反射授权, 即，不允许你访问哪些列
                // for----------- Begin FOR
                for (Map.Entry<String, TableScanOperator> tabNameTabOperEntry : tsoTopMap.entrySet()) {

                    Operator<? extends Serializable> topOp = tabNameTabOperEntry.getValue();

                    if (logSwitch) {
                        try {
                            LOG.error("----------> -- \t tsoTopMap.size = " + tsoTopMap.size());
                            LOG.error("----------> -- \t topOp.class.name = " + topOp.getClass().getName());
                            LOG.error("----------> -- \t table-name = " + tabNameTabOperEntry.getKey());
                            LOG.error("----------> -- \t table-scan-operator = " + tabNameTabOperEntry.getValue().toString());
                            LOG.error("----------> -- \t table-scan-operator.name = " + tabNameTabOperEntry.getValue().getName());
                            LOG.error("----------> -- \t table-scan-operator.NeededColumnIDs = " + tabNameTabOperEntry.getValue().getNeededColumnIDs());
                            LOG.error("----------> -- \t table-scan-operator.NeededColumns = " + tabNameTabOperEntry.getValue().getNeededColumns());
                            LOG.error("----------> -- \t table-scan-operator.NeededNestedColumnPath = " + tabNameTabOperEntry.getValue().getNeededNestedColumnPaths());
                            LOG.error("----------> -- \t table-scan-operator.ReferencedColumns = " + tabNameTabOperEntry.getValue().getReferencedColumns());
                            LOG.error("----------> -- \t table-scan-operator.ColumnExprMap = " + tabNameTabOperEntry.getValue().getColumnExprMap());
                            LOG.error("----------> -- \t table-scan-operator.SchemaEvolutionColumns = " + tabNameTabOperEntry.getValue().getSchemaEvolutionColumns());
                            LOG.error("----------> -- \t table-scan-operator.SchemaEvolutionColumnsTypes = " + tabNameTabOperEntry.getValue().getSchemaEvolutionColumnsTypes());
                        } catch (Exception e) {
                            LOG.error("----------> " + e.getCause());
                        }
                    }

                    if (topOp instanceof TableScanOperator) {
                        TableScanOperator tableScanOp = (TableScanOperator) topOp;
                        TableScanDesc tabScanDesc = tableScanOp.getConf();
                        Table tableMetadata = tabScanDesc.getTableMetadata();


                        String databaseName = tableMetadata.getDbName();
                        String tableName = tableMetadata.getTTable().getTableName();
                        // like this : bi_dw@dim_shop_list
                        String tableCompleteName = tableMetadata.getCompleteName();
                        String tableFullName = databaseName + "." + tableName;

                        if (logSwitch) {
                            LOG.error("----------> -- \t databaseName = " + databaseName);
                            LOG.error("----------> -- \t tableName = " + tableName);
                            LOG.error("----------> -- \t tableCompleteName= " + tableCompleteName);
                            LOG.error("----------> -- \t tableFullName = " + tableFullName);
                        }

                        List<Integer> neededColumnIds = tableScanOp.getNeededColumnIDs();
                        List<String> referencedColumnsList = tableScanOp.getReferencedColumns();
                        List<FieldSchema> fieldSchemaList = tableMetadata.getCols();
                        Set<String> fieldNameSet = new HashSet<String>();

                        /**
                         * Need columns
                         * for query columns
                         */
                        if (null != neededColumnIds && !neededColumnIds.isEmpty()) {
                            for (int i = 0; i < neededColumnIds.size(); i++) {
                                fieldNameSet.add(tableFullName + "." + fieldSchemaList.get(neededColumnIds.get(i)).getName());
                            }
                        }
                        if (logSwitch) {
                            LOG.error("----------> --Need \t fieldNameList = " + fieldNameSet);
                        }

                        /**
                         * Reference columns
                         * for partition column
                         */
                        if (null != referencedColumnsList && !referencedColumnsList.isEmpty()) {
                            for (String referencedColumn : referencedColumnsList) {
                                fieldNameSet.add(tableFullName + "." + referencedColumn);
                            }
                        }
                        if (logSwitch) {
                            LOG.error("----------> --Reference \t fieldNameList = " + fieldNameSet);
                        }

                        /**
                         * If fieldNameList is null
                         * addition all fields
                         */
                        if (fieldNameSet.isEmpty()) {
                            for (int i = 0; i < fieldSchemaList.size(); i++) {
                                fieldNameSet.add(tableFullName + "." + fieldSchemaList.get(i).getName());
                            }
                        }

                        if (logSwitch) {
                            LOG.error("----------> --Final \t fieldNameList = " + fieldNameSet);
                        }

                        // excolumn: 先过滤表授权
                        if (null != ua.getUserExcolumnSet() && !ua.getUserExcolumnSet().isEmpty()) {

                            for (String col : fieldNameSet) {
                                if (ua.getUserExcolumnSet().contains(col)) {
                                    throw new AuthorizationException("table [" + tableFullName + "] column [" + col + "] Permission denied.");
                                }
                                // LOG.warn(" exclude_column = [" + databaseName + "] . [" + tableName + "] : [" + col + "]");
                            }
                        }

                        // incolumn: 先过滤表授权
                        HashSet<String> authColSet = ua.getUserIncolumnMap().get(tableFullName);
                        if (null != authColSet && !authColSet.isEmpty()) {
                            for (String col : fieldNameSet) {

                                // 1. incolumn[a,b,c], sql[a,b,c,d], throw exception.
                                // 2. incolumn[a,b,c], sql[a,b], allow
                                if (!authColSet.contains(col)) {
                                    throw new AuthorizationException("table [" + tableFullName + "] must contain column [" + col + "]. Permission denied.");
                                }
                            }
                        }
                    }
                }
                // ----------- END FOR
            }
        }
    }

    public CommandProcessorResponse compileAndRespond(String command) {
        return createProcessorResponse(compileInternal(command, false));
    }

    private static final ReentrantLock globalCompileLock = new ReentrantLock();

    private int compileInternal(String command,
                                boolean deferClose) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " compileInternal(String,boolean) ");
        int ret;

        // 获取重入锁, 如果获得成功, 则直接加锁;
        final ReentrantLock compileLock = tryAcquireCompileLock(isParallelEnabled, command);
        if (compileLock == null) {
            return ErrorMsg.COMPILE_LOCK_TIMED_OUT.getErrorCode();
        }

        try {
            // 执行编译
            ret = compile(command, true, deferClose);

        } finally {
            // 释放编译的锁
            compileLock.unlock();
        }

        if (ret != 0) {
            try {
                releaseLocksAndCommitOrRollback(false, null);
            } catch (LockException e) {
                LOG.warn("Exception in releasing locks. " + org.apache.hadoop.util.StringUtils.stringifyException(e));
            }
        }

        // Save compile-time PerfLogging for WebUI.
        // Execution-time Perf logs are done by either another thread's PerfLogger
        // or a reset PerfLogger.
        PerfLogger perfLogger = SessionState.getPerfLogger();
        queryDisplay.setPerfLogStarts(QueryDisplay.Phase.COMPILATION, perfLogger.getStartTimes());
        queryDisplay.setPerfLogEnds(QueryDisplay.Phase.COMPILATION, perfLogger.getEndTimes());

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " compileInternal(String,boolean) ");
        return ret;
    }

    /**
     * Acquires the compile lock. If the compile lock wait timeout is configured, it will acquire the
     * lock if it is not held by another thread within the given waiting time.<br>
     * 获取 Compile 锁. 如果配置了锁定 等待 超时, 在给定的时候内 锁没有被其他线程占用，则线程会获取该锁。
     *
     * @return the ReentrantLock object if the lock was successfully acquired, or {@code null} if
     * compile lock wait timeout is configured and either the waiting time elapsed before the lock
     * could be acquired or if the current thread is interrupted. <br>
     * 获取锁成功则返回 ReentrantLock锁; <br>
     * 如果设置了等待时间，在获取锁之前，等待超时或线程被中断, 则返回null.
     */
    private ReentrantLock tryAcquireCompileLock(boolean isParallelEnabled,
                                                String command) {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " tryAcquireCompileLock(boolean,String) ");

        // 是否支持并行,如果支持并行的话,返回全局的锁,否则返回Session锁.
        LOG.info(
                LOG_GY_PREFIX + " \t 1 tryAcquireCompileLock(boolean,String) isParallelEnabled = " + isParallelEnabled);
        final ReentrantLock compileLock = isParallelEnabled ? SessionState.get().getCompileLock() : globalCompileLock;

        long maxCompileLockWaitTime = HiveConf.getTimeVar(this.conf,
                                                          ConfVars.HIVE_SERVER2_COMPILE_LOCK_TIMEOUT,
                                                          TimeUnit.SECONDS);

        final String lockAcquiredMsg = "Acquired the compile lock.";
        // First shot without waiting.
        try {
            if (compileLock.tryLock(0, TimeUnit.SECONDS)) {
                LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 1 tryAcquireCompileLock(boolean,String) ");
                return compileLock;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Interrupted Exception ignored", e);
            }
            return null;
        }

        // If the first shot fails, then we log the waiting messages.
        if (LOG.isDebugEnabled()) {
            LOG.debug("Waiting to acquire compile lock: " + command);
        }

        OperationLog ol = OperationLog.getCurrentOperationLog();
        if (ol != null) {
            ol.writeOperationLog(LoggingLevel.EXECUTION, "Waiting to acquire compile lock.\n");
        }

        if (maxCompileLockWaitTime > 0) {
            LOG.info(LOG_GY_PREFIX + " \t2 tryAcquireCompileLock(boolean,String)  maxCompileLockWaitTime = "
                             + maxCompileLockWaitTime);
            try {
                // 试着获取锁失败.
                if (!compileLock.tryLock(maxCompileLockWaitTime, TimeUnit.SECONDS)) {
                    errorMessage = ErrorMsg.COMPILE_LOCK_TIMED_OUT.getErrorCodedMsg();
                    LOG.error(errorMessage + ": " + command);
                    LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 2 tryAcquireCompileLock(boolean,String) ");
                    return null;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Interrupted Exception ignored", e);
                }
                return null;
            }
        } else {
            // 锁获取成功, 加锁操作.
            LOG.info(LOG_GY_PREFIX + " \t3 tryAcquireCompileLock(boolean,String)  compileLock.lock() ");
            compileLock.lock();
        }

        LOG.debug(lockAcquiredMsg);
        if (ol != null) {
            ol.writeOperationLog(LoggingLevel.EXECUTION, lockAcquiredMsg + "\n");
        }

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 3 tryAcquireCompileLock(String,boolean) ");
        return compileLock;
    }

    private CommandProcessorResponse runInternal(String command,
                                                 boolean alreadyCompiled)
            throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " runInternal(String,boolean) command = " + command
                         + ", alreadyCompiled = " + alreadyCompiled);


        LOG.info(LOG_GY_PREFIX + " 000 runInternal(String,Boolean) username = " + this.username);
        LOG.info(LOG_GY_PREFIX + " 000 runInternal(String,Boolean) userName = " + this.userName);
        LOG.info(LOG_GY_PREFIX + " 000 runInternal(String,Boolean) Vars-userName = " + this.conf.getVar(ConfVars.GUYUE_USER));
        LOG.info(LOG_GY_PREFIX + " 000 runInternal(String,Boolean) auth = " + this.conf.getVar(ConfVars.GUYUE_AUTHORIZATION_ENABLED));

        errorMessage = null;
        SQLState = null;
        downstreamError = null;

        // 修改 Driver 状态
        // 1. INITIALIZED 到 COMPILING
        stateLock.lock();
        try {
            if (alreadyCompiled) {
                LOG.info(LOG_GY_PREFIX + " \t 1 runInternal(String,boolean) driverState = " + driverState
                                 + ", alreadyCompiled = " + alreadyCompiled);
                if (driverState == DriverState.COMPILED) {
                    LOG.info(LOG_GY_PREFIX + " \t 2 runInternal(String,boolean) driverState = " + driverState);
                    driverState = DriverState.EXECUTING;
                } else {
                    LOG.info(LOG_GY_PREFIX + " \t 3 runInternal(String,boolean) driverState = " + driverState);
                    errorMessage = "FAILED: Precompiled query has been cancelled or closed.";
                    console.printError(errorMessage);
                    return createProcessorResponse(12);
                }
            } else {
                // 修改Driver的状态
                LOG.info(LOG_GY_PREFIX + " \t 4 runInternal(String,boolean) driverState = " + driverState);
                driverState = DriverState.COMPILING;
            }
            LOG.info(LOG_GY_PREFIX + " \t 5 runInternal(String,boolean) driverState = " + driverState + ", alreadyCompiled = " + alreadyCompiled);
        } finally {
            stateLock.unlock();
        }

        // a flag that helps to set the correct driver state in finally block by tracking if
        // the method has been returned by an error or not.
        // 一个标志 that 帮助正确设置Driver的最终块状态 通过跟踪, method是否返回了一个错误.
        boolean isFinishedWithError = true;
        //
        try {
            // Configuration & command
            HiveDriverRunHookContext hookContext = new HiveDriverRunHookContextImpl(conf,
                                                                                    alreadyCompiled ? ctx.getCmd() : command);

            // Get all the driver run hooks and pre-execute them.
            List<HiveDriverRunHook> driverRunHooks;
            try {
                driverRunHooks = getHooks(HiveConf.ConfVars.HIVE_DRIVER_RUN_HOOKS, HiveDriverRunHook.class);
                LOG.info(LOG_GY_PREFIX + " \t runInternal(String,boolean) pre-hook.size = " + driverRunHooks.size());
                for (HiveDriverRunHook driverRunHook : driverRunHooks) {
                    LOG.info(LOG_GY_PREFIX + " \t runInternal(String,boolean) driverRunHook = "
                                     + driverRunHook.getClass().getName());
                    driverRunHook.preDriverRun(hookContext);
                }
            } catch (Exception e) {
                errorMessage = "FAILED: Hive Internal Error: " + Utilities.getNameMessage(e);
                SQLState = ErrorMsg.findSQLState(e.getMessage());
                downstreamError = e;
                console.printError(errorMessage + "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
                return createProcessorResponse(12);
            }

            PerfLogger perfLogger = null;

            int ret;
            if (!alreadyCompiled) {
                LOG.info(LOG_GY_PREFIX + " \t 6 runInternal(String,boolean) alreadyCompiled = " + alreadyCompiled);
                // compile internal will automatically reset the perf logger
                // 内部编译会自动重置 pref-logger
                ret = compileInternal(command, true);
                // then we continue to use this perf logger
                // 这时,我们可以继续使用pref logger
                perfLogger = SessionState.getPerfLogger();
                LOG.info(LOG_GY_PREFIX + " \t 7 runInternal(String,boolean) response-status = " + ret);
                if (ret != 0) {
                    return createProcessorResponse(ret);
                }

                LOG.info(LOG_GY_PREFIX + " \t 7-1 runInternal(String,boolean) GUYUE_EXEC_MODEL = " + HiveConf.getVar(
                        this.conf,
                        ConfVars.GUYUE_EXEC_MODEL));
            } else {
                LOG.info(LOG_GY_PREFIX + " \t 7-2 runInternal(String,boolean) GUYUE_EXEC_MODEL = " + HiveConf.getVar(
                        this.conf,
                        ConfVars.GUYUE_EXEC_MODEL));
                // reuse existing perf logger.
                //
                perfLogger = SessionState.getPerfLogger();
                // Since we're reusing the compiled plan, we need to update its start time for current run
                plan.setQueryStartTime(perfLogger.getStartTime(PerfLogger.DRIVER_RUN));
            }

            // TODO 2018-05-07 by lipeng
            // TODO 2018-05-28 by lipeng
            // 放在这里是因为:
            //  1. hive-cli 模式下，执行 if分支.
            //  2. hiveServer2模式下，执行 '上面' else分支
            // 这样就能实现 explain 模式的所有功能.
            if ("explain".equalsIgnoreCase(HiveConf.getVar(this.conf, ConfVars.GUYUE_EXEC_MODEL))) {
                driverState = DriverState.EXECUTED;
                isFinishedWithError = false;
                return createProcessorResponse(0);
            }

            // the reason that we set the txn manager for the cxt here is because each
            // query has its own ctx object. The txn mgr is shared across the
            // same instance of Driver, which can run multiple queries.
            // 我们为ctx设置ctxManager的原因是 每个 Query 都有自己的 ctx对象.
            // 这个txn Manager 通过 Driver 实例 在运行的多个 Query 之间共享.
            // ctx = org.apache.hadoop.hive.Context
            // txnMgr = HiveTxnManager [Transaction manager to use for this session. 这个Session的事务管理器]
            HiveTxnManager txnManager = SessionState.get().getTxnMgr();
            ctx.setHiveTxnManager(txnManager);

            boolean startTxnImplicitly = false;
            {
                // this block ensures op makes sense in given context, e.g. COMMIT is valid only if txn is
                // open
                // DDL is not allowed in a txn, etc.
                // 这阻塞确保 HiveOperation 在给定的Context中是有意义的. 例如：只有在 txn 是打开状态时,才可以COMMIT.
                // 在一个txn中，不允许执行DLL操作等.
                // an error in an open txn does a rollback of the txn
                // 在打开的txn中，txn遇到Error将会回滚.
                if (txnManager.isTxnOpen() && !plan.getOperation().isAllowedInTransaction()) {
                    assert !txnManager.getAutoCommit() : "didn't expect AC=true";
                    return rollback(new CommandProcessorResponse(12,
                                                                 ErrorMsg.OP_NOT_ALLOWED_IN_TXN,
                                                                 null,
                                                                 plan.getOperationName(),
                                                                 Long.toString(txnManager.getCurrentTxnId())));
                }
                if (!txnManager.isTxnOpen() && plan.getOperation().isRequiresOpenTransaction()) {
                    return rollback(new CommandProcessorResponse(12,
                                                                 ErrorMsg.OP_NOT_ALLOWED_WITHOUT_TXN,
                                                                 null,
                                                                 plan.getOperationName()));
                }
                if (!txnManager.isTxnOpen() && plan.getOperation() == HiveOperation.QUERY
                        && !txnManager.getAutoCommit()) {
                    // this effectively makes START TRANSACTION optional and supports JDBC
                    // setAutoCommit(false) semantics
                    // also, indirectly allows DDL to be executed outside a txn context
                    startTxnImplicitly = true;
                }
                if (txnManager.getAutoCommit() && plan.getOperation() == HiveOperation.START_TRANSACTION) {
                    return rollback(new CommandProcessorResponse(12,
                                                                 ErrorMsg.OP_NOT_ALLOWED_IN_AUTOCOMMIT,
                                                                 null,
                                                                 plan.getOperationName()));
                }
            }
            LOG.info(LOG_GY_PREFIX + " \t 8 runInternal(String,boolean) plan.operation = " + HiveOperation.SET_AUTOCOMMIT);
            if (plan.getOperation() == HiveOperation.SET_AUTOCOMMIT) {
                try {
                    if (plan.getAutoCommitValue() && !txnManager.getAutoCommit()) {
                        /*here, if there is an open txn, we want to commit it; this behavior matches
                         * https://docs.oracle.com/javase/6/docs/api/java/sql/Connection.html#setAutoCommit(boolean)*/
                        releaseLocksAndCommitOrRollback(true, null);
                        txnManager.setAutoCommit(true);
                    } else if (!plan.getAutoCommitValue() && txnManager.getAutoCommit()) {
                        txnManager.setAutoCommit(false);
                    } else {
                        /*didn't change autoCommit value - no-op*/
                    }
                } catch (LockException e) {
                    return handleHiveException(e, 12);
                }
            }

            boolean requiresLock = requiresLock();
            LOG.info(LOG_GY_PREFIX + " \t 9 runInternal(String,boolean) requiresLock = " + requiresLock);
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.rootTask.size = " + plan.getRootTasks().size());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.OperationName = " + plan.getOperationName());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.QueryStr = " + plan.getQueryStr());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.QueryString = " + plan.getQueryString());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.getColumnAccessInfo = " + plan.getColumnAccessInfo());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.Inputs = " + plan.getInputs());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.LineageInfo = " + plan.getLineageInfo());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.Outputs = " + plan.getOutputs());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.TableAccessInfo = " + plan.getTableAccessInfo());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.getTableToColumnAccessMap = " + plan.getColumnAccessInfo());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.getOperationName = " + plan.getOperationName());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.getDone = " + plan.getDone());
            LOG.info(LOG_GY_PREFIX + " \t \t -runInternal(String,boolean) plan.getQueryStartTime = " + plan.getQueryStartTime());
            for (Task t : plan.getRootTasks()) {
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getName = " + t.getName());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getType = " + t.getType());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getQueryPlan = " + t.getQueryPlan());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getId = " + t.getId());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getStatusMessage = " + t.getStatusMessage());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getMapWork = " + t.getMapWork());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getCounters = " + t.getCounters());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getTaskState = " + t.getTaskState());
                LOG.info(LOG_GY_PREFIX + " \t \t\t -runInternal(String,boolean) t.getChildTasks = " + t.getChildTasks());
            }

            LOG.info(LOG_GY_PREFIX + " \t 10 runInternal(String,boolean) requiresLock = " + plan.getRootTasks());
            if (requiresLock) {
                // a checkpoint to see if the thread is interrupted or not before an expensive operation
                // 在一个高消耗的操作之前 检查点 查看 线程是否中断.
                if (isInterrupted()) {
                    ret = handleInterruption("at acquiring the lock.");
                } else {
                    ret = acquireLocksAndOpenTxn(startTxnImplicitly);
                }
                if (ret != 0) {
                    return rollback(createProcessorResponse(ret));
                }
            }

            LOG.info(LOG_GY_PREFIX + " \t 11 execute runInternal(String,boolean) command = " + command + " ---->>>>>>>>>>>>>>>>>>>>>>>>> ");
            ret = execute(true);
            LOG.info(LOG_GY_PREFIX + " \t 12 execute runInternal(String,boolean) command = " + command + " ----<<<<<<<<<<<<<<<<<<<<<<<<< ");

            if (ret != 0) {
                // if needRequireLock is false, the release here will do nothing because there is no lock
                return rollback(createProcessorResponse(ret));
            }

            // if needRequireLock is false, the release here will do nothing because there is no lock
            try {
                if (txnManager.getAutoCommit() || plan.getOperation() == HiveOperation.COMMIT) {
                    releaseLocksAndCommitOrRollback(true, null);
                } else if (plan.getOperation() == HiveOperation.ROLLBACK) {
                    releaseLocksAndCommitOrRollback(false, null);
                } else {
                    // txn (if there is one started) is not finished
                }
            } catch (LockException e) {
                return handleHiveException(e, 12);
            }

            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.DRIVER_RUN);
            queryDisplay.setPerfLogStarts(QueryDisplay.Phase.EXECUTION, perfLogger.getStartTimes());
            queryDisplay.setPerfLogEnds(QueryDisplay.Phase.EXECUTION, perfLogger.getEndTimes());

            // Take all the driver run hooks and post-execute them.
            try {
                for (HiveDriverRunHook driverRunHook : driverRunHooks) {
                    driverRunHook.postDriverRun(hookContext);
                }
            } catch (Exception e) {
                errorMessage = "FAILED: Hive Internal Error: " + Utilities.getNameMessage(e);
                SQLState = ErrorMsg.findSQLState(e.getMessage());
                downstreamError = e;
                console.printError(errorMessage + "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
                return createProcessorResponse(12);
            }
            isFinishedWithError = false;
            return createProcessorResponse(ret);
        } finally {
            if (isInterrupted()) {
                closeInProcess(true);
            } else {
                // only release the related resources ctx, driverContext as normal
                releaseResources();
            }
            stateLock.lock();
            try {
                if (driverState == DriverState.INTERRUPT) {
                    driverState = DriverState.ERROR;
                } else {
                    driverState = isFinishedWithError ? DriverState.ERROR : DriverState.EXECUTED;
                }
            } finally {
                stateLock.unlock();
            }
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " runInternal(String,boolean) command = " + command
                             + ", alreadyCompiled = " + alreadyCompiled);
        }
    }

    private CommandProcessorResponse rollback(CommandProcessorResponse cpr) {
        // console.printError(cpr.toString());
        try {
            releaseLocksAndCommitOrRollback(false, null);
        } catch (LockException e) {
            LOG.error("rollback() FAILED: " + cpr); // make sure not to loose
            handleHiveException(e, 12, "Additional info in hive.log at \"rollback() FAILED\"");
        }
        return cpr;
    }

    private CommandProcessorResponse handleHiveException(HiveException e,
                                                         int ret) {
        return handleHiveException(e, ret, null);
    }

    private CommandProcessorResponse handleHiveException(HiveException e,
                                                         int ret,
                                                         String rootMsg) {
        errorMessage = "FAILED: Hive Internal Error: " + Utilities.getNameMessage(e);
        if (rootMsg != null) {
            errorMessage += "\n" + rootMsg;
        }
        SQLState = e.getCanonicalErrorMsg() != null ? e.getCanonicalErrorMsg()
                .getSQLState() : ErrorMsg.findSQLState(e.getMessage());
        downstreamError = e;
        console.printError(errorMessage + "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
        return createProcessorResponse(ret);
    }

    private boolean requiresLock() {
        // 是否支持并发
        if (!checkConcurrency()) {
            return false;
        }
        // Lock operations themselves don't require the lock.
        // 锁操作本身，不需要锁.
        if (isExplicitLockOperation()) {
            return false;
        }
        //
        if (!HiveConf.getBoolVar(conf, ConfVars.HIVE_LOCK_MAPRED_ONLY)) {
            return true;
        }
        Queue<Task<? extends Serializable>> taskQueue = new LinkedList<Task<? extends Serializable>>();
        taskQueue.addAll(plan.getRootTasks());
        while (taskQueue.peek() != null) {
            Task<? extends Serializable> tsk = taskQueue.remove();
            if (tsk.requireLock()) {
                return true;
            }
            if (tsk instanceof ConditionalTask) {
                taskQueue.addAll(((ConditionalTask) tsk).getListTasks());
            }
            if (tsk.getChildTasks() != null) {
                taskQueue.addAll(tsk.getChildTasks());
            }
            // does not add back up task here, because back up task should be the same
            // type of the original task.
        }
        return false;
    }

    private boolean isExplicitLockOperation() {
        HiveOperation currentOpt = plan.getOperation();
        if (currentOpt != null) {
            switch (currentOpt) {
                case LOCKDB:
                case UNLOCKDB:
                case LOCKTABLE:
                case UNLOCKTABLE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private CommandProcessorResponse createProcessorResponse(int ret) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " \t createProcessorResponse(int)");
        SessionState.getPerfLogger().cleanupPerfLogMetrics();
        queryDisplay.setErrorMessage(errorMessage);
        LOG.info(LOG_GY_PREFIX + " \t 1. createProcessorResponse(int) errorMessage = " + errorMessage);
        LOG.info(LOG_GY_PREFIX + " \t 2. createProcessorResponse(int) SQLState = " + SQLState);
        LOG.info(LOG_GY_PREFIX + " \t 3. createProcessorResponse(int) downstreamError = " + downstreamError);
        LOG.info(LOG_GY_PREFIX + " \t 4. createProcessorResponse(int) ret = " + ret);
        return new CommandProcessorResponse(ret, errorMessage, SQLState, downstreamError);
    }

    /**
     * Returns a set of hooks specified in a configuration variable. See getHooks(HiveConf.ConfVars
     * hookConfVar, Class<T> clazz)
     */
    private List<Hook> getHooks(HiveConf.ConfVars hookConfVar) throws Exception {
        return getHooks(hookConfVar, Hook.class);
    }

    /**
     * Returns the hooks specified in a configuration variable.
     *
     * @param hookConfVar The configuration variable specifying a comma separated list of the hook
     *                    class names.
     * @param clazz       The super type of the hooks.
     *
     * @return A list of the hooks cast as the type specified in clazz, in the order they are listed
     * in the value of hookConfVar
     *
     * @throws Exception
     */
    private <T extends Hook> List<T> getHooks(ConfVars hookConfVar,
                                              Class<T> clazz) throws Exception {
        try {
            return HookUtils.getHooks(conf, hookConfVar, clazz);
        } catch (ClassNotFoundException e) {
            console.printError(hookConfVar.varname + " Class not found:" + e.getMessage());
            throw e;
        }
    }

    public int execute() throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + " \t 1 execute() ");
        return execute(false);
    }

    public int execute(boolean deferClose) throws CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + " \t 2-1 execute(boolean) deferClose = " + deferClose);
        PerfLogger perfLogger = SessionState.getPerfLogger();
        perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.DRIVER_EXECUTE);

        LOG.info(LOG_GY_PREFIX + " \t 2-2 execute(boolean) job-name = " + conf.get(MRJobConfig.JOB_NAME));
        boolean noName = StringUtils.isEmpty(conf.get(MRJobConfig.JOB_NAME));
        int maxlen = conf.getIntVar(HiveConf.ConfVars.HIVEJOBNAMELENGTH);

        LOG.info(LOG_GY_PREFIX + " \t 2-3 execute(boolean) max-len = " + maxlen);

        String queryId = conf.getVar(HiveConf.ConfVars.HIVEQUERYID);
        LOG.info(LOG_GY_PREFIX + " \t 2-4 execute(boolean) query-id = " + queryId);
        // Get the query string from the conf file as the compileInternal() method might
        // hide sensitive information during query redaction.
        // compileInternal() 方法中进行 查询编辑时，可能会隐藏一些敏感信息，所以从conf中重新获取查询语句.
        String queryStr = conf.getQueryString();
        LOG.info(LOG_GY_PREFIX + " \t 2-5 execute(boolean) queryStr = " + queryStr);

        stateLock.lock();
        try {
            // if query is not in compiled state, or executing state which is carried over from
            // a combined compile/execute in runInternal, throws the error
            // TODO guyue
            if (driverState != DriverState.COMPILED && driverState != DriverState.EXECUTING) {
                SQLState = "HY008";
                errorMessage = "FAILED: query " + queryStr + " has " + (
                        driverState == DriverState.INTERRUPT ? "been cancelled" : "not been compiled.");
                console.printError(errorMessage);
                return 1000;
            } else {
                driverState = DriverState.EXECUTING;
            }
        } finally {
            stateLock.unlock();
        }

        maxthreads = HiveConf.getIntVar(conf, HiveConf.ConfVars.EXECPARALLETHREADNUMBER);
        LOG.info(LOG_GY_PREFIX + " \t 2-6 execute(boolean) maxthreads = " + maxthreads);

        HookContext hookContext = null;
        boolean executionError = false;
        try {
            // compile and execute can get called from different threads in case of HS2
            // so clear timing in this thread's Hive object before proceeding.
            Hive.get().clearMetaCallTiming();

            plan.setStarted();

            if (SessionState.get() != null) {
                SessionState.get().getHiveHistory().startQuery(queryStr, conf.getVar(HiveConf.ConfVars.HIVEQUERYID));
                SessionState.get().getHiveHistory().logPlanProgress(plan);
            }
            resStream = null;

            SessionState ss = SessionState.get();

            hookContext = new HookContext(plan,
                                          queryState,
                                          ctx.getPathToCS(),
                                          SessionState.getUserFromAuthenticator(),
                                          ss.getUserIpAddress(),
                                          InetAddress.getLocalHost().getHostAddress(),
                                          operationId,
                                          ss.getSessionId(),
                                          Thread.currentThread().getName(),
                                          ss.isHiveServerQuery(),
                                          perfLogger);
            hookContext.setHookType(HookContext.HookType.PRE_EXEC_HOOK);

            LOG.info(LOG_GY_PREFIX + " \t 2-8 execute(boolean) input = " + plan.getInputs());
            LOG.info(LOG_GY_PREFIX + " \t 2-9 execute(boolean) output = " + plan.getOutputs());

            for (Hook peh : getHooks(HiveConf.ConfVars.PREEXECHOOKS)) {

                LOG.info(LOG_GY_PREFIX + " \t\t 2- execute(boolean) PreExecute = " + peh.getClass().getName());

                if (peh instanceof ExecuteWithHookContext) {
                    perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.PRE_HOOK + peh.getClass().getName());

                    ((ExecuteWithHookContext) peh).run(hookContext);

                    perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.PRE_HOOK + peh.getClass().getName());
                } else if (peh instanceof PreExecute) {
                    perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.PRE_HOOK + peh.getClass().getName());

                    ((PreExecute) peh).run(SessionState.get(), plan.getInputs(), plan.getOutputs(), Utils.getUGI());

                    perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.PRE_HOOK + peh.getClass().getName());
                }
            }
            setQueryDisplays(plan.getRootTasks());
            LOG.info(LOG_GY_PREFIX + " \t 2-10 execute(boolean) root-task = " + plan.getRootTasks());

            int mrJobs = Utilities.getMRTasks(plan.getRootTasks()).size();
            LOG.info(LOG_GY_PREFIX + " \t 2-11 execute(boolean) mrJobs = " + plan.getRootTasks());
            LOG.info(LOG_GY_PREFIX + " \t 2-12 execute(boolean) TezTask = " + Utilities.getTezTasks(plan.getRootTasks())
                    .size());
            LOG.info(LOG_GY_PREFIX + " \t 2-13 execute(boolean) SparkTasks = "
                             + Utilities.getSparkTasks(plan.getRootTasks()));
            int jobs = mrJobs + Utilities.getTezTasks(plan.getRootTasks()).size()
                    + Utilities.getSparkTasks(plan.getRootTasks()).size();
            if (jobs > 0) {
                logMrWarning(mrJobs);
                console.printInfo(LOG_GY_PREFIX + " \t 2-14 Query ID = " + queryId);
                console.printInfo(LOG_GY_PREFIX + " \t 2-15 Total jobs = " + jobs);
            }
            if (SessionState.get() != null) {
                SessionState.get()
                        .getHiveHistory()
                        .setQueryProperty(queryId, Keys.QUERY_NUM_TASKS, String.valueOf(jobs));
                SessionState.get().getHiveHistory().setIdToTableMap(plan.getIdToTableNameMap());
            }
            String jobname = Utilities.abbreviate(queryStr, maxlen - 6);

            // A runtime that launches runnable tasks as separate Threads through
            // TaskRunners
            // Task的运行通过 TaskRunner 将可运行的任务分开执行。
            // As soon as a task isRunnable, it is put in a queue
            // 一个任务进入 Runnable状态, 任务将被放入一个队列.
            // At any time, at most maxthreads tasks can be running
            // 任何时候运行的线程数不能超过 maxthreads.
            // The main thread polls the TaskRunners to check if they have finished.
            // 如果一个线程运行完成,主线程拉取Taskrunner对其进行检查

            if (isInterrupted()) {
                return handleInterruption("before running tasks.");
            }
            DriverContext driverCxt = new DriverContext(ctx);
            driverCxt.prepare(plan);

            ctx.setHDFSCleanup(true);
            this.driverCxt = driverCxt; // for canceling the query (should be bound to session?)

            SessionState.get().setMapRedStats(new LinkedHashMap<String, MapRedStats>());
            SessionState.get().setStackTraces(new HashMap<String, List<List<String>>>());
            SessionState.get().setLocalMapRedErrors(new HashMap<String, List<String>>());

            // Add root Tasks to runnable
            for (Task<? extends Serializable> tsk : plan.getRootTasks()) {
                // This should never happen, if it does, it's a bug with the potential to produce
                // incorrect results.
                assert tsk.getParentTasks() == null || tsk.getParentTasks().isEmpty();
                LOG.info(LOG_GY_PREFIX + " \t\t 2-16- execute(boolean) task-type = " + tsk.getClass().getName());
                driverCxt.addToRunnable(tsk);
            }

            perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.RUN_TASKS);
            // Loop while you either have tasks running, or tasks queued up
            // 循环至到没有任务需要执行,或队列已经为空
            while (driverCxt.isRunning()) {

                // Launch upto maxthreads tasks
                Task<? extends Serializable> task;
                while ((task = driverCxt.getRunnable(maxthreads)) != null) {
                    TaskRunner runner = launchTask(task, queryId, noName, jobname, jobs, driverCxt);
                    if (!runner.isRunning()) {
                        break;
                    }
                }

                // poll the Tasks to see which one completed
                // 拉取已经完成的任务.
                TaskRunner tskRun = driverCxt.pollFinished();
                if (tskRun == null) {
                    continue;
                }
                hookContext.addCompleteTask(tskRun);
                queryDisplay.setTaskResult(tskRun.getTask().getId(), tskRun.getTaskResult());

                Task<? extends Serializable> tsk = tskRun.getTask();
                TaskResult result = tskRun.getTaskResult();

                int exitVal = result.getExitVal();
                if (isInterrupted()) {
                    return handleInterruption("when checking the execution result.");
                }
                if (exitVal != 0) {
                    if (tsk.ifRetryCmdWhenFail()) {
                        driverCxt.shutdown();
                        // in case we decided to run everything in local mode, restore the
                        // the jobtracker setting to its initial value
                        ctx.restoreOriginalTracker();
                        throw new CommandNeedRetryException();
                    }
                    Task<? extends Serializable> backupTask = tsk.getAndInitBackupTask();
                    if (backupTask != null) {
                        setErrorMsgAndDetail(exitVal, result.getTaskError(), tsk);
                        console.printError(errorMessage);
                        errorMessage = "ATTEMPT: Execute BackupTask: " + backupTask.getClass().getName();
                        console.printError(errorMessage);

                        // add backup task to runnable
                        if (DriverContext.isLaunchable(backupTask)) {
                            driverCxt.addToRunnable(backupTask);
                        }
                        continue;

                    } else {
                        setErrorMsgAndDetail(exitVal, result.getTaskError(), tsk);
                        invokeFailureHooks(perfLogger,
                                           hookContext,
                                           errorMessage + Strings.nullToEmpty(tsk.getDiagnosticsMessage()),
                                           result.getTaskError());
                        SQLState = "08S01";
                        console.printError(errorMessage);
                        driverCxt.shutdown();
                        // in case we decided to run everything in local mode, restore the
                        // the jobtracker setting to its initial value
                        ctx.restoreOriginalTracker();
                        return exitVal;
                    }
                }

                driverCxt.finished(tskRun);

                if (SessionState.get() != null) {
                    SessionState.get()
                            .getHiveHistory()
                            .setTaskProperty(queryId, tsk.getId(), Keys.TASK_RET_CODE, String.valueOf(exitVal));
                    SessionState.get().getHiveHistory().endTask(queryId, tsk);
                }

                if (tsk.getChildTasks() != null) {
                    for (Task<? extends Serializable> child : tsk.getChildTasks()) {
                        if (DriverContext.isLaunchable(child)) {
                            driverCxt.addToRunnable(child);
                        }
                    }
                }
            }
            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.RUN_TASKS);

            // in case we decided to run everything in local mode, restore the
            // the jobtracker setting to its initial value
            ctx.restoreOriginalTracker();

            if (driverCxt.isShutdown()) {
                LOG.info(LOG_GY_PREFIX + " \t HY008 " + driverCxt.isShutdown());
                SQLState = "HY008";
                errorMessage = "FAILED: Operation cancelled";
                invokeFailureHooks(perfLogger, hookContext, errorMessage, null);
                console.printError(errorMessage);
                return 1000;
            }

            // remove incomplete outputs.
            // 删除没有完成的输出.
            // Some incomplete outputs may be added at the beginning, for eg: for dynamic partitions.
            // remove them
            // 刚开始添加了一些没有完成的输出路径,比如动态分区的. 删除这些输出
            HashSet<WriteEntity> remOutputs = new LinkedHashSet<WriteEntity>();
            for (WriteEntity output : plan.getOutputs()) {
                if (!output.isComplete()) {
                    LOG.info(LOG_GY_PREFIX + " \toutput-name = " + output.getType().name() + ", " + output.getD()
                            .toString());
                    remOutputs.add(output);
                }
            }

            for (WriteEntity output : remOutputs) {
                plan.getOutputs().remove(output);
            }

            hookContext.setHookType(HookContext.HookType.POST_EXEC_HOOK);
            // Get all the post execution hooks and execute them.
            // 得到所有提交的 Execution-Hooks 并且执行他们.
            for (Hook peh : getHooks(HiveConf.ConfVars.POSTEXECHOOKS)) {
                if (peh instanceof ExecuteWithHookContext) {
                    perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.POST_HOOK + peh.getClass().getName());

                    ((ExecuteWithHookContext) peh).run(hookContext);

                    perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.POST_HOOK + peh.getClass().getName());
                } else if (peh instanceof PostExecute) {
                    perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.POST_HOOK + peh.getClass().getName());

                    ((PostExecute) peh).run(SessionState.get(),
                                            plan.getInputs(),
                                            plan.getOutputs(),
                                            (SessionState.get() != null ? SessionState.get().getLineageState().getLineageInfo() : null),
                                            Utils.getUGI());

                    perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.POST_HOOK + peh.getClass().getName());
                }
            }

            if (SessionState.get() != null) {
                SessionState.get().getHiveHistory().setQueryProperty(queryId, Keys.QUERY_RET_CODE, String.valueOf(0));
                SessionState.get().getHiveHistory().printRowCount(queryId);
            }
            releasePlan(plan);
        } catch (CommandNeedRetryException e) {
            executionError = true;
            throw e;
        } catch (Exception e) {
            executionError = true;
            if (isInterrupted()) {
                return handleInterruption("during query execution: \n" + e.getMessage());
            }

            ctx.restoreOriginalTracker();
            if (SessionState.get() != null) {
                SessionState.get().getHiveHistory().setQueryProperty(queryId, Keys.QUERY_RET_CODE, String.valueOf(12));
            }
            // TODO: do better with handling types of Exception here
            errorMessage = "FAILED: Hive Internal Error: " + Utilities.getNameMessage(e);
            if (hookContext != null) {
                try {
                    invokeFailureHooks(perfLogger, hookContext, errorMessage, e);
                } catch (Exception t) {
                    LOG.warn("Failed to invoke failure hook", t);
                }
            }
            SQLState = "08S01";
            downstreamError = e;
            console.printError(errorMessage + "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
            return (12);
        } finally {
            if (SessionState.get() != null) {
                SessionState.get().getHiveHistory().endQuery(queryId);
            }
            if (noName) {
                conf.set(MRJobConfig.JOB_NAME, "");
            }
            double duration = perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.DRIVER_EXECUTE) / 1000.00;

            ImmutableMap<String, Long> executionHMSTimings = dumpMetaCallTimingWithoutEx("execution");
            queryDisplay.setHmsTimings(QueryDisplay.Phase.EXECUTION, executionHMSTimings);

            Map<String, MapRedStats> stats = SessionState.get().getMapRedStats();
            if (stats != null && !stats.isEmpty()) {
                long totalCpu = 0;
                console.printInfo("MapReduce Jobs Launched: ");
                for (Map.Entry<String, MapRedStats> entry : stats.entrySet()) {
                    console.printInfo("Stage-" + entry.getKey() + ": " + entry.getValue());
                    totalCpu += entry.getValue().getCpuMSec();
                }
                console.printInfo("Total MapReduce CPU Time Spent: " + Utilities.formatMsecToStr(totalCpu));
            }
            boolean isInterrupted = isInterrupted();
            if (isInterrupted && !deferClose) {
                closeInProcess(true);
            }
            stateLock.lock();
            try {
                if (isInterrupted) {
                    if (!deferClose) {
                        driverState = DriverState.ERROR;
                    }
                } else {
                    driverState = executionError ? DriverState.ERROR : DriverState.EXECUTED;
                }
            } finally {
                stateLock.unlock();
            }
            if (isInterrupted) {
                LOG.info(LOG_GY_PREFIX + " \tExecuting command(queryId=" + queryId + ") has been interrupted after "
                                 + duration + " seconds");
            } else {
                LOG.info(LOG_GY_PREFIX + " \tCompleted executing command(queryId=" + queryId + "); Time taken: "
                                 + duration + " seconds");
            }
        }

        if (console != null) {
            console.printInfo(LOG_GY_PREFIX + " \t ---------------------------------  OK");
        }

        return (0);
    }

    private void releasePlan(QueryPlan plan) {
        // Plan maybe null if Driver.close is called in another thread for the same Driver object
        // 如果其他相同的Driver Object 线程调用了 Driver.close()方法，执行计划可能是空.
        stateLock.lock();
        try {
            if (plan != null) {
                plan.setDone();
                if (SessionState.get() != null) {
                    try {
                        SessionState.get().getHiveHistory().logPlanProgress(plan);
                    } catch (Exception e) {
                        // Log and ignore
                        LOG.warn("Could not log query plan progress", e);
                    }
                }
            }
        } finally {
            stateLock.unlock();
        }
    }

    private void setQueryDisplays(List<Task<? extends Serializable>> tasks) {
        if (tasks != null) {
            for (Task<? extends Serializable> task : tasks) {
                task.setQueryDisplay(queryDisplay);
                setQueryDisplays(task.getDependentTasks());
            }
        }
    }

    private void logMrWarning(int mrJobs) {
        if (mrJobs <= 0 || !("mr".equals(HiveConf.getVar(conf, ConfVars.HIVE_EXECUTION_ENGINE)))) {
            return;
        }
        String warning = HiveConf.generateMrDeprecationWarning();
        LOG.warn(warning);
        warning = "WARNING: " + warning;
        console.printInfo(warning);
        // Propagate warning to beeline via operation log.
        OperationLog ol = OperationLog.getCurrentOperationLog();
        if (ol != null) {
            ol.writeOperationLog(LoggingLevel.EXECUTION, warning + "\n");
        }
    }

    private void setErrorMsgAndDetail(int exitVal,
                                      Throwable downstreamError,
                                      Task tsk) {
        this.downstreamError = downstreamError;
        errorMessage = "FAILED: Execution Error, return code " + exitVal + " from " + tsk.getClass().getName();
        if (downstreamError != null) {
            // here we assume that upstream code may have parametrized the msg from ErrorMsg
            // so we want to keep it
            errorMessage += ". " + downstreamError.getMessage();
        } else {
            ErrorMsg em = ErrorMsg.getErrorMsg(exitVal);
            if (em != null) {
                errorMessage += ". " + em.getMsg();
            }
        }
    }

    private void invokeFailureHooks(PerfLogger perfLogger,
                                    HookContext hookContext,
                                    String errorMessage,
                                    Throwable exception) throws Exception {
        hookContext.setHookType(HookContext.HookType.ON_FAILURE_HOOK);
        hookContext.setErrorMessage(errorMessage);
        hookContext.setException(exception);
        // Get all the failure execution hooks and execute them.
        for (Hook ofh : getHooks(HiveConf.ConfVars.ONFAILUREHOOKS)) {
            perfLogger.PerfLogBegin(CLASS_NAME, PerfLogger.FAILURE_HOOK + ofh.getClass().getName());

            ((ExecuteWithHookContext) ofh).run(hookContext);

            perfLogger.PerfLogEnd(CLASS_NAME, PerfLogger.FAILURE_HOOK + ofh.getClass().getName());
        }
    }

    /**
     * Launches a new task
     *
     * @param tsk     task being launched
     * @param queryId Id of the query containing the task
     * @param noName  whether the task has a name set
     * @param jobname name of the task, if it is a map-reduce job
     * @param jobs    number of map-reduce jobs
     * @param cxt     the driver context
     */
    private TaskRunner launchTask(Task<? extends Serializable> tsk,
                                  String queryId,
                                  boolean noName,
                                  String jobname,
                                  int jobs,
                                  DriverContext cxt) throws HiveException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " \t 000 launchTask(Task,String,boolean,String,int,DriverContext) ");
        LOG.info(LOG_GY_PREFIX + " 1 \t launchTask queryId = " + queryId);
        LOG.info(LOG_GY_PREFIX + " 2 \t launchTask noName = " + noName);
        LOG.info(LOG_GY_PREFIX + " 3 \t launchTask jobname = " + jobname);
        LOG.info(LOG_GY_PREFIX + " 4 \t launchTask jobs = " + jobs);
        LOG.info(LOG_GY_PREFIX + " 5 \t launchTask cxt = " + cxt);

        if (SessionState.get() != null) {
            SessionState.get().getHiveHistory().startTask(queryId, tsk, tsk.getClass().getName());
        }

        if (tsk.isMapRedTask() && !(tsk instanceof ConditionalTask)) {
            if (noName) {
                conf.set(MRJobConfig.JOB_NAME, jobname + "(" + tsk.getId() + ")");
            }
            conf.set("mapreduce.workflow.node.name", tsk.getId());
            Utilities.setWorkflowAdjacencies(conf, plan);
            cxt.incCurJobNo(1);
            console.printInfo("Launching Job " + cxt.getCurJobNo() + " out of " + jobs);
        }

        tsk.initialize(queryState, plan, cxt, ctx.getOpContext());
        TaskResult tskRes = new TaskResult();
        TaskRunner tskRun = new TaskRunner(tsk, tskRes);

        cxt.launching(tskRun);
        // Launch Task
        if (HiveConf.getBoolVar(conf, HiveConf.ConfVars.EXECPARALLEL) && tsk.isMapRedTask()) {
            // Launch it in the parallel mode, as a separate thread only for MR tasks
            if (LOG.isInfoEnabled()) {
                LOG.info(LOG_GY_PREFIX + " Starting task [" + tsk + "] in parallel");
            }
            tskRun.setOperationLog(OperationLog.getCurrentOperationLog());
            tskRun.start();
        } else {
            if (LOG.isInfoEnabled()) {
                LOG.info(LOG_GY_PREFIX + " Starting task [" + tsk + "] in serial mode");
            }
            tskRun.runSequential();
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " \t 999 launchTask(Task,String,boolean,String,int,DriverContext) ");
        return tskRun;
    }

    public boolean isFetchingTable() {
        return fetchTask != null;
    }

    /**
     * 被2个地方调用
     * 1. CliDriver.processLocalCmd -> qp.getResults(res)
     * 2. SQLOperation.getNextRowSet -> driver.getResults(convey)
     *
     * @param res
     *
     * @return
     *
     * @throws IOException
     * @throws CommandNeedRetryException
     */
    @SuppressWarnings("unchecked")
    public boolean getResults(List res) throws IOException, CommandNeedRetryException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 getResults(List res) ");
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " \t 0 getResults(List res) res.size = " + res.size());
        if (driverState == DriverState.DESTROYED || driverState == DriverState.CLOSED) {
            throw new IOException("FAILED: query has been cancelled, closed, or destroyed.");
        }

        LOG.info(LOG_GY_PREFIX + " \t getResults(List res) ---------------------------------------------------------->>>>>>>>>> 111");
        LOG.info(LOG_GY_PREFIX + " \t getResults(List res) exec.model = " + HiveConf.getVar(conf, ConfVars.GUYUE_EXEC_MODEL));
        LOG.info(LOG_GY_PREFIX + " \t getResults(List res) ---------------------------------------------------------->>>>>>>>>> 222");
        /** 2018-05-28 by lipeng 处理 hive.guyue.exec.model=explain 并且 select * from a limit 10; 的情况 */
        if ("explain".equalsIgnoreCase(HiveConf.getVar(conf, ConfVars.GUYUE_EXEC_MODEL))) {
            LOG.info(LOG_GY_PREFIX + " \t getResults(List res) --------------------------->>>>>>>>>> 333 GUYUE_EXEC_MODEL_EXPLAIN_RESULT = " + this.conf.getVar(ConfVars.GUYUE_EXEC_MODEL_EXPLAIN_RESULT));
            String result = this.conf.getVar(ConfVars.GUYUE_EXEC_MODEL_EXPLAIN_RESULT);
            if (StringUtils.isEmpty(result)) {
                return false;
            }

            LOG.info(LOG_GY_PREFIX + " \t getResults(List res) ---------------------------------------------------------->>>>>>>>>> 444");
            LOG.info(LOG_GY_PREFIX + " \t getResults(List res) explain-result = " + result);
            LOG.info(LOG_GY_PREFIX + " \t getResults(List res) ---------------------------------------------------------->>>>>>>>>> 555");
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999-explain getResults(List res) ");
            res.add(new Object[]{result});

            // release resource
            this.conf.setVar(ConfVars.GUYUE_EXEC_MODEL_EXPLAIN_RESULT, "");
            return true;
        }

        if (isFetchingTable()) {
            LOG.info(LOG_GY_PREFIX + " \t 1 getResults(List res) isFetchingTable = " + isFetchingTable());
            /**
             * If resultset serialization to thrift object is enabled, and if the destination table is
             * indeed written using ThriftJDBCBinarySerDe, read one row from the output sequence file,
             * since it is a blob of row batches.
             */
            if (fetchTask.getWork().isUsingThriftJDBCBinarySerDe()) {
                maxRows = 1;
            }
            LOG.info(LOG_GY_PREFIX + " \t 2 getResults(List res) maxRows = " + maxRows);
            fetchTask.setMaxRows(maxRows);
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999-isFetchingTable getResults(List res) ");
            return fetchTask.fetch(res);
        }

        LOG.info(LOG_GY_PREFIX + " \t 3 getResults(List res) maxRows = " + maxRows);
        if (resStream == null) {
            resStream = ctx.getStream();
        }

        LOG.info(LOG_GY_PREFIX + " \t 4 getResults(List res) resStream = " + resStream);
        if (resStream == null) {
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999-resStream-null getResults(List res) ");
            return false;
        }

        LOG.info(LOG_GY_PREFIX + " \t 5 getResults(List res) resStream = " + resStream);

        int numRows = 0;
        String row = null;
        LOG.info(LOG_GY_PREFIX + " \t 6 getResults(List res) maxRows = " + maxRows);

        while (numRows < maxRows) {
            if (resStream == null) {
                return numRows > 0;
            }

            bos.reset();
            Utilities.StreamStatus ss;
            try {
                ss = Utilities.readColumn(resStream, bos);
                if (bos.getLength() > 0) {
                    row = new String(bos.getData(), 0, bos.getLength(), "UTF-8");
                } else if (ss == Utilities.StreamStatus.TERMINATED) {
                    row = new String();
                }

                LOG.info(LOG_GY_PREFIX + " \t\t\t\t - getResults(List res) row = " + row);
                if (row != null) {
                    numRows++;
                    res.add(row);
                }
                LOG.info(LOG_GY_PREFIX + " \t\t\t\t - getResults(List res) res.size = " + res.size());
                row = null;
            } catch (IOException e) {
                console.printError("FAILED: Unexpected IO exception : " + e.getMessage());
                return false;
            }

            if (ss == Utilities.StreamStatus.EOF) {
                LOG.info(LOG_GY_PREFIX + " \t 7 getResults(List res) ss = EOF, numRows=" + numRows);
                resStream = ctx.getStream();
            }
        }

        LOG.info(LOG_GY_PREFIX + " \t 7 getResults(List res) numRows = " + numRows);
        LOG.info(LOG_GY_PREFIX + " \t 8 getResults(List res) res.size = " + res.size());
        LOG.info(LOG_GY_PREFIX + " \t 9 getResults(List res) res = " + res);

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 getResults(List res) ");
        return true;
    }

    public void resetFetch() throws IOException {
        if (driverState == DriverState.DESTROYED || driverState == DriverState.CLOSED) {
            throw new IOException("FAILED: driver has been cancelled, closed or destroyed.");
        }
        if (isFetchingTable()) {
            try {
                fetchTask.clearFetch();
            } catch (Exception e) {
                throw new IOException("Error closing the current fetch task", e);
            }
            // FetchTask should not depend on the plan.
            fetchTask.initialize(queryState, null, null, ctx.getOpContext());
        } else {
            ctx.resetStream();
            resStream = null;
        }
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    // DriverContext could be released in the query and close processes at same
    // time, which needs to be thread protected.
    // DriverContext 资源被释同时关闭进程, 这个过程需要线程保护(同步)
    private void releaseDriverContext() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 releaseDriverContext() ");
        stateLock.lock();
        try {
            if (driverCxt != null) {
                driverCxt.shutdown();
                driverCxt = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception while shutting down the task runner", e);
        } finally {
            stateLock.unlock();
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 releaseDriverContext() ");
    }

    private void releasePlan() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 releasePlan() ");
        try {
            if (plan != null) {
                fetchTask = plan.getFetchTask();
                if (fetchTask != null) {
                    fetchTask.setDriverContext(null);
                    fetchTask.setQueryPlan(null);
                }
            }
            plan = null;
        } catch (Exception e) {
            LOG.debug("Exception while clearing the Fetch task", e);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 releasePlan() ");
    }

    private void releaseContext() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 releaseContext() ");
        try {
            if (ctx != null) {
                ctx.clear();
                if (ctx.getHiveLocks() != null) {
                    hiveLocks.addAll(ctx.getHiveLocks());
                    ctx.setHiveLocks(null);
                }
                ctx = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception while clearing the context ", e);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 999releaseContext() ");
    }

    private void releaseResStream() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 releaseResStream() ");
        try {
            if (resStream != null) {
                ((FSDataInputStream) resStream).close();
                resStream = null;
            }
        } catch (Exception e) {
            LOG.debug(" Exception while closing the resStream ", e);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 releaseResStream() ");
    }

    private void releaseFetchTask() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 releaseFetchTask() ");
        try {
            if (fetchTask != null) {
                fetchTask.clearFetch();
                fetchTask = null;
            }
        } catch (Exception e) {
            LOG.debug(" Exception while clearing the FetchTask ", e);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 releaseFetchTask() ");
    }

    // Close and release resources within a running query process. Since it runs under
    // driver state COMPILING, EXECUTING or INTERRUPT, it would not have race condition
    // with the releases probably running in the other closing thread.<br/>
    // 在一个查询进程中关闭并释放资源。由于它在Driver的 COMPILING, EXECUTING, 或 INTERRUPT 状态下运行
    private int closeInProcess(boolean destroyed) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 closeInProcess(boolean) ");
        releaseDriverContext();
        releasePlan();
        releaseFetchTask();
        releaseResStream();
        releaseContext();
        if (SessionState.get() != null) {
            SessionState.get().getLineageState().clear();
        }
        if (destroyed) {
            if (!hiveLocks.isEmpty()) {
                try {
                    releaseLocksAndCommitOrRollback(false, null);
                } catch (LockException e) {
                    LOG.warn("Exception when releasing locking in destroy: " + e.getMessage());
                }
            }
            ShutdownHookManager.removeShutdownHook(shutdownRunner);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 closeInProcess(boolean) ");
        return 0;
    }

    // is called to stop the query if it is running, clean query results, and release resources.
    public int close() {
        stateLock.lock();
        try {
            releaseDriverContext();
            if (driverState == DriverState.COMPILING || driverState == DriverState.EXECUTING
                    || driverState == DriverState.INTERRUPT) {
                driverState = DriverState.INTERRUPT;
                return 0;
            }
            releasePlan();
            releaseFetchTask();
            releaseResStream();
            releaseContext();
            driverState = DriverState.CLOSED;
        } finally {
            stateLock.unlock();
        }
        if (SessionState.get() != null) {
            SessionState.get().getLineageState().clear();
        }
        return 0;
    }

    // is usually called after close() to commit or rollback a query and end the driver life cycle.
    // do not understand why it is needed and wonder if it could be combined with close.
    public void destroy() {
        stateLock.lock();
        try {
            // in the cancel case where the driver state is INTERRUPTED, destroy will be deferred to
            // the query process
            if (driverState == DriverState.DESTROYED || driverState == DriverState.INTERRUPT) {
                return;
            } else {
                driverState = DriverState.DESTROYED;
            }
        } finally {
            stateLock.unlock();
        }
        if (!hiveLocks.isEmpty()) {
            try {
                releaseLocksAndCommitOrRollback(false, null);
            } catch (LockException e) {
                LOG.warn("Exception when releasing locking in destroy: " + e.getMessage());
            }
        }
        ShutdownHookManager.removeShutdownHook(shutdownRunner);
    }

    public org.apache.hadoop.hive.ql.plan.api.Query getQueryPlan() throws IOException {
        return plan.getQueryPlan();
    }

    public String getErrorMsg() {
        return errorMessage;
    }

    public QueryDisplay getQueryDisplay() {
        return queryDisplay;
    }

    /**
     * Set the HS2 operation handle's guid string
     *
     * @param opId base64 encoded guid string
     */
    public void setOperationId(String opId) {
        this.operationId = opId;
    }

    /**
     * Resets QueryState to get new queryId on Driver reuse.
     */
    public void resetQueryState() {
        // Note: Driver cleanup for reuse at this point is not very clear. The assumption here is that
        // repeated compile/execute calls create new contexts, plan, etc., so we don't need to worry
        // propagating queryState into those existing fields, or resetting them.
        releaseResources();
        this.queryState = new QueryState(queryState.getConf());
    }
}
