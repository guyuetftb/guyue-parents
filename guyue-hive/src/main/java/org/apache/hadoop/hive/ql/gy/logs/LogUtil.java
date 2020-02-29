package org.apache.hadoop.hive.ql.gy.logs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.QueryState;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.TableScanOperator;
import org.apache.hadoop.hive.ql.exec.tez.TezSessionState;
import org.apache.hadoop.hive.ql.hooks.Entity;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.metadata.AuthorizationException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.*;
import org.apache.hadoop.hive.ql.plan.TableScanDesc;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.tez.client.TezClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class LogUtil {

    private static final Logger MRYXBLG_COMMAND_LOGGER      = LoggerFactory.getLogger("MRYXBLG_COMMAND_LOGGER");
    private static final Logger MRYXBLG_COMMAND_AVRO_LOGGER = LoggerFactory.getLogger("MRYXBLG_COMMAND_AVRO_LOGGER");
    private static final Logger LOG                         = LoggerFactory.getLogger(LogUtil.class);

    /**
     * @param loggerType
     * @param conf
     * @param processor
     * @param queryState
     * @param command
     * @param modeType
     * @param location
     * @param ret
     */
    public static void logUserAction(String loggerType,
                                     Configuration conf,
                                     CommandProcessor processor,
                                     QueryState queryState,
                                     String command,
                                     String modeType,
                                     String location,
                                     int ret) {

        SessionState ss = SessionState.get();
        HiveConf hiveConf = ss.getConf();

        LOG.warn(" 000 logUserAction() Vars-userName = " + hiveConf.getVar(HiveConf.ConfVars.MRYXBLG_USER));
        LOG.warn(" 000 logUserAction() auth = " + hiveConf.getVar(HiveConf.ConfVars.MRYXBLG_AUTHORIZATION_ENABLED));
        LOG.warn(" 000 logUserAction() MRYXBLG_COMMAND_MONITORING = " + HiveConf.getBoolVar(hiveConf, HiveConf.ConfVars.MRYXBLG_COMMAND_MONITORING));

        if (HiveConf.getBoolVar(hiveConf, HiveConf.ConfVars.MRYXBLG_COMMAND_MONITORING)) {
            try {
                Gson gson = new GsonBuilder().serializeNulls().create();
                LogBean logBean = new LogBean();

                String realCommand = command;
                if (StringUtils.isEmpty(realCommand)) {
                    realCommand = hiveConf.getQueryString();
                }
                String commandClean = realCommand.replaceAll("\\r|\\n", " ");

                // SessionState.get().getTezSession().getSession().getAppMasterApplicationId()
                if (null != ss) {
                    logBean.setSessionId(ss.getSessionId());
                    logBean.setUserIpAddress(ss.getUserIpAddress());

                    TezSessionState tss = ss.getTezSession();
                    if (null != tss) {
                        TezClient tezClient = tss.getSession();
                        if (null != tezClient) {
                            logBean.setApplicationId(tezClient.getAppMasterApplicationId().toString());
                        }
                    }
                }

                logBean.setLoggerType(loggerType);
                logBean.setLoggerLocation(location);
                logBean.setCommand(command);
                logBean.setCommandClean(commandClean);
                logBean.setQueryString(hiveConf.getQueryString());
                logBean.setCurrentTime(String.valueOf(System.currentTimeMillis()));
                logBean.setBlgUserName(HiveConf.getVar(conf, HiveConf.ConfVars.MRYXBLG_USER, "null"));
                logBean.setUserName(StringUtils.isEmpty(ss.getUserName()) ? hiveConf.getUser() : ss.getUserName());
                logBean.setRet(String.valueOf(ret));
                if (null != queryState) {
                    logBean.setQueryId(queryState.getQueryId());
                } else {
                    logBean.setQueryId(QueryPlan.makeQueryId());  // query_id.
                }
                logBean.setModeType(modeType);
                logBean.setProcessorName((null != processor ? processor.getClass().getName() : null));
                logBean.setLastCommand(ss.getLastCommand());

                logBean.setMryxblgExecModel(String.valueOf(HiveConf.getVar(conf,
                                                                           HiveConf.ConfVars.MRYXBLG_EXEC_MODEL)));
                logBean.setMryxblgAuthorizationEnabled(String.valueOf(HiveConf.getBoolVar(conf,
                                                                                          HiveConf.ConfVars.MRYXBLG_AUTHORIZATION_ENABLED)));
                logBean.setMryxblgCommandMonitoring(String.valueOf(HiveConf.getBoolVar(conf,
                                                                                       HiveConf.ConfVars.MRYXBLG_COMMAND_MONITORING)));

                String gsonString = gson.toJson(logBean, LogBean.class);
                LOG.warn(" ---------------------------->   111");
                MRYXBLG_COMMAND_LOGGER.warn(gsonString);
                LOG.warn(" ---------------------------->   222");
                MRYXBLG_COMMAND_AVRO_LOGGER.warn(gsonString);
                LOG.warn(" ---------------------------->   333");
            } catch (Exception e) {
                MRYXBLG_COMMAND_LOGGER.error(" Writern Log Error : ", e.getStackTrace());
                MRYXBLG_COMMAND_AVRO_LOGGER.error(" Writern Log Error : ", e.getStackTrace());
                LOG.error(" Writern Log Error : ", e.getStackTrace());
            }
        }
        LOG.warn(" 999 logUserAction() MRYXBLG_COMMAND_MONITORING = " + HiveConf.getBoolVar(hiveConf, HiveConf.ConfVars.MRYXBLG_COMMAND_MONITORING));
    }

    /**
     * @param queryState
     * @param sem
     * @param command
     *
     * @throws HiveException
     * @throws JSONException
     * @throws AuthorizationException
     */
    public static void doExplain(QueryState queryState,
                                 BaseSemanticAnalyzer sem,
                                 String command)
            throws JSONException, AuthorizationException {
        LOG.info("-----------------------------------------------> HIVE_OPERATION = " + queryState.getHiveOperation());
        LOG.info("-----------------------------------------------> sem = " + sem.getClass().getName());
        LOG.info("-------------------------------> getAllInputs = " + sem.getAllInputs());
        LOG.info("-------------------------------> getAllOutputs = " + sem.getAllOutputs());
        LOG.info("-------------------------------> getColumnAccessInfo = " + sem.getColumnAccessInfo());
        LOG.info("-------------------------------> getInputs = " + sem.getInputs());

//        if (queryState.getHiveOperation().equals(HiveOperation.CREATETABLE_AS_SELECT) || queryState.getHiveOperation()
//                .equals(HiveOperation.QUERY)) {
        JSONObject result = new JSONObject();
        JSONObject source = new JSONObject();
        JSONObject target = new JSONObject();
        result.put("source", source);
        result.put("target", target);

        /**
         * -----------------------------------------------------------------
         * save target table info. <br/>
         * HiveOperation = [CREATETABLE_AS_SELECT or QUERY]
         * -----------------------------------------------------------------
         */
        HashSet<WriteEntity> output = sem.getOutputs();
        if (output != null && !output.isEmpty()) {

            JSONArray targetDatabaseJArr = new JSONArray();
            JSONArray targetTablesJArr = new JSONArray();
            target.put("target_database", targetDatabaseJArr);
            target.put("target_table", targetTablesJArr);

            for (WriteEntity writeEntity : output) {
                JSONObject tableJObj = new JSONObject();
                targetTablesJArr.put(tableJObj);

                String tabPath = null;
                try {
                    if (null != writeEntity.getLocation()) {
                        tabPath = writeEntity.getLocation().toString();
                        tableJObj.put("table_path", tabPath);
                    } else {
                        tableJObj.put("table_path", "");
                    }
                } catch (Exception e) {
                    throw new AuthorizationException(e);
                }

                Table metaTable = writeEntity.getTable();

                // TODO by lipeng 2018-07-11
                // FOR CASE: create databases;
                LOG.info("---------------------------- type = " + writeEntity.getTyp());
                if (Entity.Type.DATABASE.equals(writeEntity.getTyp())) {
                    tableJObj.put("database_name", writeEntity.getDatabase().getName());
                    targetDatabaseJArr.put(writeEntity.getDatabase().getName());
                }

                if (null != metaTable) {
                    String completeName = metaTable.getCompleteName();
                    String tabName = metaTable.getTableName();
                    String dbName = metaTable.getDbName();
                    String owner = metaTable.getOwner();
                    org.apache.hadoop.hive.metastore.api.Table apiTable = metaTable.getTTable();
                    int lastAccessTime = metaTable.getLastAccessTime();
                    int createTime = 0;
                    if (null != apiTable) {
                        createTime = apiTable.getCreateTime();
                    }

                    // save table info
                    tableJObj.put("table_name", tabName);
                    tableJObj.put("complete_table_name", completeName);
                    tableJObj.put("database_name", dbName);
                    tableJObj.put("owner", owner);
                    tableJObj.put("create_time", createTime);
                    tableJObj.put("last_access_time", lastAccessTime);
                    tableJObj.put("table_type", metaTable.getTableType().name());

                    // save database info
                    targetDatabaseJArr.put(dbName);
                }
            }
        }

        /**
         * -----------------------------------------------------------------
         * save select table info.
         * -----------------------------------------------------------------
         */
        HashSet<ReadEntity> inputs = sem.getInputs();
        if (inputs != null && inputs.size() > 0) {

            // initialize JSON Object.
            JSONArray sourceDatabaseJArr = new JSONArray();
            JSONArray sourceTablesJArr = new JSONArray();
            source.put("source_database", sourceDatabaseJArr);
            source.put("source_table", sourceTablesJArr);

            if (sem instanceof DDLSemanticAnalyzer) {
                DDLSemanticAnalyzer ddlSemanticAnalyzer = (DDLSemanticAnalyzer) sem;

                if (null != inputs && inputs.size() > 0) {
                    for (ReadEntity readEntity : inputs) {
                        Table metaDataTable = readEntity.getTable();
                        String tableName = null;
                        String tableType = null;
                        String databaseName = null;
                        String completeName = null;
                        String tablePath = null;
                        int lastAccessTime = 0;
                        boolean partitionedTable = false;

                        int createTime = -1;
                        if (null != metaDataTable) {
                            tableName = metaDataTable.getTableName();
                            databaseName = metaDataTable.getDbName();
                            completeName = metaDataTable.getCompleteName();
                            createTime = metaDataTable.getTTable().getCreateTime();
                            tablePath = metaDataTable.getPath().toUri().toString();
                            partitionedTable = metaDataTable.isPartitioned();
                            tableType = metaDataTable.getTableType().name();
                            lastAccessTime = metaDataTable.getLastAccessTime();
                        }

                        sourceDatabaseJArr.put(databaseName);

                        JSONObject tableJObj = new JSONObject();
                        tableJObj.put("table_name", tableName);
                        tableJObj.put("complete_table_name", completeName);
                        tableJObj.put("database_name", databaseName);
                        tableJObj.put("is_partitioned", partitionedTable);
                        tableJObj.put("table_path", tablePath);
                        tableJObj.put("last_access_time", lastAccessTime);
                        tableJObj.put("create_time", createTime);
                        tableJObj.put("table_type", tableType);

                        sourceTablesJArr.put(tableJObj);
                    }
                }
            } else if (sem instanceof SemanticAnalyzer) {

                SemanticAnalyzer semanticAnalyzer = (SemanticAnalyzer) sem;
                ParseContext parseContext = semanticAnalyzer.getParseContext();
                Map<String, TableScanOperator> tsoTopMap = parseContext.getTopOps();

                // for----------- Begin FOR
                for (Map.Entry<String, TableScanOperator> tabNameTabOperEntry : tsoTopMap.entrySet()) {

                    Operator<? extends Serializable> topOp = tabNameTabOperEntry.getValue();

                    if (topOp instanceof TableScanOperator) {
                        String tableName = null;
                        String databaseName = null;
                        String completeName = null;
                        String tablePath = null;
                        String tableType = null;
                        boolean partitionedTable = false;
                        int createTime = -1;
                        int lastAccessTime = -1;

                        List<Integer> neededColumnIds = null;
                        List<FieldSchema> fieldSchemaList = null;
                        List<String> fieldNameList = null;

                        TableScanOperator tableScanOp = (TableScanOperator) topOp;
                        TableScanDesc tabScanDesc = tableScanOp.getConf();
                        Table tableMetadata = tabScanDesc.getTableMetadata();


                        tableName = tableMetadata.getTTable().getTableName();
                        databaseName = tableMetadata.getTTable().getDbName();

                        // source data_bases
                        sourceDatabaseJArr.put(databaseName);

                        createTime = tableMetadata.getTTable().getCreateTime();
                        completeName = tableMetadata.getCompleteName();

                        neededColumnIds = tableScanOp.getNeededColumnIDs();
                        fieldSchemaList = tableMetadata.getCols();
                        fieldNameList = new ArrayList<String>();

                        if (neededColumnIds != null && neededColumnIds.size() > 0) {
                            for (int i = 0; i < neededColumnIds.size(); i++) {
                                fieldNameList.add(fieldSchemaList.get(neededColumnIds.get(i)).getName());
                            }
                        } else {
                            for (int i = 0; i < fieldSchemaList.size(); i++) {
                                fieldNameList.add(fieldSchemaList.get(i).getName());
                            }
                        }

                        tablePath = tabScanDesc.getTableMetadata().getPath().toUri().toString();
                        partitionedTable = tableMetadata.isPartitioned();
                        lastAccessTime = tabScanDesc.getTableMetadata().getLastAccessTime();
                        tableType = tabScanDesc.getTableMetadata().getTableType().name();

                        JSONObject tableJObj = new JSONObject();
                        tableJObj.put("table_name", tableName);
                        tableJObj.put("complete_table_name", completeName);
                        tableJObj.put("alias_name", tabScanDesc.getAlias());
                        tableJObj.put("database_name", databaseName);
                        tableJObj.put("access_column_names", tabScanDesc.getNeededColumns());
                        tableJObj.put("access_column_ids", tabScanDesc.getNeededColumnIDs());
                        tableJObj.put("is_partitioned", partitionedTable);
                        tableJObj.put("table_path", tablePath);
                        tableJObj.put("last_access_time", lastAccessTime);
                        tableJObj.put("create_time", createTime);
                        tableJObj.put("table_type", tableType);
                        sourceTablesJArr.put(tableJObj);
                    }
                }
            }
        }

        // output result

        /**
         * -----------------------------------------------------------------
         * return json information
         * -----------------------------------------------------------------
         */
        LOG.info("-------------------------------> doExplain() -------------------------------------------------------------------- ");
        LOG.info("-------------------------------> doExplain() MRYXBLG_EXEC_MODEL_EXPLAIN_RESULT 11111 = " + queryState.getConf().getVar(HiveConf.ConfVars.MRYXBLG_EXEC_MODEL_EXPLAIN_RESULT));
        LOG.info("-------------------------------> doExplain() -------------------------------------------------------------------- ");
        LOG.info("");
        LOG.info("");
        LOG.info("");
        LOG.info("");
        LOG.info("");
        LOG.info("");
        LOG.info("-------------------------------> doExplain() -------------------------------------------------------------------- ");
        queryState.getConf().setVar(HiveConf.ConfVars.MRYXBLG_EXEC_MODEL_EXPLAIN_RESULT, result.toString());
        LOG.info("-------------------------------> doExplain() MRYXBLG_EXEC_MODEL_EXPLAIN_RESULT 22222 = " + queryState.getConf().getVar(HiveConf.ConfVars.MRYXBLG_EXEC_MODEL_EXPLAIN_RESULT));
        LOG.info("-------------------------------> doExplain() -------------------------------------------------------------------- ");

        // LOG.warn(" explain-content = " + result.toString());
        // System.out.println(" System-explain = " + result.toString());

//        }
    }
}
