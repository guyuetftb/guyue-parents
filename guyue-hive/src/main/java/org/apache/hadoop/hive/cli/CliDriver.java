/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.cli;

import static org.apache.hadoop.util.StringUtils.stringifyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Splitter;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.history.FileHistory;
import jline.console.history.History;
import jline.console.history.PersistentHistory;
import jline.console.completer.StringsCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.ArgumentCompleter.ArgumentDelimiter;
import jline.console.completer.ArgumentCompleter.AbstractArgumentDelimiter;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.HiveInterruptUtils;
import org.apache.hadoop.hive.common.LogUtils;
import org.apache.hadoop.hive.common.LogUtils.LogInitializationException;
import org.apache.hadoop.hive.common.cli.ShellCmdExecutor;
import org.apache.hadoop.hive.common.io.CachingPrintStream;
import org.apache.hadoop.hive.common.io.FetchConverter;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveVariableSource;
import org.apache.hadoop.hive.conf.Validator;
import org.apache.hadoop.hive.conf.VariableSubstitution;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.ql.CommandNeedRetryException;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.exec.FunctionRegistry;
import org.apache.hadoop.hive.ql.exec.mr.HadoopJobExecHelper;
import org.apache.hadoop.hive.ql.exec.tez.TezJobExecHelper;
import org.apache.hadoop.hive.ql.exec.tez.TezSessionState;
import org.apache.hadoop.hive.ql.gy.logs.LogUtil;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.io.IOUtils;
import org.apache.hive.common.util.ShutdownHookManager;
import org.apache.tez.client.TezClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * CliDriver.
 */
public class CliDriver {

    private static final Logger LOG                    = LoggerFactory.getLogger(CliDriver.class.getName());
    static final         String LOG_GY_PREFIX          = " MY_TEST .... ";
    static final         String LOG_GY_BEGIN           = " Beginninggggggggggg ";
    static final         String LOG_GY_END             = " Endingggggggggggggg ";
    private static final Logger GUYUE_COMMAND_LOGGER = LoggerFactory.getLogger("GUYUE_COMMAND_LOGGER");

    public static       String prompt                        = null;
    public static       String prompt2                       = null; // when ';' is not yet seen. 当一直没有遇到 "(分号);"时使用
    public static final int    LINES_TO_FETCH                = 40; // number of lines to fetch in batch from remote hive server
    public static final int    DELIMITED_CANDIDATE_THRESHOLD = 10;

    public static final String HIVERCFILE = ".hiverc";

    private final LogHelper     console;
    protected     ConsoleReader reader;
    private       Configuration conf;

    public CliDriver() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 CliDriver()  ");
        SessionState ss = SessionState.get();
        conf = (ss != null) ? ss.getConf() : new Configuration();
        LOG.info(LOG_GY_PREFIX + " \t CliDriver inited with classpath {}", System.getProperty("java.class.path"));
        console = new LogHelper(LOG);
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 CliDriver()  ");
    }

    public int processCmd(String cmd) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 processCmd(String)");

        LOG.info(LOG_GY_PREFIX + " \t 0 processCmd(String) command = " + cmd);
        CliSessionState ss = (CliSessionState) SessionState.get();
        ss.setLastCommand(cmd);

        // 更新线程名称.
        ss.updateThreadName();

        // Flush the print stream, so it doesn't include output from the last command
        // 刷新屏幕输出, 所以他不包含上条命令的 输出.
        ss.err.flush();
        String cmd_trimmed = cmd.trim();
        String[] tokens = tokenizeCmd(cmd_trimmed);
        int ret = 0;

        if (cmd_trimmed.toLowerCase().equals("quit") || cmd_trimmed.toLowerCase().equals("exit")) {
            LOG.info(LOG_GY_PREFIX + " \t 1 processCmd(String)  cmd = " + cmd);

            // if we have come this far - either the previous commands
            // are all successful or this is command line. in either case
            // this counts as a successful run

            // 2018-05-17 by lipeng
            // logUserAction = [quit, exit]
            LogUtil.logUserAction("0", conf, null, null, cmd, "Exit_Mode", "processCmd", 0);

            ss.close();
            System.exit(0);

        } else if (tokens[0].equalsIgnoreCase("source")) {
            LOG.info(LOG_GY_PREFIX + " \t 2 processCmd(String)  cmd = " + cmd);
            String cmd_1 = getFirstCmd(cmd_trimmed, tokens[0].length());
            cmd_1 = new VariableSubstitution(new HiveVariableSource() {

                @Override
                public Map<String, String> getHiveVariable() {
                    return SessionState.get().getHiveVariables();
                }
            }).substitute(ss.getConf(), cmd_1);

            File sourceFile = new File(cmd_1);
            if (!sourceFile.isFile()) {
                console.printError("File: " + cmd_1 + " is not a file.");
                ret = 1;
            } else {
                try {
                    ret = processFile(cmd_1);
                } catch (IOException e) {
                    console.printError("Failed processing file " + cmd_1 + " " + e.getLocalizedMessage(), stringifyException(e));
                    ret = 1;
                }
            }
            // 2018-05-17 by lipeng
            // logUserAction = [source]
            LogUtil.logUserAction("0", conf, null, null, cmd, "Source_Mode", "processCmd", ret);
        } else if (cmd_trimmed.startsWith("!")) {

            LOG.info(LOG_GY_PREFIX + " \t 3 processCmd(String)  cmd = " + cmd);
            String shell_cmd = cmd_trimmed.substring(1);
            shell_cmd = new VariableSubstitution(new HiveVariableSource() {

                @Override
                public Map<String, String> getHiveVariable() {
                    return SessionState.get().getHiveVariables();
                }
            }).substitute(ss.getConf(), shell_cmd);

            // shell_cmd = "/bin/bash -c \'" + shell_cmd + "\'";
            try {
                ShellCmdExecutor executor = new ShellCmdExecutor(shell_cmd, ss.out, ss.err);
                ret = executor.execute();
                if (ret != 0) {
                    console.printError("Command failed with exit code = " + ret);
                }
            } catch (Exception e) {
                console.printError("Exception raised from Shell command " + e.getLocalizedMessage(), stringifyException(e));
                ret = 1;
            }
            // 2018-05-17 by lipeng
            // logUserAction = [!]
            LogUtil.logUserAction("0", conf, null, null, cmd, "Shell_Mode", "processCmd", ret);
        } else { // local mode
            try {
                LOG.info(LOG_GY_PREFIX + " \t 4 processCmd(String)  cmd = " + cmd);
                CommandProcessor proc = CommandProcessorFactory.get(tokens, (HiveConf) conf);
                ret = processLocalCmd(cmd, proc, ss);
            } catch (SQLException e) {
                console.printError("Failed processing command " + tokens[0] + " " + e.getLocalizedMessage(),
                                   org.apache.hadoop.util.StringUtils.stringifyException(e));
                ret = 1;
            }
        }
        LOG.info(LOG_GY_PREFIX + " \t 5 processCmd(String)  cmd = " + cmd);

        ss.resetThreadName();
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 processCmd(String) ");
        return ret;
    }

    /**
     * For testing purposes to inject Configuration dependency
     *
     * @param conf to replace default
     */
    void setConf(Configuration conf) {
        this.conf = conf;
    }

    /**
     * Extract and clean up the first command in the input.
     */
    private String getFirstCmd(String cmd,
                               int length) {
        return cmd.substring(length).trim();
    }

    private String[] tokenizeCmd(String cmd) {
        return cmd.split("\\s+");
    }

    int processLocalCmd(String cmd,
                        CommandProcessor proc,
                        CliSessionState ss) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " processLocalCmd(String,CommandProcessor,CliSessionState) ");
        LOG.info(LOG_GY_PREFIX + " \t 0 processLocalCmd(String,CommandProcessor,CliSessionState) command = " + cmd);

        int tryCount = 0;
        boolean needRetry;
        int ret = 0;

        do {
            try {
                needRetry = false;
                if (proc != null) {
                    if (proc instanceof Driver) {
                        /**
                         * Driver 中 记录 command.monitoring
                         */

                        LOG.info(LOG_GY_PREFIX + " \t 1-1 processLocalCmd(String,CommandProcessor,CliSessionState) ProcessCommand = Driver. ");

                        Driver qp = (Driver) proc;
                        LOG.info(LOG_GY_PREFIX + " \t 1-2 processLocalCmd(String,CommandProcessor,CliSessionState) Driver = " + qp);

                        PrintStream out = ss.out;
                        long start = System.currentTimeMillis();
                        if (ss.getIsVerbose()) {
                            out.println(cmd);
                        }
                        LOG.info(LOG_GY_PREFIX + " \t 1-3 processLocalCmd(String,CommandProcessor,CliSessionState) tryCount = " + tryCount);

                        qp.setTryCount(tryCount);

                        LOG.info(LOG_GY_PREFIX + " \t 1-4 processLocalCmd(String,CommandProcessor,CliSessionState) Driver.run ---->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ret = " + ret);

                        // TODO lipeng 2018-07-09
                        // 其实是调整用 Driver.run 方法.
                        try {
                            ret = qp.run(cmd).getResponseCode();
                        } catch (CommandNeedRetryException e) {
                            e.printStackTrace();
                        }

                        LOG.info(LOG_GY_PREFIX + " \t 1-5 processLocalCmd(String,CommandProcessor,CliSessionState) Driver.run ----<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ret = " + ret);

                        if (ret != 0) {
                            qp.close();
                            return ret;
                        }

                        /**
                         * 2018-05-28 by lipeng
                         */
                        // query has run capture the time
                        long end = System.currentTimeMillis();
                        double timeTaken = (end - start) / 1000.0;

                        // TODO lipeng 2018-07-09
                        //
                        // Driver.getResults 被2个地方调用
                        //  1. CliDriver.processLocalCmd -> qp.getResults(res)
                        //  2. SQLOperation.getNextRowSet -> driver.getResults(convey)
                        // 结果缓冲区, 每次读取一定数量的日志, 然后输出.
                        ArrayList<Object> res = new ArrayList<Object>();

                        if ("run".equalsIgnoreCase(HiveConf.getVar(conf, ConfVars.GUYUE_EXEC_MODEL))) {
                            LOG.info(LOG_GY_PREFIX + " \t 1-6 processLocalCmd(String,CommandProcessor,CliSessionState) Driver.run exec.model = run ");
                            printHeader(qp, out);
                        } else {
                            LOG.info(LOG_GY_PREFIX + " \t 1-6 processLocalCmd(String,CommandProcessor,CliSessionState) Driver.run exec.model = explain ");
                        }

                        // print the results
                        // 打印 查询结果.
                        int counter = 0;
                        try {
                            if (out instanceof FetchConverter) {
                                ((FetchConverter) out).fetchStarted();
                            }
                            while (qp.getResults(res)) {
                                if ("run".equalsIgnoreCase(HiveConf.getVar(conf, ConfVars.GUYUE_EXEC_MODEL))) {

                                    // guyue.exec.model=run
                                    for (Object r : res) {
                                        out.println("run    " + " \t " + (String) r);
                                    }
                                } else {

                                    // guyue.exec.model=explain
                                    // 2018-07-09   can't print Object[] directly.
                                    // explain          [Ljava.lang.Object;@1e5aacd9
                                    out.println("explain" + " \t " + ((Object[]) res.get(0))[0]);
                                }
                                // 累加输出数量
                                counter += res.size();
                                // 清空 输出缓冲区.
                                res.clear();
                                if (out.checkError()) {
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            console.printError("Failed with exception " + e.getClass().getName() + ":" + e.getMessage(), "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
                            ret = 1;
                        }

                        int cret = qp.close();
                        if (ret == 0) {
                            ret = cret;
                        }

                        if (out instanceof FetchConverter) {
                            ((FetchConverter) out).fetchFinished();
                        }

                        // 输出完成后, 在屏幕最后一行, 打印一共输出的条数，及 需要的时间.
                        console.printInfo(LOG_GY_PREFIX + " \t 1-5 Time taken: " + timeTaken + " seconds" + (counter == 0 ? "" : ", Fetched: " + counter + " row(s)"));
                    } else {
                        LOG.info(LOG_GY_PREFIX + " \t 2-0 processLocalCmd(String,CommandProcessor,CliSessionState) ProcessCommand = " + proc.getClass().getName());

                        String firstToken = tokenizeCmd(cmd.trim())[0];
                        String cmd_1 = getFirstCmd(cmd.trim(), firstToken.length());
                        LOG.info(LOG_GY_PREFIX + " \t 2-1 processLocalCmd(String,CommandProcessor,CliSessionState) firstToken = " + firstToken);
                        LOG.info(LOG_GY_PREFIX + " \t 2-2 processLocalCmd(String,CommandProcessor,CliSessionState) cmd_1 = " + cmd_1);
                        LOG.info(LOG_GY_PREFIX + " \t 2-3 processLocalCmd(String,CommandProcessor,CliSessionState) proc = " + proc.getClass().getName());

                        if (ss.getIsVerbose()) {
                            ss.out.println(firstToken + " " + cmd_1);
                        }

                        // 2018-05-17 by lipeng 执行各种命令.
                        CommandProcessorResponse res = proc.run(cmd_1);

                        // 2018-05-17 by lipeng
                        // logUserAction = [Driver Model]
                        LogUtil.logUserAction("0", conf, proc, null, cmd, "Local_Mode", "processLocalCmd", res.getResponseCode());

                        if (res.getResponseCode() != 0) {
                            ss.out.println("Query returned non-zero code: " + res.getResponseCode() + ", cause: " + res.getErrorMessage());
                        }
                        if (res.getConsoleMessages() != null) {
                            for (String consoleMsg : res.getConsoleMessages()) {
                                console.printInfo(consoleMsg);
                            }
                        }
                        ret = res.getResponseCode();
                        LOG.info(LOG_GY_PREFIX + " \t 2-4 processLocalCmd(String,CommandProcessor,CliSessionState) ret = " + ret);
                    }
                }
            } catch (CommandNeedRetryException e) {
                console.printInfo("Retry query with a different approach...");
                tryCount++;
                needRetry = true;
            }
        } while (needRetry);

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " processLocalCmd(String,CommandProcessor,CliSessionState)  cmd = " + cmd);
        return ret;
    }

    /**
     * If enabled and applicable to this command, print the field headers
     * for the output.
     *
     * @param qp  Driver that executed the command
     * @param out PrintStream which to send output to
     */
    private void printHeader(Driver qp,
                             PrintStream out) {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 printHeader(Driver,PrintStream)  ");
        /**
         * 2018-05-28 by lipeng
         * 处理:
         * hive.guyue.exec.model=explain
         * 并且
         * select * from a limit 10
         * 的情况
         */
        if ("explain".equalsIgnoreCase(HiveConf.getVar(conf, ConfVars.GUYUE_EXEC_MODEL))) {
            return;
        }

        List<FieldSchema> fieldSchemas = qp.getSchema().getFieldSchemas();
        LOG.info(LOG_GY_PREFIX + " \t 0 printHeader(Driver,PrintStream)  HIVE_CLI_PRINT_HEADER = " + HiveConf.getBoolVar(conf, HiveConf.ConfVars.HIVE_CLI_PRINT_HEADER));
        LOG.info(LOG_GY_PREFIX + " \t 1 printHeader(Driver,PrintStream)  fieldSchemas = " + (fieldSchemas != null));
        if (HiveConf.getBoolVar(conf, HiveConf.ConfVars.HIVE_CLI_PRINT_HEADER) && fieldSchemas != null) {
            // Print the column names
            boolean first_col = true;
            for (FieldSchema fs : fieldSchemas) {
                if (!first_col) {
                    out.print('\t');
                }
                out.print(fs.getName());
                first_col = false;
            }
            out.println();
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 printHeader(Driver,PrintStream) ");
    }

    public int processLine(String line) {
        return processLine(line, false);
    }

    /**
     * Processes a line of semicolon separated commands
     *
     * @param line              The commands to process
     * @param allowInterrupting When true the function will handle SIG_INT (Ctrl+C) by interrupting the processing and
     *                          returning -1
     *
     * @return 0 if ok
     */
    public int processLine(String line,
                           boolean allowInterrupting) {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 processLine(String, boolean). ");

        SignalHandler oldSignal = null;
        Signal interruptSignal = null;

        if (allowInterrupting) {
            // 可以允许执行中断.
            LOG.info(LOG_GY_PREFIX + " \t 1 processLine(String, boolean) allowInterrupting = " + allowInterrupting);
            // Remember all threads that were running at the time we started line processing.
            // Hook up the custom Ctrl+C handler while processing this line
            interruptSignal = new Signal("INT");
            oldSignal = Signal.handle(interruptSignal, new SignalHandler() {

                private boolean interruptRequested;

                @Override
                public void handle(Signal signal) {
                    // 在执行命令的时候，是否允许 中断执行.
                    // 这里添加一个 信号处理器, 当接收到 中断信号时, 退出 当前 Terminal.
                    boolean initialRequest = !interruptRequested;
                    interruptRequested = true;

                    // Kill the VM on second ctrl+c
                    if (!initialRequest) {
                        console.printInfo("Exiting the JVM");
                        System.exit(127);
                    }

                    // Interrupt the CLI thread to stop the current statement and return
                    // to prompt
                    console.printInfo("Interrupting... Be patient, this might take some time.");
                    console.printInfo("Press Ctrl+C again to kill JVM");

                    // First, kill any running MR jobs
                    // 杀掉正在执行的 MapReduce Job.

                    HadoopJobExecHelper.killRunningJobs();

                    // 杀掉正在执行的 TezJob.
                    TezJobExecHelper.killRunningJobs();
                    HiveInterruptUtils.interrupt();
                }
            });
        }

        try {
            int lastRet = 0, ret = 0;

            // we can not use "split" function directly as ";" may be quoted
            // 不能直接使用split函数，对命令进行分隔.
            List<String> commands = splitSemiColon(line);
            String command = "";
            for (String oneCmd : commands) {

                if (StringUtils.endsWith(oneCmd, "\\")) {
                    command += StringUtils.chop(oneCmd) + ";";
                    continue;
                } else {
                    command += oneCmd;
                }
                if (StringUtils.isBlank(command)) {
                    continue;
                }

                LOG.info(LOG_GY_PREFIX + " \t processLine(String, boolean) ---->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
                ret = processCmd(command);
                LOG.info(LOG_GY_PREFIX + " \t processLine(String, boolean) ----<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ret = " + ret);
                command = "";
                lastRet = ret;
                boolean ignoreErrors = HiveConf.getBoolVar(conf, HiveConf.ConfVars.CLIIGNOREERRORS);
                if (ret != 0 && !ignoreErrors) {
                    CommandProcessorFactory.clean((HiveConf) conf);
                    return ret;
                }
            }
            CommandProcessorFactory.clean((HiveConf) conf);
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " processLine(String, boolean) ");
            return lastRet;
        } finally {
            // Once we are done processing the line, restore the old handler
            if (oldSignal != null && interruptSignal != null) {
                Signal.handle(interruptSignal, oldSignal);
            }
        }
    }

    public static List<String> splitSemiColon(String line) {
        boolean insideSingleQuote = false;
        boolean insideDoubleQuote = false;
        boolean escape = false;
        int beginIndex = 0;
        List<String> ret = new ArrayList<>();
        for (int index = 0; index < line.length(); index++) {
            if (line.charAt(index) == '\'') {
                // take a look to see if it is escaped
                if (!escape) {
                    // flip the boolean variable
                    insideSingleQuote = !insideSingleQuote;
                }
            } else if (line.charAt(index) == '\"') {
                // take a look to see if it is escaped
                if (!escape) {
                    // flip the boolean variable
                    insideDoubleQuote = !insideDoubleQuote;
                }
            } else if (line.charAt(index) == ';') {
                if (insideSingleQuote || insideDoubleQuote) {
                    // do not split
                } else {
                    // split, do not include ; itself
                    ret.add(line.substring(beginIndex, index));
                    beginIndex = index + 1;
                }
            } else {
                // nothing to do
            }
            // set the escape
            if (escape) {
                escape = false;
            } else if (line.charAt(index) == '\\') {
                escape = true;
            }
        }
        ret.add(line.substring(beginIndex));
        return ret;
    }

    public int processReader(BufferedReader r) throws IOException {
        String line;
        StringBuilder qsb = new StringBuilder();

        while ((line = r.readLine()) != null) {
            // Skipping through comments
            if (!line.startsWith("--")) {
                qsb.append(line + "\n");
            }
        }

        return (processLine(qsb.toString()));
    }

    public int processFile(String fileName) throws IOException {
        Path path = new Path(fileName);
        FileSystem fs;
        if (!path.toUri().isAbsolute()) {
            fs = FileSystem.getLocal(conf);
            path = fs.makeQualified(path);
        } else {
            fs = FileSystem.get(path.toUri(), conf);
        }
        BufferedReader bufferReader = null;
        int rc = 0;
        try {
            bufferReader = new BufferedReader(new InputStreamReader(fs.open(path)));
            rc = processReader(bufferReader);
        } finally {
            IOUtils.closeStream(bufferReader);
        }
        return rc;
    }

    public void processInitFiles(CliSessionState ss) throws IOException {
        boolean saveSilent = ss.getIsSilent();
        ss.setIsSilent(true);
        for (String initFile : ss.initFiles) {
            int rc = processFile(initFile);
            if (rc != 0) {
                System.exit(rc);
            }
        }
        if (ss.initFiles.size() == 0) {
            if (System.getenv("HIVE_HOME") != null) {
                String hivercDefault =
                        System.getenv("HIVE_HOME") + File.separator + "bin" + File.separator + HIVERCFILE;
                if (new File(hivercDefault).exists()) {
                    int rc = processFile(hivercDefault);
                    if (rc != 0) {
                        System.exit(rc);
                    }
                    console.printError("Putting the global hiverc in " + "$HIVE_HOME/bin/.hiverc is deprecated. Please "
                                               + "use $HIVE_CONF_DIR/.hiverc instead.");
                }
            }
            if (System.getenv("HIVE_CONF_DIR") != null) {
                String hivercDefault = System.getenv("HIVE_CONF_DIR") + File.separator + HIVERCFILE;
                if (new File(hivercDefault).exists()) {
                    int rc = processFile(hivercDefault);
                    if (rc != 0) {
                        System.exit(rc);
                    }
                }
            }
            if (System.getProperty("user.home") != null) {
                String hivercUser = System.getProperty("user.home") + File.separator + HIVERCFILE;
                if (new File(hivercUser).exists()) {
                    int rc = processFile(hivercUser);
                    if (rc != 0) {
                        System.exit(rc);
                    }
                }
            }
        }
        ss.setIsSilent(saveSilent);
    }

    public void processSelectDatabase(CliSessionState ss) {
        String database = ss.database;
        if (database != null) {
            int rc = processLine("use " + database + ";");
            if (rc != 0) {
                System.exit(rc);
            }
        }
    }

    public static Completer[] getCommandCompleter() {
        // StringsCompleter matches against a pre-defined wordlist
        // We start with an empty wordlist and build it up
        List<String> candidateStrings = new ArrayList<String>();

        // We add Hive function names
        // For functions that aren't infix operators, we add an open
        // parenthesis at the end.
        for (String s : FunctionRegistry.getFunctionNames()) {
            if (s.matches("[a-z_]+")) {
                candidateStrings.add(s + "(");
            } else {
                candidateStrings.add(s);
            }
        }

        // We add Hive keywords, including lower-cased versions
        for (String s : HiveParser.getKeywords()) {
            candidateStrings.add(s);
            candidateStrings.add(s.toLowerCase());
        }

        StringsCompleter strCompleter = new StringsCompleter(candidateStrings);

        // Because we use parentheses in addition to whitespace
        // as a keyword delimiter, we need to define a new ArgumentDelimiter
        // that recognizes parenthesis as a delimiter.
        ArgumentDelimiter delim = new AbstractArgumentDelimiter() {

            @Override
            public boolean isDelimiterChar(CharSequence buffer,
                                           int pos) {
                char c = buffer.charAt(pos);
                return (Character.isWhitespace(c) || c == '(' || c == ')' || c == '[' || c == ']');
            }
        };

        // The ArgumentCompletor allows us to match multiple tokens
        // in the same line.
        final ArgumentCompleter argCompleter = new ArgumentCompleter(delim, strCompleter);
        // By default ArgumentCompletor is in "strict" mode meaning
        // a token is only auto-completed if all prior tokens
        // match. We don't want that since there are valid tokens
        // that are not in our wordlist (eg. table and column names)
        argCompleter.setStrict(false);

        // ArgumentCompletor always adds a space after a matched token.
        // This is undesirable for function names because a space after
        // the opening parenthesis is unnecessary (and uncommon) in Hive.
        // We stack a custom Completor on top of our ArgumentCompletor
        // to reverse this.
        Completer customCompletor = new Completer() {

            @Override
            public int complete(String buffer,
                                int offset,
                                List completions) {
                List<String> comp = completions;
                int ret = argCompleter.complete(buffer, offset, completions);
                // ConsoleReader will do the substitution if and only if there
                // is exactly one valid completion, so we ignore other cases.
                if (completions.size() == 1) {
                    if (comp.get(0).endsWith("( ")) {
                        comp.set(0, comp.get(0).trim());
                    }
                }
                return ret;
            }
        };

        List<String> vars = new ArrayList<String>();
        for (HiveConf.ConfVars conf : HiveConf.ConfVars.values()) {
            vars.add(conf.varname);
        }

        StringsCompleter confCompleter = new StringsCompleter(vars) {

            @Override
            public int complete(final String buffer,
                                final int cursor,
                                final List<CharSequence> clist) {
                int result = super.complete(buffer, cursor, clist);
                if (clist.isEmpty() && cursor > 1 && buffer.charAt(cursor - 1) == '=') {
                    HiveConf.ConfVars var = HiveConf.getConfVars(buffer.substring(0, cursor - 1));
                    if (var == null) {
                        return result;
                    }
                    if (var.getValidator() instanceof Validator.StringSet) {
                        Validator.StringSet validator = (Validator.StringSet) var.getValidator();
                        clist.addAll(validator.getExpected());
                    } else if (var.getValidator() != null) {
                        clist.addAll(Arrays.asList(var.getValidator().toDescription(), ""));
                    } else {
                        clist.addAll(Arrays.asList("Expects " + var.typeString() + " type value", ""));
                    }
                    return cursor;
                }
                if (clist.size() > DELIMITED_CANDIDATE_THRESHOLD) {
                    Set<CharSequence> delimited = new LinkedHashSet<CharSequence>();
                    for (CharSequence candidate : clist) {
                        Iterator<String> it = Splitter.on(".")
                                .split(candidate.subSequence(cursor, candidate.length()))
                                .iterator();
                        if (it.hasNext()) {
                            String next = it.next();
                            if (next.isEmpty()) {
                                next = ".";
                            }
                            candidate = buffer != null ? buffer.substring(0, cursor) + next : next;
                        }
                        delimited.add(candidate);
                    }
                    clist.clear();
                    clist.addAll(delimited);
                }
                return result;
            }
        };

        StringsCompleter setCompleter = new StringsCompleter("set") {

            @Override
            public int complete(String buffer,
                                int cursor,
                                List<CharSequence> clist) {
                return buffer != null && buffer.equals("set") ? super.complete(buffer, cursor, clist) : -1;
            }
        };

        ArgumentCompleter propCompleter = new ArgumentCompleter(setCompleter, confCompleter) {

            @Override
            public int complete(String buffer,
                                int offset,
                                List<CharSequence> completions) {
                int ret = super.complete(buffer, offset, completions);
                if (completions.size() == 1) {
                    completions.set(0, ((String) completions.get(0)).trim());
                }
                return ret;
            }
        };
        return new Completer[]{propCompleter, customCompletor};
    }

    public static void main(String[] args) throws Exception {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 main(String[]) 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 ");
        int ret = new CliDriver().run(args);
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 main(String[]) 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 ");
        System.exit(ret);
    }

    public int run(String[] args) throws Exception {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 run(String[]) ");
        OptionsProcessor oproc = new OptionsProcessor();
        if (!oproc.process_stage1(args)) {
            return 1;
        }

        // NOTE: It is critical to do this here so that log4j is reinitialized
        // before any of the other core hive classes are loaded
        boolean logInitFailed = false;
        String logInitDetailMessage;
        try {
            logInitDetailMessage = LogUtils.initHiveLog4j();
        } catch (LogInitializationException e) {
            logInitFailed = true;
            logInitDetailMessage = e.getMessage();
        }

        CliSessionState ss = new CliSessionState(new HiveConf(SessionState.class));
        ss.in = System.in;
        try {
            ss.out = new PrintStream(System.out, true, "UTF-8");
            ss.info = new PrintStream(System.err, true, "UTF-8");
            ss.err = new CachingPrintStream(System.err, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return 3;
        }

        if (!oproc.process_stage2(ss)) {
            return 2;
        }

        if (!ss.getIsSilent()) {
            if (logInitFailed) {
                System.err.println(logInitDetailMessage);
            } else {
                SessionState.getConsole().printInfo(logInitDetailMessage);
            }
        }

        // set all properties specified via command line
        HiveConf conf = ss.getConf();
        for (Map.Entry<Object, Object> item : ss.cmdProperties.entrySet()) {
            conf.set((String) item.getKey(), (String) item.getValue());
            ss.getOverriddenConfigurations().put((String) item.getKey(), (String) item.getValue());
        }

        // read prompt configuration and substitute variables.
        prompt = conf.getVar(HiveConf.ConfVars.CLIPROMPT);
        prompt = new VariableSubstitution(new HiveVariableSource() {

            @Override
            public Map<String, String> getHiveVariable() {
                return SessionState.get().getHiveVariables();
            }
        }).substitute(conf, prompt);
        prompt2 = spacesForString(prompt);

        if (HiveConf.getBoolVar(conf, ConfVars.HIVE_CLI_TEZ_SESSION_ASYNC)) {
            // Start the session in a fire-and-forget manner. When the asynchronously initialized parts of
            // the session are needed, the corresponding getters and other methods will wait as needed.
            SessionState.beginStart(ss, console);
        } else {
            SessionState.start(ss);
        }

        ss.updateThreadName();

        // execute cli driver work
        try {

            int res = executeDriver(ss, conf, oproc);
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 run(String[]) ");
            return res;
        } finally {
            ss.resetThreadName();
            ss.close();
        }
    }

    /**
     * Execute the cli work
     *
     * @param ss    CliSessionState of the CLI driver
     * @param conf  HiveConf for the driver session
     * @param oproc Operation processor of the CLI invocation
     *
     * @return status of the CLI command execution
     *
     * @throws Exception
     */
    private int executeDriver(CliSessionState ss,
                              HiveConf conf,
                              OptionsProcessor oproc) throws Exception {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 executeDriver(CliSessionState, HiveConf, OptionsProcessor) ");
        CliDriver cli = new CliDriver();
        cli.setHiveVariables(oproc.getHiveVariables());

        // use the specified database if specified
        cli.processSelectDatabase(ss);

        // Execute -i init files (always in silent mode)
        cli.processInitFiles(ss);

        if (ss.execString != null) {
            int cmdProcessStatus = cli.processLine(ss.execString);
            return cmdProcessStatus;
        }

        try {
            // ./bin/hive -f fileName
            if (ss.fileName != null) {
                return cli.processFile(ss.fileName);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not open input file for reading. (" + e.getMessage() + ")");
            return 3;
        }
        // 如果执行引擎是 MapReduce时, 输出信息
        if ("mr".equals(HiveConf.getVar(conf, ConfVars.HIVE_EXECUTION_ENGINE))) {
            console.printInfo(HiveConf.generateMrDeprecationWarning());
        }

        // 初始化 终端输入
        setupConsoleReader();

        String line;
        int ret = 0;
        String prefix = "";
        String curDB = getFormattedDb(conf, ss);
        String curPrompt = prompt + curDB;
        String dbSpaces = spacesForString(curDB);

        LOG.info(LOG_GY_PREFIX + " \t 1 executeDriver(CliSessionState, HiveConf, OptionsProcessor) curDB = " + curDB);
        LOG.info(LOG_GY_PREFIX + " \t 2 executeDriver(CliSessionState, HiveConf, OptionsProcessor) curPrompt = " + curPrompt);
        LOG.info(LOG_GY_PREFIX + " \t 3 executeDriver(CliSessionState, HiveConf, OptionsProcessor) dbSpaces = " + dbSpaces);

        // reader = ConsoleReader.
        while ((line = reader.readLine(curPrompt + "> ")) != null) {
            if (!prefix.equals("")) {
                // prefix不为空,则在 后面添加一个换行符.
                prefix += '\n';
            }
            if (line.trim().startsWith("--")) {
                // 这是一行注释.
                continue;
            }
            if (line.trim().endsWith(";") && !line.trim().endsWith("\\;")) {
                // 行尾是 分号(结尾).
                line = prefix + line;
                // 处理读取的每条命令.
                ret = cli.processLine(line, true);
                LOG.info(LOG_GY_PREFIX + " \t executeDriver(CliSessionState, HiveConf, OptionsProcessor) ret = " + ret + ", command = " + line);
                prefix = "";
                curDB = getFormattedDb(conf, ss);
                curPrompt = prompt + curDB;
                dbSpaces = dbSpaces.length() == curDB.length() ? dbSpaces : spacesForString(curDB);
            } else {
                // 将读入的所有SQL语句, 都累加到 prefix中.
                prefix = prefix + line;
                curPrompt = prompt2 + dbSpaces;
                continue;
            }
        }

        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 executeDriver(CliSessionState, HiveConf, OptionsProcessor) ");
        return ret;
    }

    private void setupCmdHistory() {
        final String HISTORYFILE = ".hivehistory";
        String historyDirectory = System.getProperty("user.home");
        PersistentHistory history = null;
        try {
            if ((new File(historyDirectory)).exists()) {
                String historyFile = historyDirectory + File.separator + HISTORYFILE;
                history = new FileHistory(new File(historyFile));
                reader.setHistory(history);
            } else {
                System.err.println("WARNING: Directory for Hive history file: " + historyDirectory
                                           + " does not exist.   History will not be available during this session.");
            }
        } catch (Exception e) {
            System.err.println("WARNING: Encountered an error while trying to initialize Hive's "
                                       + "history file.  History will not be available during this session.");
            System.err.println(e.getMessage());
        }

        // add shutdown hook to flush the history to history file
        ShutdownHookManager.addShutdownHook(new Runnable() {

            @Override
            public void run() {
                History h = reader.getHistory();
                if (h instanceof FileHistory) {
                    try {
                        ((FileHistory) h).flush();
                    } catch (IOException e) {
                        System.err.println("WARNING: Failed to write command history file: " + e.getMessage());
                    }
                }
            }
        });
    }

    protected void setupConsoleReader() throws IOException {
        // 终端读取器
        reader = new ConsoleReader();
        reader.setExpandEvents(false);
        reader.setBellEnabled(false);
        for (Completer completer : getCommandCompleter()) {
            reader.addCompleter(completer);
        }
        // 初始化 hive_command 的历史命令.
        setupCmdHistory();
    }

    /**
     * Retrieve the current database name string to display, based on the
     * configuration value.
     *
     * @param conf storing whether or not to show current db
     * @param ss   CliSessionState to query for db name
     *
     * @return String to show user for current db value
     */
    private static String getFormattedDb(HiveConf conf,
                                         CliSessionState ss) {
        if (!HiveConf.getBoolVar(conf, HiveConf.ConfVars.CLIPRINTCURRENTDB)) {
            return "";
        }
        //BUG: This will not work in remote mode - HIVE-5153
        String currDb = SessionState.get().getCurrentDatabase();

        if (currDb == null) {
            return "";
        }

        return " (" + currDb + ")";
    }

    /**
     * Generate a string of whitespace the same length as the parameter
     *
     * @param s String for which to generate equivalent whitespace
     *
     * @return Whitespace
     */
    private static String spacesForString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        return String.format("%1$-" + s.length() + "s", "");
    }

    public void setHiveVariables(Map<String, String> hiveVariables) {
        SessionState.get().setHiveVariables(hiveVariables);
    }

}
