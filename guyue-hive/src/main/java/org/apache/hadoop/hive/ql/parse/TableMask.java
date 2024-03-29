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

import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.TokenRewriteStream;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.metadata.HiveUtils;
import org.apache.hadoop.hive.ql.metadata.VirtualColumn;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthorizer;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HivePrivilegeObject;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzContext;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main purpose for this class is for authorization. More specifically, row
 * filtering and column masking are done through this class. We first call
 * create function to create the corresponding strings for row filtering and
 * column masking. We then replace the TAB_REF with the strings.
 */
public class TableMask {

    protected final Logger LOG = LoggerFactory.getLogger(TableMask.class);

    static final String LOG_GY_PREFIX = " MY_TEST .... ";
    static final String LOG_GY_BEGIN = " Beginninggggggggggg ";
    static final String LOG_GY_END = " Endingggggggggggggg ";

    HiveAuthorizer authorizer;
    private UnparseTranslator translator;
    private boolean enable;
    private boolean needsRewrite;
    private HiveAuthzContext queryContext;
    private HiveConf conf;

    public TableMask(SemanticAnalyzer analyzer, HiveConf conf, boolean skipTableMasking)
            throws SemanticException {
        try {
            LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " TableMask(SemanticAnalyzer,HiveConf,boolean) ");
            authorizer = SessionState.get().getAuthorizerV2();
            this.conf = conf;
            String cmdString = analyzer.ctx.getCmd();
            SessionState ss = SessionState.get();
            HiveAuthzContext.Builder ctxBuilder = new HiveAuthzContext.Builder();
            ctxBuilder.setCommandString(cmdString);

            ctxBuilder.setUserIpAddress(ss.getUserIpAddress());
            ctxBuilder.setForwardedAddresses(ss.getForwardedAddresses());
            LOG.info(LOG_GY_PREFIX + " \t TableMask(SemanticAnalyzer,HiveConf,boolean) UserIpAddress = " + ctxBuilder.getUserIpAddress());
            LOG.info(LOG_GY_PREFIX + " \t TableMask(SemanticAnalyzer,HiveConf,boolean) ForwardedAddresses = " + ctxBuilder.getForwardedAddresses());
            queryContext = ctxBuilder.build();
            if (authorizer != null && needTransform() && !skipTableMasking) {
                enable = true;
                translator = new UnparseTranslator(conf);
                translator.enable();
            }
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " TableMask(SemanticAnalyzer,HiveConf,boolean) ");
        } catch (Exception e) {
            LOG.warn("Failed to initialize masking policy");
            throw new SemanticException(e);
        }
    }

    public List<HivePrivilegeObject> applyRowFilterAndColumnMasking(List<HivePrivilegeObject> privObjs)
            throws SemanticException {
        return authorizer.applyRowFilterAndColumnMasking(queryContext, privObjs);
    }

    public boolean isEnabled() {
        return enable;
    }

    public boolean needTransform() {
        return authorizer.needTransform();
    }

    public String create(HivePrivilegeObject privObject, MaskAndFilterInfo maskAndFilterInfo)
            throws SemanticException {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " create(HivePrivilegeObject,MaskAndFilterInfo) ");
        boolean doColumnMasking = false;
        boolean doRowFiltering = false;
        StringBuilder sb = new StringBuilder();
        sb.append("(SELECT ");
        boolean firstOne = true;
        List<String> exprs = privObject.getCellValueTransformers();
        if (exprs != null) {
            if (exprs.size() != privObject.getColumns().size()) {
                throw new SemanticException("Expect " + privObject.getColumns().size() + " columns in "
                        + privObject.getObjectName() + ", but only find " + exprs.size());
            }
            List<String> colTypes = maskAndFilterInfo.colTypes;
            for (int index = 0; index < exprs.size(); index++) {
                String expr = exprs.get(index);
                if (expr == null) {
                    throw new SemanticException("Expect string type CellValueTransformer in "
                            + privObject.getObjectName() + ", but only find null");
                }
                if (!firstOne) {
                    sb.append(", ");
                } else {
                    firstOne = false;
                }
                String colName = privObject.getColumns().get(index);
                if (!expr.equals(colName)) {
                    // CAST(expr AS COLTYPE) AS COLNAME
                    sb.append("CAST(" + expr + " AS " + colTypes.get(index) + ") AS "
                            + HiveUtils.unparseIdentifier(colName, conf));
                    doColumnMasking = true;
                } else {
                    sb.append(HiveUtils.unparseIdentifier(colName, conf));
                }
            }
        }
        if (!doColumnMasking) {
            sb = new StringBuilder();
            sb.append("(SELECT *");
        }

        if (!maskAndFilterInfo.isView) {
            // put all virtual columns in RowResolver.
            Iterator<VirtualColumn> vcs = VirtualColumn.getRegistry(conf).iterator();
            while (vcs.hasNext()) {
                VirtualColumn vc = vcs.next();
                sb.append(", " + vc.getName());
            }
        }

        sb.append(" FROM ");
        sb.append(HiveUtils.unparseIdentifier(privObject.getDbname(), conf));
        sb.append(".");
        sb.append(HiveUtils.unparseIdentifier(privObject.getObjectName(), conf));
        sb.append(" " + maskAndFilterInfo.additionalTabInfo);
        String filter = privObject.getRowFilterExpression();
        if (filter != null) {
            sb.append(" WHERE " + filter);
            doRowFiltering = true;
        }
        sb.append(")" + HiveUtils.unparseIdentifier(maskAndFilterInfo.alias, conf));

        if (!doColumnMasking && !doRowFiltering) {
            // nothing to do
            return null;
        } else {
            LOG.debug("TableMask creates `" + sb.toString() + "`");
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " create(HivePrivilegeObject,MaskAndFilterInfo) ");
            return sb.toString();
        }
    }

    void addTranslation(ASTNode node, String replacementText) {
        translator.addTranslation(node, replacementText);
    }

    void applyTranslations(TokenRewriteStream tokenRewriteStream) {
        translator.applyTranslations(tokenRewriteStream);
    }

    public boolean needsRewrite() {
        return needsRewrite;
    }

    public void setNeedsRewrite(boolean needsRewrite) {
        this.needsRewrite = needsRewrite;
    }

}
