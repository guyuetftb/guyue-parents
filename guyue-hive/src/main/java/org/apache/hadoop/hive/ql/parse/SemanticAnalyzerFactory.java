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

package org.apache.hadoop.hive.ql.parse;

import org.antlr.runtime.tree.Tree;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.QueryState;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.HiveOperation;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * SemanticAnalyzerFactory.
 */
public final class SemanticAnalyzerFactory {

    private static final Logger LOG           = LoggerFactory.getLogger(SemanticAnalyzerFactory.class);
    static final         String LOG_GY_PREFIX = " MY_Test .... ";
    static final         String LOG_GY_BEGIN  = " Beginninggggggggggg ";
    static final         String LOG_GY_END    = " Endingggggggggggggg ";

    static HashMap<Integer, HiveOperation>   commandType               = new HashMap<Integer, HiveOperation>();
    static HashMap<Integer, HiveOperation[]> tablePartitionCommandType = new HashMap<Integer, HiveOperation[]>();

    static {
        commandType.put(HiveParser.TOK_EXPLAIN, HiveOperation.EXPLAIN);
        commandType.put(HiveParser.TOK_LOAD, HiveOperation.LOAD);
        commandType.put(HiveParser.TOK_EXPORT, HiveOperation.EXPORT);
        commandType.put(HiveParser.TOK_IMPORT, HiveOperation.IMPORT);
        commandType.put(HiveParser.TOK_CREATEDATABASE, HiveOperation.CREATEDATABASE);
        commandType.put(HiveParser.TOK_DROPDATABASE, HiveOperation.DROPDATABASE);
        commandType.put(HiveParser.TOK_SWITCHDATABASE, HiveOperation.SWITCHDATABASE);
        commandType.put(HiveParser.TOK_CREATETABLE, HiveOperation.CREATETABLE);
        commandType.put(HiveParser.TOK_TRUNCATETABLE, HiveOperation.TRUNCATETABLE);
        commandType.put(HiveParser.TOK_DROPTABLE, HiveOperation.DROPTABLE);
        commandType.put(HiveParser.TOK_DESCTABLE, HiveOperation.DESCTABLE);
        commandType.put(HiveParser.TOK_DESCFUNCTION, HiveOperation.DESCFUNCTION);
        commandType.put(HiveParser.TOK_MSCK, HiveOperation.MSCK);
        commandType.put(HiveParser.TOK_ALTERTABLE_ADDCOLS, HiveOperation.ALTERTABLE_ADDCOLS);
        commandType.put(HiveParser.TOK_ALTERTABLE_REPLACECOLS, HiveOperation.ALTERTABLE_REPLACECOLS);
        commandType.put(HiveParser.TOK_ALTERTABLE_RENAMECOL, HiveOperation.ALTERTABLE_RENAMECOL);
        commandType.put(HiveParser.TOK_ALTERTABLE_RENAME, HiveOperation.ALTERTABLE_RENAME);
        commandType.put(HiveParser.TOK_ALTERTABLE_DROPPARTS, HiveOperation.ALTERTABLE_DROPPARTS);
        commandType.put(HiveParser.TOK_ALTERTABLE_ADDPARTS, HiveOperation.ALTERTABLE_ADDPARTS);
        commandType.put(HiveParser.TOK_ALTERTABLE_TOUCH, HiveOperation.ALTERTABLE_TOUCH);
        commandType.put(HiveParser.TOK_ALTERTABLE_ARCHIVE, HiveOperation.ALTERTABLE_ARCHIVE);
        commandType.put(HiveParser.TOK_ALTERTABLE_UNARCHIVE, HiveOperation.ALTERTABLE_UNARCHIVE);
        commandType.put(HiveParser.TOK_ALTERTABLE_PROPERTIES, HiveOperation.ALTERTABLE_PROPERTIES);
        commandType.put(HiveParser.TOK_ALTERTABLE_DROPPROPERTIES, HiveOperation.ALTERTABLE_PROPERTIES);
        commandType.put(HiveParser.TOK_ALTERTABLE_EXCHANGEPARTITION,
                        HiveOperation.ALTERTABLE_EXCHANGEPARTITION);
        commandType.put(HiveParser.TOK_ALTERTABLE_DROPCONSTRAINT, HiveOperation.ALTERTABLE_DROPCONSTRAINT);
        commandType.put(HiveParser.TOK_ALTERTABLE_ADDCONSTRAINT, HiveOperation.ALTERTABLE_ADDCONSTRAINT);
        commandType.put(HiveParser.TOK_SHOWDATABASES, HiveOperation.SHOWDATABASES);
        commandType.put(HiveParser.TOK_SHOWTABLES, HiveOperation.SHOWTABLES);
        commandType.put(HiveParser.TOK_SHOWCOLUMNS, HiveOperation.SHOWCOLUMNS);
        commandType.put(HiveParser.TOK_SHOW_TABLESTATUS, HiveOperation.SHOW_TABLESTATUS);
        commandType.put(HiveParser.TOK_SHOW_TBLPROPERTIES, HiveOperation.SHOW_TBLPROPERTIES);
        commandType.put(HiveParser.TOK_SHOW_CREATEDATABASE, HiveOperation.SHOW_CREATEDATABASE);
        commandType.put(HiveParser.TOK_SHOW_CREATETABLE, HiveOperation.SHOW_CREATETABLE);
        commandType.put(HiveParser.TOK_SHOWFUNCTIONS, HiveOperation.SHOWFUNCTIONS);
        commandType.put(HiveParser.TOK_SHOWINDEXES, HiveOperation.SHOWINDEXES);
        commandType.put(HiveParser.TOK_SHOWPARTITIONS, HiveOperation.SHOWPARTITIONS);
        commandType.put(HiveParser.TOK_SHOWLOCKS, HiveOperation.SHOWLOCKS);
        commandType.put(HiveParser.TOK_SHOWDBLOCKS, HiveOperation.SHOWLOCKS);
        commandType.put(HiveParser.TOK_SHOWCONF, HiveOperation.SHOWCONF);
        commandType.put(HiveParser.TOK_SHOWVIEWS, HiveOperation.SHOWVIEWS);
        commandType.put(HiveParser.TOK_CREATEFUNCTION, HiveOperation.CREATEFUNCTION);
        commandType.put(HiveParser.TOK_DROPFUNCTION, HiveOperation.DROPFUNCTION);
        commandType.put(HiveParser.TOK_RELOADFUNCTION, HiveOperation.RELOADFUNCTION);
        commandType.put(HiveParser.TOK_CREATEMACRO, HiveOperation.CREATEMACRO);
        commandType.put(HiveParser.TOK_DROPMACRO, HiveOperation.DROPMACRO);
        commandType.put(HiveParser.TOK_CREATEVIEW, HiveOperation.CREATEVIEW);
        commandType.put(HiveParser.TOK_CREATE_MATERIALIZED_VIEW, HiveOperation.CREATE_MATERIALIZED_VIEW);
        commandType.put(HiveParser.TOK_DROPVIEW, HiveOperation.DROPVIEW);
        commandType.put(HiveParser.TOK_DROP_MATERIALIZED_VIEW, HiveOperation.DROP_MATERIALIZED_VIEW);
        commandType.put(HiveParser.TOK_CREATEINDEX, HiveOperation.CREATEINDEX);
        commandType.put(HiveParser.TOK_DROPINDEX, HiveOperation.DROPINDEX);
        commandType.put(HiveParser.TOK_ALTERINDEX_REBUILD, HiveOperation.ALTERINDEX_REBUILD);
        commandType.put(HiveParser.TOK_ALTERINDEX_PROPERTIES, HiveOperation.ALTERINDEX_PROPS);
        commandType.put(HiveParser.TOK_ALTERVIEW_PROPERTIES, HiveOperation.ALTERVIEW_PROPERTIES);
        commandType.put(HiveParser.TOK_ALTERVIEW_DROPPROPERTIES, HiveOperation.ALTERVIEW_PROPERTIES);
        commandType.put(HiveParser.TOK_ALTERVIEW_ADDPARTS, HiveOperation.ALTERTABLE_ADDPARTS);
        commandType.put(HiveParser.TOK_ALTERVIEW_DROPPARTS, HiveOperation.ALTERTABLE_DROPPARTS);
        commandType.put(HiveParser.TOK_ALTERVIEW_RENAME, HiveOperation.ALTERVIEW_RENAME);
        commandType.put(HiveParser.TOK_ALTERVIEW, HiveOperation.ALTERVIEW_AS);
        commandType.put(HiveParser.TOK_QUERY, HiveOperation.QUERY);
        commandType.put(HiveParser.TOK_LOCKTABLE, HiveOperation.LOCKTABLE);
        commandType.put(HiveParser.TOK_UNLOCKTABLE, HiveOperation.UNLOCKTABLE);
        commandType.put(HiveParser.TOK_LOCKDB, HiveOperation.LOCKDB);
        commandType.put(HiveParser.TOK_UNLOCKDB, HiveOperation.UNLOCKDB);
        commandType.put(HiveParser.TOK_CREATEROLE, HiveOperation.CREATEROLE);
        commandType.put(HiveParser.TOK_DROPROLE, HiveOperation.DROPROLE);
        commandType.put(HiveParser.TOK_GRANT, HiveOperation.GRANT_PRIVILEGE);
        commandType.put(HiveParser.TOK_REVOKE, HiveOperation.REVOKE_PRIVILEGE);
        commandType.put(HiveParser.TOK_SHOW_GRANT, HiveOperation.SHOW_GRANT);
        commandType.put(HiveParser.TOK_GRANT_ROLE, HiveOperation.GRANT_ROLE);
        commandType.put(HiveParser.TOK_REVOKE_ROLE, HiveOperation.REVOKE_ROLE);
        commandType.put(HiveParser.TOK_SHOW_ROLES, HiveOperation.SHOW_ROLES);
        commandType.put(HiveParser.TOK_SHOW_SET_ROLE, HiveOperation.SHOW_ROLES);
        commandType.put(HiveParser.TOK_SHOW_ROLE_PRINCIPALS, HiveOperation.SHOW_ROLE_PRINCIPALS);
        commandType.put(HiveParser.TOK_SHOW_ROLE_GRANT, HiveOperation.SHOW_ROLE_GRANT);
        commandType.put(HiveParser.TOK_ALTERDATABASE_PROPERTIES, HiveOperation.ALTERDATABASE);
        commandType.put(HiveParser.TOK_ALTERDATABASE_OWNER, HiveOperation.ALTERDATABASE_OWNER);
        commandType.put(HiveParser.TOK_DESCDATABASE, HiveOperation.DESCDATABASE);
        commandType.put(HiveParser.TOK_ALTERTABLE_SKEWED, HiveOperation.ALTERTABLE_SKEWED);
        commandType.put(HiveParser.TOK_ANALYZE, HiveOperation.ANALYZE_TABLE);
        commandType.put(HiveParser.TOK_CACHE_METADATA, HiveOperation.CACHE_METADATA);
        commandType.put(HiveParser.TOK_ALTERTABLE_PARTCOLTYPE, HiveOperation.ALTERTABLE_PARTCOLTYPE);
        commandType.put(HiveParser.TOK_SHOW_COMPACTIONS, HiveOperation.SHOW_COMPACTIONS);
        commandType.put(HiveParser.TOK_SHOW_TRANSACTIONS, HiveOperation.SHOW_TRANSACTIONS);
        commandType.put(HiveParser.TOK_ABORT_TRANSACTIONS, HiveOperation.ABORT_TRANSACTIONS);
        commandType.put(HiveParser.TOK_START_TRANSACTION, HiveOperation.START_TRANSACTION);
        commandType.put(HiveParser.TOK_COMMIT, HiveOperation.COMMIT);
        commandType.put(HiveParser.TOK_ROLLBACK, HiveOperation.ROLLBACK);
        commandType.put(HiveParser.TOK_SET_AUTOCOMMIT, HiveOperation.SET_AUTOCOMMIT);
        commandType.put(HiveParser.TOK_REPL_DUMP, HiveOperation.EXPORT); // piggyback on EXPORT security handling for now
        commandType.put(HiveParser.TOK_REPL_LOAD, HiveOperation.IMPORT); // piggyback on IMPORT security handling for now
        commandType.put(HiveParser.TOK_REPL_STATUS, HiveOperation.SHOW_TBLPROPERTIES); // TODO : also actually DESCDATABASE

    }

    static {
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_FILEFORMAT,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_FILEFORMAT,
                                              HiveOperation.ALTERPARTITION_FILEFORMAT});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_LOCATION,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_LOCATION,
                                              HiveOperation.ALTERPARTITION_LOCATION});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_MERGEFILES,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_MERGEFILES,
                                              HiveOperation.ALTERPARTITION_MERGEFILES});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_SERIALIZER,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_SERIALIZER,
                                              HiveOperation.ALTERPARTITION_SERIALIZER});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_SERDEPROPERTIES,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_SERDEPROPERTIES,
                                              HiveOperation.ALTERPARTITION_SERDEPROPERTIES});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_RENAMEPART,
                                      new HiveOperation[]{null, HiveOperation.ALTERTABLE_RENAMEPART});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_COMPACT,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_COMPACT, HiveOperation.ALTERTABLE_COMPACT});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_SKEWED_LOCATION,
                                      new HiveOperation[]{HiveOperation.ALTERTBLPART_SKEWED_LOCATION,
                                              HiveOperation.ALTERTBLPART_SKEWED_LOCATION});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_BUCKETS,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_BUCKETNUM,
                                              HiveOperation.ALTERPARTITION_BUCKETNUM});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_CLUSTER_SORT,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_CLUSTER_SORT,
                                              HiveOperation.ALTERTABLE_CLUSTER_SORT});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_UPDATECOLSTATS,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_UPDATETABLESTATS,
                                              HiveOperation.ALTERTABLE_UPDATEPARTSTATS});
        tablePartitionCommandType.put(HiveParser.TOK_ALTERTABLE_UPDATESTATS,
                                      new HiveOperation[]{HiveOperation.ALTERTABLE_UPDATETABLESTATS,
                                              HiveOperation.ALTERTABLE_UPDATEPARTSTATS});
    }

    public static BaseSemanticAnalyzer get(QueryState queryState,
                                           ASTNode tree)
            throws SemanticException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " SemanticAnalyzerFactory.get(QueryState,ASTNode) ");
        if (tree.getToken() == null) {
            throw new RuntimeException("Empty Syntax Tree");
        } else {
            HiveOperation opType = commandType.get(tree.getType());
            LOG.info(LOG_GY_PREFIX + " \t 1 SemanticAnalyzerFactory.get(QueryState,ASTNode) type = " + tree.getType());
            LOG.info(LOG_GY_PREFIX + " \t 2 SemanticAnalyzerFactory.get(QueryState,ASTNode) token = " + tree.getToken());
            LOG.info(LOG_GY_PREFIX + " \t 3 SemanticAnalyzerFactory.get(QueryState,ASTNode) operation-name = " + opType);

            queryState.setCommandType(opType);
            switch (tree.getType()) {
                case HiveParser.TOK_EXPLAIN:
                    return new ExplainSemanticAnalyzer(queryState);
                case HiveParser.TOK_EXPLAIN_SQ_REWRITE:
                    return new ExplainSQRewriteSemanticAnalyzer(queryState);
                case HiveParser.TOK_LOAD:
                    return new LoadSemanticAnalyzer(queryState);
                case HiveParser.TOK_EXPORT:
                    return new ExportSemanticAnalyzer(queryState);
                case HiveParser.TOK_IMPORT:
                    return new ImportSemanticAnalyzer(queryState);
                case HiveParser.TOK_REPL_DUMP:
                    return new ReplicationSemanticAnalyzer(queryState);
                case HiveParser.TOK_REPL_LOAD:
                    return new ReplicationSemanticAnalyzer(queryState);
                case HiveParser.TOK_REPL_STATUS:
                    return new ReplicationSemanticAnalyzer(queryState);
                case HiveParser.TOK_ALTERTABLE: {
                    Tree child = tree.getChild(1);
                    switch (child.getType()) {
                        case HiveParser.TOK_ALTERTABLE_RENAME:
                        case HiveParser.TOK_ALTERTABLE_TOUCH:
                        case HiveParser.TOK_ALTERTABLE_ARCHIVE:
                        case HiveParser.TOK_ALTERTABLE_UNARCHIVE:
                        case HiveParser.TOK_ALTERTABLE_ADDCOLS:
                        case HiveParser.TOK_ALTERTABLE_RENAMECOL:
                        case HiveParser.TOK_ALTERTABLE_REPLACECOLS:
                        case HiveParser.TOK_ALTERTABLE_DROPPARTS:
                        case HiveParser.TOK_ALTERTABLE_ADDPARTS:
                        case HiveParser.TOK_ALTERTABLE_PARTCOLTYPE:
                        case HiveParser.TOK_ALTERTABLE_PROPERTIES:
                        case HiveParser.TOK_ALTERTABLE_DROPPROPERTIES:
                        case HiveParser.TOK_ALTERTABLE_EXCHANGEPARTITION:
                        case HiveParser.TOK_ALTERTABLE_SKEWED:
                        case HiveParser.TOK_ALTERTABLE_DROPCONSTRAINT:
                        case HiveParser.TOK_ALTERTABLE_ADDCONSTRAINT:
                            queryState.setCommandType(commandType.get(child.getType()));
                            return new DDLSemanticAnalyzer(queryState);
                    }
                    opType =
                            tablePartitionCommandType.get(child.getType())[tree.getChildCount() > 2 ? 1 : 0];
                    queryState.setCommandType(opType);
                    return new DDLSemanticAnalyzer(queryState);
                }
                case HiveParser.TOK_ALTERVIEW: {
                    Tree child = tree.getChild(1);
                    switch (child.getType()) {
                        case HiveParser.TOK_ALTERVIEW_PROPERTIES:
                        case HiveParser.TOK_ALTERVIEW_DROPPROPERTIES:
                        case HiveParser.TOK_ALTERVIEW_ADDPARTS:
                        case HiveParser.TOK_ALTERVIEW_DROPPARTS:
                        case HiveParser.TOK_ALTERVIEW_RENAME:
                            opType = commandType.get(child.getType());
                            queryState.setCommandType(opType);
                            return new DDLSemanticAnalyzer(queryState);
                    }
                    // TOK_ALTERVIEW_AS
                    assert child.getType() == HiveParser.TOK_QUERY;
                    queryState.setCommandType(HiveOperation.ALTERVIEW_AS);
                    return new SemanticAnalyzer(queryState);
                }
                case HiveParser.TOK_CREATEDATABASE:
                case HiveParser.TOK_DROPDATABASE:
                case HiveParser.TOK_SWITCHDATABASE:
                case HiveParser.TOK_DROPTABLE:
                case HiveParser.TOK_DROPVIEW:
                case HiveParser.TOK_DROP_MATERIALIZED_VIEW:
                case HiveParser.TOK_DESCDATABASE:
                case HiveParser.TOK_DESCTABLE:
                case HiveParser.TOK_DESCFUNCTION:
                case HiveParser.TOK_MSCK:
                case HiveParser.TOK_ALTERINDEX_REBUILD:
                case HiveParser.TOK_ALTERINDEX_PROPERTIES:
                case HiveParser.TOK_SHOWDATABASES:
                case HiveParser.TOK_SHOWTABLES:
                case HiveParser.TOK_SHOWCOLUMNS:
                case HiveParser.TOK_SHOW_TABLESTATUS:
                case HiveParser.TOK_SHOW_TBLPROPERTIES:
                case HiveParser.TOK_SHOW_CREATEDATABASE:
                case HiveParser.TOK_SHOW_CREATETABLE:
                case HiveParser.TOK_SHOWFUNCTIONS:
                case HiveParser.TOK_SHOWPARTITIONS:
                case HiveParser.TOK_SHOWINDEXES:
                case HiveParser.TOK_SHOWLOCKS:
                case HiveParser.TOK_SHOWDBLOCKS:
                case HiveParser.TOK_SHOW_COMPACTIONS:
                case HiveParser.TOK_SHOW_TRANSACTIONS:
                case HiveParser.TOK_ABORT_TRANSACTIONS:
                case HiveParser.TOK_SHOWCONF:
                case HiveParser.TOK_SHOWVIEWS:
                case HiveParser.TOK_CREATEINDEX:
                case HiveParser.TOK_DROPINDEX:
                case HiveParser.TOK_ALTERTABLE_CLUSTER_SORT:
                case HiveParser.TOK_LOCKTABLE:
                case HiveParser.TOK_UNLOCKTABLE:
                case HiveParser.TOK_LOCKDB:
                case HiveParser.TOK_UNLOCKDB:
                case HiveParser.TOK_CREATEROLE:
                case HiveParser.TOK_DROPROLE:
                case HiveParser.TOK_GRANT:
                case HiveParser.TOK_REVOKE:
                case HiveParser.TOK_SHOW_GRANT:
                case HiveParser.TOK_GRANT_ROLE:
                case HiveParser.TOK_REVOKE_ROLE:
                case HiveParser.TOK_SHOW_ROLE_GRANT:
                case HiveParser.TOK_SHOW_ROLE_PRINCIPALS:
                case HiveParser.TOK_SHOW_ROLES:
                case HiveParser.TOK_ALTERDATABASE_PROPERTIES:
                case HiveParser.TOK_ALTERDATABASE_OWNER:
                case HiveParser.TOK_TRUNCATETABLE:
                case HiveParser.TOK_SHOW_SET_ROLE:
                case HiveParser.TOK_CACHE_METADATA:
                    return new DDLSemanticAnalyzer(queryState);

                case HiveParser.TOK_CREATEFUNCTION:
                case HiveParser.TOK_DROPFUNCTION:
                case HiveParser.TOK_RELOADFUNCTION:
                    return new FunctionSemanticAnalyzer(queryState);

                case HiveParser.TOK_ANALYZE:
                    return new ColumnStatsSemanticAnalyzer(queryState);

                case HiveParser.TOK_CREATEMACRO:
                case HiveParser.TOK_DROPMACRO:
                    return new MacroSemanticAnalyzer(queryState);

                case HiveParser.TOK_UPDATE_TABLE:
                case HiveParser.TOK_DELETE_FROM:
                case HiveParser.TOK_MERGE:
                    return new UpdateDeleteSemanticAnalyzer(queryState);

                case HiveParser.TOK_START_TRANSACTION:
                case HiveParser.TOK_COMMIT:
                case HiveParser.TOK_ROLLBACK:
                case HiveParser.TOK_SET_AUTOCOMMIT:
                default: {
                    LOG.info(LOG_GY_PREFIX + " \t 4 SemanticAnalyzerFactory.get(QueryState,ASTNode) commandType = " + queryState.getCommandType());
                    LOG.info(LOG_GY_PREFIX + " \t 5 SemanticAnalyzerFactory.get(QueryState,ASTNode) hiveOperation = " + queryState.getHiveOperation());
                    LOG.info(LOG_GY_PREFIX + " \t 6 SemanticAnalyzerFactory.get(QueryState,ASTNode) queryId = " + queryState.getQueryId());
                    LOG.info(LOG_GY_PREFIX + " \t 7 SemanticAnalyzerFactory.get(QueryState,ASTNode) (CalcitePlanner or SemanticAnalyzer) = " + HiveConf
                            .getBoolVar(queryState.getConf(), HiveConf.ConfVars.HIVE_CBO_ENABLED));

                    SemanticAnalyzer semAnalyzer = HiveConf
                            .getBoolVar(queryState.getConf(), HiveConf.ConfVars.HIVE_CBO_ENABLED) ?
                            new CalcitePlanner(queryState) : new SemanticAnalyzer(queryState); // 语义查询

                    LOG.info(LOG_GY_PREFIX + " \t 8 SemanticAnalyzerFactory.get(QueryState,ASTNode) semAnalyzer = " + semAnalyzer);
                    LOG.info(LOG_GY_PREFIX + LOG_GY_END + " SemanticAnalyzerFactory.get(QueryState,ASTNode) ");
                    return semAnalyzer;
                }
            }
        }
    }

    private SemanticAnalyzerFactory() {
        // prevent instantiation
    }

    static HiveOperation getOperation(int hiveParserToken) {
        return commandType.get(hiveParserToken);
    }
}
