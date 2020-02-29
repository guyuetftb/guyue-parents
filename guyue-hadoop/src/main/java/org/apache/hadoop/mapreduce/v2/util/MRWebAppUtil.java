package org.apache.hadoop.mapreduce.v2.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.http.HttpConfig;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.TypeConverter;
import org.apache.hadoop.mapreduce.v2.jobhistory.JHAdminConfig;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

@InterfaceAudience.Private
@InterfaceStability.Evolving
public class MRWebAppUtil {
    private static final Splitter ADDR_SPLITTER = Splitter.on(':').trimResults();
    private static final Joiner JOINER = Joiner.on("");
    private static HttpConfig.Policy httpPolicyInYarn;
    private static HttpConfig.Policy httpPolicyInJHS;

    public static void initialize(Configuration conf) {
        setHttpPolicyInYARN(conf.get("yarn.http.policy", YarnConfiguration.YARN_HTTP_POLICY_DEFAULT));

        setHttpPolicyInJHS(conf.get("mapreduce.jobhistory.http.policy", JHAdminConfig.DEFAULT_MR_HS_HTTP_POLICY));
    }

    private static void setHttpPolicyInJHS(String policy) {
        httpPolicyInJHS = HttpConfig.Policy.fromString(policy);
    }

    private static void setHttpPolicyInYARN(String policy) {
        httpPolicyInYarn = HttpConfig.Policy.fromString(policy);
    }

    public static HttpConfig.Policy getJHSHttpPolicy() {
        return httpPolicyInJHS;
    }

    public static HttpConfig.Policy getYARNHttpPolicy() {
        return httpPolicyInYarn;
    }

    public static String getYARNWebappScheme() {
        return httpPolicyInYarn == HttpConfig.Policy.HTTPS_ONLY ? "https://" : "http://";
    }

    public static String getJHSWebappScheme() {
        return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? "https://" : "http://";
    }

    public static void setJHSWebappURLWithoutScheme(Configuration conf, String hostAddress) {
        if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
            conf.set("mapreduce.jobhistory.webapp.https.address", hostAddress);
        } else {
            conf.set("mapreduce.jobhistory.webapp.address", hostAddress);
        }
    }

    public static String getJHSWebappURLWithoutScheme(Configuration conf) {
        if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
            return conf.get("mapreduce.jobhistory.webapp.https.address", "0.0.0.0:19890");
        }
        return conf.get("mapreduce.jobhistory.webapp.address", "0.0.0.0:19888");
    }

    public static String getJHSWebappURLWithScheme(Configuration conf) {
        return getJHSWebappScheme() + getJHSWebappURLWithoutScheme(conf);
    }

    public static InetSocketAddress getJHSWebBindAddress(Configuration conf) {
        if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
            return conf.getSocketAddr("mapreduce.jobhistory.bind-host", "mapreduce.jobhistory.webapp.https.address", "0.0.0.0:19890", 19890);
        }
        return conf.getSocketAddr("mapreduce.jobhistory.bind-host", "mapreduce.jobhistory.webapp.address", "0.0.0.0:19888", 19888);
    }

    public static String getApplicationWebURLOnJHSWithoutScheme(Configuration conf, ApplicationId appId)
            throws UnknownHostException {
        String addr = getJHSWebappURLWithoutScheme(conf);
        Iterator<String> it = ADDR_SPLITTER.split(addr).iterator();
        it.next();
        String port = it.next();

        addr = conf.get("mapreduce.jobhistory.address", "0.0.0.0:10020");

        String host = ADDR_SPLITTER.split(addr).iterator().next();
        String hsAddress = JOINER.join(host, ":", port);
        InetSocketAddress address = NetUtils.createSocketAddr(hsAddress, getDefaultJHSWebappPort(), getDefaultJHSWebappURLWithoutScheme());

        StringBuffer sb = new StringBuffer();
        if (address.getAddress() != null && (address.getAddress().isAnyLocalAddress() || address.getAddress().isLoopbackAddress())) {
            sb.append(InetAddress.getLocalHost().getCanonicalHostName());
        } else {
            sb.append(address.getHostName());
        }
        sb.append(":").append(address.getPort());
        sb.append("/jobhistory/job/");
        JobID jobId = TypeConverter.fromYarn(appId);
        sb.append(jobId.toString());
        return sb.toString();
    }

    public static String getApplicationWebURLOnJHSWithScheme(Configuration conf, ApplicationId appId)
            throws UnknownHostException {
        return getJHSWebappScheme() + getApplicationWebURLOnJHSWithoutScheme(conf, appId);
    }

    private static int getDefaultJHSWebappPort() {
        return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? 19890 : 19888;
    }

    private static String getDefaultJHSWebappURLWithoutScheme() {
        return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? "0.0.0.0:19890" : "0.0.0.0:19888";
    }

    public static String getAMWebappScheme(Configuration conf) {
        return "http://";
    }
}
