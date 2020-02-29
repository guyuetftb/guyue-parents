package com.gy.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ConfigurationUtils {

    private final static Logger logger = Logger.getLogger(ConfigurationUtils.class);

    // bit
    private final static int HTABLE_BUF_SIZE = 32;

    public static Configuration createConfiguratoin(String host, String port) {
        Configuration conf = createConfiguratoin();
        // hbase.regionserver.lease.period
        conf.set(HConstants.HBASE_CONFIG_READ_ZOOKEEPER_CONFIG, String.valueOf(1000 * 2));
        conf.set(HConstants.HBASE_RPC_TIMEOUT_KEY, "120000");
        setThirdJars(conf);
        return conf;
    }

    public static Configuration createConfiguratoin(Configuration conf) {
        return HBaseConfiguration.create(conf);
    }

    public static Configuration createConfiguratoin() {
        return new Configuration();
    }

    public static Job createJob(Configuration conf) {
        return createJob(conf, null);
    }

    public static Job createJob(Configuration conf, String jobName) {
        try {
            return Job.getInstance(conf, jobName);
        } catch (IOException e) {
            logger.error("The createJob(" + conf + "," + jobName + ") method of ConfigurationUtils class is executing failure.", e);
        }
        return null;
    }

    public static Job createJob() {
        return createJob(null, null);
    }

    public static HTable createHTable(Configuration conf, String tableName) throws IOException {
        HTable htable = new HTable(conf, tableName);
        htable.setAutoFlush(Boolean.FALSE);
        htable.setWriteBufferSize(HTABLE_BUF_SIZE * 1024 * 1024);
        return htable;
    }

    @SuppressWarnings("deprecation")
    public static void setThirdJars(Configuration conf) {
        if (ThirdJarUtils.getThirdJars().size() <= 0) {
            logger.info("The HashMap<jar-name,jar-absolute-path> of third jar is null.");
            return;
        }
        try {
            FileSystem fs = FileSystem.get(conf);
            Path jarPath = null;
            for (String s : ThirdJarUtils.getThirdJars().values()) {
                jarPath = new Path(s);
                DistributedCache.addFileToClassPath(jarPath, conf, fs);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
