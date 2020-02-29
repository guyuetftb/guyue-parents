package org.apache.hadoop.hive.ql.gy.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.metadata.AuthorizationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 用户认证类，用于从数据库中提取相关的信息。* @author songwei*
 */
public class UserAuthDataMode {
    private static final Log LOG = LogFactory.getLog(UserAuthDataMode.class.getName());

    private HiveConf conf;
    private boolean isSuperUser = false;

    private UserAuth                         userAuth        = null;
    private HashSet<String>                  userDBSet       = new HashSet<String>();
    private HashSet<String>                  userTableSet    = new HashSet<String>();
    private HashSet<String>                  userExcolumnSet = new HashSet<String>();
    private HashMap<String, HashSet<String>> userIncolumnMap = new HashMap<String, HashSet<String>>();

    private String     userName;
    private String     password;
    private Connection conn;
    private int        userid;

    private static final String SQL_AUTH =
            "select id,username,is_superuser, "
                    + " (select jsoncontext from t_hive_user_auth where user_id = user.id) user_json_context, "
                    + " (select jsoncontext from auth_user_groups g join t_hive_user_group_auth au on (g.group_id = au.group_id) where g.user_id = user.id) group_json_context "
                    + " from auth_user user"
                    + " where username = '%s'";

    private void createConn() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbURL = HiveConf.getVar(this.conf, HiveConf.ConfVars.MRYXBLG_HIVESERVER_URL);
        String dbUserName = HiveConf.getVar(this.conf, HiveConf.ConfVars.MRYXBLG_HIVESERVER_USER);
        String dbPassword = HiveConf.getVar(this.conf, HiveConf.ConfVars.MRYXBLG_HIVESERVER_PASSWORD);
        LOG.info(" db_url = " + HiveConf.ConfVars.MRYXBLG_HIVESERVER_URL + " = " + dbURL);
        LOG.info(" db_userName = " + HiveConf.ConfVars.MRYXBLG_HIVESERVER_USER + " = " + dbUserName);
        LOG.info(" db_Password = " + HiveConf.ConfVars.MRYXBLG_HIVESERVER_PASSWORD + " = " + dbPassword);
        this.conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
    }

    public UserAuthDataMode(String userName,
                            String password,
                            HiveConf conf) throws Exception {
        this.userName = userName;
        this.password = password;
        this.conf = conf;
        this.createConn();
    }

    private ResultSet query(String sql) throws Exception {
        Statement stmt = conn.createStatement();
        LOG.warn(" SQL_AUTH = " + sql);
        return stmt.executeQuery(sql);
    }

    private void checkUser() throws Exception {
    }

    private void parseAuth() throws Exception {
        // query
        ResultSet mrRs = this.query(String.format(SQL_AUTH, this.userName));

        UserAuth user = null;
        if (mrRs.isClosed()) {
            // throw exception
            throw new AuthorizationException(" Database connection has already closed.");
        } else {
            while (mrRs.next()) {
                user = new UserAuth();
                user.setId(mrRs.getInt("id"));
                user.setUserName(mrRs.getString("username"));
                user.setSuperUser(mrRs.getBoolean("is_superuser"));
                user.setUserJsonContext(mrRs.getString("user_json_context"));
                user.setGroupJsonContext(mrRs.getString("group_json_context"));
                user.setId(mrRs.getInt("id"));
                userAuth = user;
                break;
            }
        }

        if (user == null) {
            throw new AuthorizationException(" No such user [" + this.userName + "]");
        }

        // is super user
        this.isSuperUser = user.isSuperUser();

        LOG.warn(" user_json_context = [" + user.getUserJsonContext() + "]");
        LOG.warn(" group_json_context = [" + user.getGroupJsonContext() + "]");
        /** user auth. */
        if (StringUtils.isNotEmpty(user.getUserJsonContext())
                && !"null".equalsIgnoreCase(user.getUserJsonContext())) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Auth authUser = gson.fromJson(user.getUserJsonContext(), Auth.class);
            authUser.setUserId(user.getId());

            LOG.warn(" user_auth.db = [" + authUser.getDb() + "]");
            LOG.warn(" user_auth.table = [" + authUser.getTable() + "]");
            LOG.warn(" user_auth.excolumn = [" + authUser.getExcolumn() + "]");

            // db
            if (StringUtils.isNotEmpty(authUser.getDb())) {
                for (String s : authUser.getDb().split(",")) {
                    userDBSet.add(s.trim());
                }
            }

            // table
            if (StringUtils.isNotEmpty(authUser.getTable())) {
                for (String s : authUser.getTable().split(",")) {
                    userTableSet.add(s.trim());
                }
            }

            // columns
            if (StringUtils.isNotEmpty(authUser.getExcolumn())) {
                for (String s : authUser.getExcolumn().split(",")) {
                    userExcolumnSet.add(s.trim());
                }
            }
            userAuth.setUserAuth(authUser);
        }

        /** group auth */
        if (StringUtils.isNotEmpty(user.getGroupJsonContext())
                && !"null".equalsIgnoreCase(user.getGroupJsonContext())) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Auth authGroup = gson.fromJson(user.getGroupJsonContext(), Auth.class);
            authGroup.setUserId(user.getId());

            LOG.warn(" group_auth.db = [" + authGroup.getDb() + "]");
            LOG.warn(" group_auth.table = [" + authGroup.getTable() + "]");
            LOG.warn(" group_auth.excolumn = [" + authGroup.getExcolumn() + "]");

            // db
            if (StringUtils.isNotEmpty(authGroup.getDb())) {
                for (String s : authGroup.getDb().split(",")) {
                    userDBSet.add(s.trim());
                }
            }

            // table
            if (StringUtils.isNotEmpty(authGroup.getTable())) {
                for (String s : authGroup.getTable().split(",")) {
                    userTableSet.add(s.trim());
                }
            }

            // excolumn
            if (StringUtils.isNotEmpty(authGroup.getExcolumn())) {
                for (String s : authGroup.getExcolumn().split(",")) {
                    userExcolumnSet.add(s.trim());
                }
            }
            userAuth.setGroupAuth(authGroup);
        }
        showData();
    }

    public void showData() {
        LOG.warn(" userAuth = " + userAuth);
        LOG.warn(" userDBSet = " + this.userDBSet);
        LOG.warn(" userTableSet = " + this.userTableSet);
        LOG.warn(" userExcolumnSet = " + this.getUserExcolumnSet());
    }

    public void run() throws Exception {
        this.checkUser();
        this.parseAuth();
        this.checkData();
        this.modifyConf();
        this.clearData();
    }

    public void clearData() throws Exception {
        this.conn.close();
    }

    private void modifyConf() {
        //    this.conf.setInt("mapred.map.tasks", this.maxMapCount);
        //    this.conf.setInt("hive.exec.reducers.ma", this.maxRedCount);
        //    HiveConf.setIntVar(this.conf, HiveConf.ConfVars.MAXREDUCERS, this.maxRedCount);
    }

    private void checkData() {
        LOG.debug(this.userDBSet.size());
        LOG.debug(this.userTableSet.size());
        LOG.debug(this.userExcolumnSet.size());
        LOG.debug(this.userIncolumnMap.size());
    }

    public static void main(String[] args) throws Exception {
        UserAuthDataMode ua = new UserAuthDataMode("swtest", "swtest", null);
        ua.run();
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public HashSet<String> getUserDBSet() {
        return userDBSet;
    }

    public HashSet<String> getUserTableSet() {
        return userTableSet;
    }

    public HashMap<String, HashSet<String>> getUserIncolumnMap() {
        return userIncolumnMap;
    }

    public HashSet<String> getUserExcolumnSet() {
        return userExcolumnSet;
    }
}
