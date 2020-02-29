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

import static org.apache.commons.lang.StringUtils.isBlank;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hive.cli.CliDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.metadata.*;
import org.apache.hadoop.hive.ql.session.SessionState;

/**
 * CommandProcessorFactory.
 */
public final class CommandProcessorFactory {


    static Logger LOG = LoggerFactory.getLogger(CommandProcessorFactory.class);
    static final String LOG_GY_PREFIX = " MY_TEST .... ";
    static final String LOG_GY_BEGIN = " Beginninggggggggggg ";
    static final String LOG_GY_END = " Endingggggggggggggg ";

    private CommandProcessorFactory() {
        // prevent instantiation
    }

    private static final Map<HiveConf, Driver> mapDrivers = Collections.synchronizedMap(new HashMap<HiveConf, Driver>());

    public static CommandProcessor get(String cmd)
            throws SQLException {
        return get(new String[]{cmd}, null);
    }

    public static CommandProcessor getForHiveCommand(String[] cmd, HiveConf conf)
            throws SQLException {
        return getForHiveCommandInternal(cmd, conf, false);
    }

    public static CommandProcessor getForHiveCommandInternal(String[] cmd, HiveConf conf,
                                                             boolean testOnly)
            throws SQLException {

        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " getForHiveCommandInternal(String[], HiveConf,boolean) ");
        HiveCommand hiveCommand = HiveCommand.find(cmd, testOnly);
        if (hiveCommand == null || isBlank(cmd[0])) {
            LOG.info(LOG_GY_PREFIX + " \t 0-0 getForHiveCommandInternal(String[], HiveConf,boolean) hiveCommand = null ");
            return null;
        }

        LOG.info(LOG_GY_PREFIX + " \t 0-1 getForHiveCommandInternal(String[], HiveConf,boolean) hiveCommand.name = " + hiveCommand.name());
        if (conf == null) {
            conf = new HiveConf();
        }
        Set<String> availableCommands = new HashSet<String>();
        for (String availableCommand : conf.getVar(HiveConf.ConfVars.HIVE_SECURITY_COMMAND_WHITELIST)
                .split(",")) {
            availableCommands.add(availableCommand.toLowerCase().trim());
        }
        if (!availableCommands.contains(cmd[0].trim().toLowerCase())) {
            throw new SQLException("Insufficient privileges to execute " + cmd[0], "42000");
        }
        if (cmd.length > 1 && "reload".equalsIgnoreCase(cmd[0])
                && "function".equalsIgnoreCase(cmd[1])) {
            // special handling for SQL "reload function"
            return null;
        }
        switch (hiveCommand) {
            case SET:
                LOG.info(LOG_GY_PREFIX + " \t 1-SET getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new SetProcessor();
            case RESET:
                LOG.info(LOG_GY_PREFIX + " \t 2-RESET getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new ResetProcessor();
            case DFS:
                LOG.info(LOG_GY_PREFIX + " \t 3-DFS getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                SessionState ss = SessionState.get();
                return new DfsProcessor(ss.getConf());
            case ADD:
                LOG.info(LOG_GY_PREFIX + " \t 4-ADD getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new AddResourceProcessor();
            case LIST:
                LOG.info(LOG_GY_PREFIX + " \t 5-LIST getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new ListResourceProcessor();
            case DELETE:
                LOG.info(LOG_GY_PREFIX + " \t 6-DELETE getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new DeleteResourceProcessor();
            case COMPILE:
                LOG.info(LOG_GY_PREFIX + " \t 7-COMPILE getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new CompileProcessor();
            case RELOAD:
                LOG.info(LOG_GY_PREFIX + " \t 8-RELOAD getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                return new ReloadProcessor();
            case CRYPTO:
                try {
                    LOG.info(LOG_GY_PREFIX + " \t 9-CRYPTO getForHiveCommandInternal(String[], HiveConf,boolean) command = " + hiveCommand);
                    return new CryptoProcessor(SessionState.get().getHdfsEncryptionShim(), conf);
                } catch (HiveException e) {
                    throw new SQLException("Fail to start the command processor due to the exception: ", e);
                }
            default:
                throw new AssertionError("Unknown HiveCommand " + hiveCommand);
        }
    }


    public static CommandProcessor get(String[] cmd, HiveConf conf)
            throws SQLException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " get(String[], HiveConf) ");
        CommandProcessor result = getForHiveCommand(cmd, conf);
        if (result != null) {
            LOG.info(LOG_GY_PREFIX + " \t 1 get(String[], HiveConf) ");
            return result;
        }
        if (isBlank(cmd[0])) {
            LOG.info(LOG_GY_PREFIX + " \t 2 get(String[], HiveConf) ");
            return null;
        } else {
            if (conf == null) {
                LOG.info(LOG_GY_PREFIX + " \t 3 get(String[], HiveConf) ");
                return new Driver();
            }

            Driver drv = mapDrivers.get(conf);
            if (drv == null) {
                LOG.info(LOG_GY_PREFIX + " \t 4 get(String[], HiveConf) ");
                drv = new Driver();
                mapDrivers.put(conf, drv);
            } else {
                LOG.info(LOG_GY_PREFIX + " \t 5 get(String[], HiveConf) ");
                drv.resetQueryState();
            }
            drv.init();
            return drv;
        }
    }

    public static void clean(HiveConf conf) {
        Driver drv = mapDrivers.get(conf);
        if (drv != null) {
            drv.destroy();
        }

        mapDrivers.remove(conf);
    }
}
