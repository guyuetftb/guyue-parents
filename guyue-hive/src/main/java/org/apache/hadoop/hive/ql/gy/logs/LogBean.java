package org.apache.hadoop.hive.ql.gy.logs;

import com.google.gson.annotations.SerializedName;

public class LogBean {

    @SerializedName("application_id")
    private String applicationId;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("user_ip_address")
    private String userIpAddress;

    @SerializedName("logger_type")
    private String loggerType;

    @SerializedName("logger_location")
    private String loggerLocation;

    @SerializedName("command")
    private String command;

    @SerializedName("command_clean")
    private String commandClean;

    @SerializedName("query_string")
    private String queryString;

    @SerializedName("current_time")
    private String currentTime;

    @SerializedName("blg_user_name")
    private String blgUserName;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("ret")
    private String ret;

    @SerializedName("query_id")
    private String queryId;

    @SerializedName("mode_type")
    private String modeType;

    @SerializedName("processor_name")
    private String processorName;

    @SerializedName("last_command")
    private String lastCommand;

    @SerializedName("guyue_exec_model")
    private String guyueExecModel;

    @SerializedName("guyue_authorization_enabled")
    private String guyueAuthorizationEnabled;

    @SerializedName("guyue_command_monitoring")
    private String guyueCommandMonitoring;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public String getLoggerLocation() {
        return loggerLocation;
    }

    public void setLoggerLocation(String loggerLocation) {
        this.loggerLocation = loggerLocation;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandClean() {
        return commandClean;
    }

    public void setCommandClean(String commandClean) {
        this.commandClean = commandClean;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getBlgUserName() {
        return blgUserName;
    }

    public void setBlgUserName(String blgUserName) {
        this.blgUserName = blgUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public String getGuyueExecModel() {
        return guyueExecModel;
    }

    public void setGuyueExecModel(String guyueExecModel) {
        this.guyueExecModel = guyueExecModel;
    }

    public String getGuyueAuthorizationEnabled() {
        return guyueAuthorizationEnabled;
    }

    public void setGuyueAuthorizationEnabled(String guyueAuthorizationEnabled) {
        this.guyueAuthorizationEnabled = guyueAuthorizationEnabled;
    }

    public String getGuyueCommandMonitoring() {
        return guyueCommandMonitoring;
    }

    public void setGuyueCommandMonitoring(String guyueCommandMonitoring) {
        this.guyueCommandMonitoring = guyueCommandMonitoring;
    }

    public String getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(String loggerType) {
        this.loggerType = loggerType;
    }
}
