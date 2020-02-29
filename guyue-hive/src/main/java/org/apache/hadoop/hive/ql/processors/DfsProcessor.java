/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.processors;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsShell;
import org.apache.hadoop.hive.conf.HiveVariableSource;
import org.apache.hadoop.hive.conf.VariableSubstitution;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveOperationType;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;

/**
 * DfsProcessor.
 */
public class DfsProcessor implements CommandProcessor {

    public static final Logger LOG           = LoggerFactory.getLogger(DfsProcessor.class.getName());
    static final        String LOG_GY_PREFIX = " MY_TEST .... ";
    static final        String LOG_GY_BEGIN  = " Beginninggggggggggg ";
    static final        String LOG_GY_END    = " Endingggggggggggggg ";

    public static final LogHelper console           = new LogHelper(LOG);
    public static final String    DFS_RESULT_HEADER = "DFS Output";

    private final FsShell dfs;
    private final Schema  dfsSchema;

    public DfsProcessor(Configuration conf) {
        this(conf, false);
    }

    public DfsProcessor(Configuration conf,
                        boolean addSchema) {
        dfs = new FsShell(conf);
        dfsSchema = new Schema();
        dfsSchema.addToFieldSchemas(new FieldSchema(DFS_RESULT_HEADER, "string", ""));
    }

    @Override
    public void init() {
    }

    @Override
    public CommandProcessorResponse run(String command) {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 run(command) ");
        try {
            SessionState ss = SessionState.get();
            command = new VariableSubstitution(new HiveVariableSource() {

                @Override
                public Map<String, String> getHiveVariable() {
                    return SessionState.get().getHiveVariables();
                }
            }).substitute(ss.getConf(), command);

            String[] tokens = command.split("\\s+");
            LOG.info(LOG_GY_PREFIX + " \t 0 tokens = " + tokens);
            LOG.info(LOG_GY_PREFIX + " \t 1 tokens.length = " + tokens.length);
            CommandProcessorResponse authErrResp =
                    CommandUtil.authorizeCommand(ss, HiveOperationType.DFS, Arrays.asList(tokens));

            LOG.info(LOG_GY_PREFIX + " \t 2 authErrResp = " + authErrResp);
            if (authErrResp != null) {
                // there was an authorization issue
                return authErrResp;
            }


            PrintStream oldOut = System.out;

            if (ss != null && ss.out != null) {
                System.setOut(ss.out);
            }

            for (int index = 0; index < tokens.length; index++) {
                LOG.info(LOG_GY_PREFIX + " \t 3-->" + index + ", " + tokens[index]);
            }

            // TODO lipeng 2018-07-27
            String commandTrim = tokens[0].trim();
            int ret = -1;
            if ("-rm".equals(commandTrim) || "-rmr".equals(commandTrim) || "-mv".equals(commandTrim) || "-rmdir".equals(commandTrim)) {
                ret = 403;
                console.printError("Command " + command + " failed with exit code = " + ret);
                console.printError("Guyue Authorization failed: Pemission denied. Please contact blg-team-dataman@guyuecn for your information.");
            } else {
                ret = dfs.run(tokens);
                if (ret != 0) {
                    console.printError("Command " + command + " failed with exit code = " + ret);
                }
            }


            System.setOut(oldOut);
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 run(command) ");
            return new CommandProcessorResponse(ret, null, null, dfsSchema);
        } catch (Exception e) {
            console.printError("Exception raised from DFSShell.run "
                                       + e.getLocalizedMessage(), org.apache.hadoop.util.StringUtils
                                       .stringifyException(e));
            return new CommandProcessorResponse(1);
        }
    }

}
