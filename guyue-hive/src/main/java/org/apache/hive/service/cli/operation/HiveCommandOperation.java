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

package org.apache.hive.service.cli.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.CharEncoding;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.OperationLog;
import org.apache.hadoop.hive.ql.session.OperationLog.LoggingLevel;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hive.service.ServiceUtils;
import org.apache.hive.service.cli.FetchOrientation;
import org.apache.hive.service.cli.HiveSQLException;
import org.apache.hive.service.cli.OperationState;
import org.apache.hive.service.cli.RowSet;
import org.apache.hive.service.cli.RowSetFactory;
import org.apache.hive.service.cli.TableSchema;
import org.apache.hive.service.cli.session.HiveSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes a HiveCommand
 */
public class HiveCommandOperation extends ExecuteStatementOperation {
    static final        String LOG_GY_PREFIX = " MY_TEST .... ";
    static final        String LOG_GY_BEGIN  = " Beginninggggggggggg ";
    static final        String LOG_GY_END    = " Endingggggggggggggg ";
    public static final Logger LOG           = LoggerFactory.getLogger(HiveCommandOperation.class.getName());

    private final CommandProcessor commandProcessor;
    private TableSchema resultSchema        = null;
    private boolean     closeSessionStreams = true; // Only close file based streams, not System.out and System.err.

    /**
     * For processors other than Hive queries (Driver), they output to session.out (a temp file)
     * first and the fetchOne/fetchN/fetchAll functions get the output from pipeIn.
     */
    private BufferedReader resultReader;

    protected HiveCommandOperation(HiveSession parentSession,
                                   String statement,
                                   CommandProcessor commandProcessor,
                                   Map<String, String> confOverlay) {
        super(parentSession, statement, confOverlay, false);
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 HiveCommandOperation(HiveSession, statement, commandProcessor, Map<String, String>) ");
        this.commandProcessor = commandProcessor;
        setupSessionIO(parentSession.getSessionState());
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 HiveCommandOperation(HiveSession, statement, commandProcessor, Map<String, String>) ");
    }

    private void setupSessionIO(SessionState sessionState) {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 setupSessionIO(sessionState) ");
        try {
            LOG.info(LOG_GY_PREFIX + " \t Putting temp output to file " + sessionState.getTmpOutputFile().toString()
                             + " and error output to file " + sessionState.getTmpErrOutputFile().toString());
            sessionState.in = null; // hive server's session input stream is not used
            // open a per-session file in auto-flush mode for writing temp results and tmp error output
            sessionState.out = new PrintStream(new FileOutputStream(sessionState.getTmpOutputFile()),
                                               true,
                                               CharEncoding.UTF_8);
            sessionState.err = new PrintStream(new FileOutputStream(sessionState.getTmpErrOutputFile()),
                                               true,
                                               CharEncoding.UTF_8);
        } catch (IOException e) {
            LOG.error("Error in creating temp output file ", e);

            // Close file streams to avoid resource leaking
            ServiceUtils.cleanup(LOG, parentSession.getSessionState().out, parentSession.getSessionState().err);
            closeSessionStreams = false;

            try {
                sessionState.in = null;
                sessionState.out = new PrintStream(System.out, true, CharEncoding.UTF_8);
                sessionState.err = new PrintStream(System.err, true, CharEncoding.UTF_8);
            } catch (UnsupportedEncodingException ee) {
                LOG.error("Error creating PrintStream", e);
                ee.printStackTrace();
                sessionState.out = null;
                sessionState.err = null;
            }
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 setupSessionIO(sessionState) ");
    }

    private void tearDownSessionIO() {
        if (closeSessionStreams) {
            ServiceUtils.cleanup(LOG, parentSession.getSessionState().out, parentSession.getSessionState().err);
        }
    }

    @Override
    public void runInternal() throws HiveSQLException {

        /**
         * 从 父类 的 Operation.run 方法，调用本地 runInternal 方法.
         * TODO lipeng
         * hiveServer2 模式 下 hive代码的执行器.
         * 调用 commandProcessor.run 执行 具体的 hive 命令
         */
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 runInternal() ");
        setState(OperationState.RUNNING);
        try {

            LOG.info(LOG_GY_PREFIX + " \t 1 runInternal() state = " + getState());
            LOG.info(LOG_GY_PREFIX + " \t 1 runInternal() status = " + getStatus());
            String command = getStatement().trim();
            String[] tokens = statement.split("\\s");
            String commandArgs = command.substring(tokens[0].length()).trim();

            LOG.info(LOG_GY_PREFIX + " \t 2 runInternal() command = " + command);
            LOG.info(LOG_GY_PREFIX + " \t 3 runInternal() tokens = " + tokens);
            LOG.info(LOG_GY_PREFIX + " \t 4 runInternal() commands-args = " + commandArgs);

            //
            CommandProcessorResponse response = commandProcessor.run(commandArgs);
            LOG.info(LOG_GY_PREFIX + " \t 5 runInternal() response-code = " + response.getResponseCode());
            int returnCode = response.getResponseCode();
            if (returnCode != 0) {
                throw toSQLException("Error while processing statement", response);
            }
            Schema schema = response.getSchema();
            if (schema != null) {
                LOG.info(LOG_GY_PREFIX + " \t 5-1111 runInternal() true resultSchema = " + resultSchema);
                setHasResultSet(true);
                resultSchema = new TableSchema(schema);
            } else {
                setHasResultSet(false);
                resultSchema = new TableSchema();
                LOG.info(LOG_GY_PREFIX + " \t 5-2222 runInternal() true resultSchema= " + resultSchema);
            }

            if (response.getConsoleMessages() != null) {
                LOG.info(LOG_GY_PREFIX + " \t 6 runInternal() response-ConsoleMessages = " + response.getConsoleMessages());
                // Propagate processor messages (if any) to beeline or other client.
                OperationLog ol = OperationLog.getCurrentOperationLog();
                if (ol != null) {
                    LOG.info(LOG_GY_PREFIX + " \t 7 runInternal() OperationLog is not null. ");
                    for (String consoleMsg : response.getConsoleMessages()) {
                        ol.writeOperationLog(LoggingLevel.EXECUTION, consoleMsg + "\n");
                    }
                }
            }
            LOG.info(LOG_GY_PREFIX + " \t 8 runInternal() status = " + this.getTaskStatus());
        } catch (HiveSQLException e) {
            setState(OperationState.ERROR);
            throw e;
        } catch (Exception e) {
            setState(OperationState.ERROR);
            throw new HiveSQLException("Error running query: " + e.toString(), e);
        }
        setState(OperationState.FINISHED);
        LOG.info(LOG_GY_PREFIX + " \t 8 runInternal() get-state = " + getState());
        LOG.info(LOG_GY_PREFIX + " \t 8 runInternal() get-status = " + getStatus());
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 runInternal() ");
    }

    /* (non-Javadoc)
     * @see org.apache.hive.service.cli.operation.Operation#close()
     */
    @Override
    public void close() throws HiveSQLException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 close() ");
        LOG.info(LOG_GY_PREFIX + " 1 \t close() --> setState " + getStatement());
        setState(OperationState.CLOSED);
        LOG.info(LOG_GY_PREFIX + " 2 \t close() --> tearDownSessionIO ");
        tearDownSessionIO();
        LOG.info(LOG_GY_PREFIX + " 3 \t close() --> cleanTmpFile ");
        cleanTmpFile();
        LOG.info(LOG_GY_PREFIX + " 4 \t close() --> cleanupOperationLog ");
        cleanupOperationLog();
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 close() ");
    }

    /* (non-Javadoc)
     * @see org.apache.hive.service.cli.operation.Operation#getResultSetSchema()
     */
    @Override
    public TableSchema getResultSetSchema() throws HiveSQLException {
        return resultSchema;
    }

    /* (non-Javadoc)
     * @see org.apache.hive.service.cli.operation.Operation#getNextRowSet(org.apache.hive.service.cli.FetchOrientation, long)
     */
    @Override
    public RowSet getNextRowSet(FetchOrientation orientation,
                                long maxRows) throws HiveSQLException {
        /**
         * TODO lipeng
         * ThriftCLIService.FetchResults
         *  -> CLIService.fetchResults
         *      -> HiveSessionImpl.fetchResults
         *          -> OperationManager.getOperationNextRowSet
         *              -> HiveCommandOperation.getNextRowSet
         *                  ->
         */
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 getNextRowSet(FetchOrientation, maxRows) ");
        LOG.info(LOG_GY_PREFIX + " 1 \t getNextRowSet(FetchOrientation, maxRows) orientation.class= " + orientation.getClass().getSimpleName());
        validateDefaultFetchOrientation(orientation);

        if (orientation.equals(FetchOrientation.FETCH_FIRST)) {
            LOG.info(LOG_GY_PREFIX + " 2 \t getNextRowSet(FetchOrientation, maxRows) orientation = " + orientation);
            resetResultReader();
        }
        List<String> rows = readResults((int) maxRows);
        RowSet rowSet = RowSetFactory.create(resultSchema, getProtocolVersion(), false);

        for (String row : rows) {
            rowSet.addRow(new String[]{row});
        }
        LOG.info(LOG_GY_PREFIX + " 3 \t getNextRowSet(FetchOrientation, maxRows) rowSet.numRows = " + rowSet.numRows());
        LOG.info(LOG_GY_PREFIX + " 4 \t getNextRowSet(FetchOrientation, maxRows) rowSet.numColumns = " + rowSet.numColumns());
        LOG.info(LOG_GY_PREFIX + " 5 \t getNextRowSet(FetchOrientation, maxRows) rowSet.getStartOffset = " + rowSet.getStartOffset());
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 getNextRowSet(FetchOrientation, maxRows) ");
        return rowSet;
    }

    /**
     * Reads the temporary results for non-Hive (non-Driver) commands to the
     * resulting List of strings.
     *
     * @param nLines number of lines read at once. If it is <= 0, then read all lines.
     */
    private List<String> readResults(int nLines) throws HiveSQLException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " readResults(nLines) ");
        if (resultReader == null) {
            LOG.info(LOG_GY_PREFIX + " 1 \t readResults(nLines) resultReader = null ");
            SessionState sessionState = getParentSession().getSessionState();
            File tmp = sessionState.getTmpOutputFile();
            LOG.info(LOG_GY_PREFIX + " 2 \t readResults(nLines) tmp =  " + tmp.getAbsolutePath());
            try {
                resultReader = new BufferedReader(new FileReader(tmp));
            } catch (FileNotFoundException e) {
                LOG.error("File " + tmp + " not found. ", e);
                throw new HiveSQLException(e);
            }
        }
        List<String> results = new ArrayList<String>();

        for (int i = 0; i < nLines || nLines <= 0; ++i) {
            try {
                String line = resultReader.readLine();
                if (line == null) {
                    // reached the end of the result file
                    LOG.info(LOG_GY_PREFIX + "  \t\t\t readResults(nLines) line = null ");
                    break;
                } else {
                    LOG.info(LOG_GY_PREFIX + "  \t\t\t readResults(nLines) line = " + line);
                    results.add(line);
                }
            } catch (IOException e) {
                LOG.error("Reading temp results encountered an exception: ", e);
                throw new HiveSQLException(e);
            }
        }
        LOG.info(LOG_GY_PREFIX + " 3 \t readResults(nLines) results.isEmpty = " + results.isEmpty());
        LOG.info(LOG_GY_PREFIX + " 4 \t readResults(nLines) results.size = " + results.size());
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " readResults(nLines) ");
        return results;
    }

    private void cleanTmpFile() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 cleanTmpFile() ");
        resetResultReader();
        SessionState sessionState = getParentSession().getSessionState();
        LOG.info(LOG_GY_PREFIX + " 1 \t cleanTmpFile() --> deleteTmpOutputFile");
        sessionState.deleteTmpOutputFile();
        LOG.info(LOG_GY_PREFIX + " 2 \t cleanTmpFile() --> deleteTmpErrOutputFile");
        sessionState.deleteTmpErrOutputFile();
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 cleanTmpFile() ");
    }

    private void resetResultReader() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 resetResultReader() ");
        if (resultReader != null) {
            ServiceUtils.cleanup(LOG, resultReader);
            resultReader = null;
            LOG.info(LOG_GY_PREFIX + " 1 \t resetResultReader() resultReader = " + resultReader);
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 resetResultReader() ");
    }

    @Override
    public void cancel(OperationState stateAfterCancel) throws HiveSQLException {
        throw new UnsupportedOperationException("HiveCommandOperation.cancel()");
    }
}
