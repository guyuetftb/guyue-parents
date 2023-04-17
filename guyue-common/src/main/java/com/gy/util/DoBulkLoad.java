package com.gy.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.util.Bytes;

public class DoBulkLoad {

    public synchronized Boolean loadIncrementHFile(Configuration conf, Path path, String TableName, Boolean isCompression) {
        byte[] TABLE = Bytes.toBytes(TableName);
        Boolean result = false;
        try {
            HTable table = new HTable(conf, TABLE);
            LoadIncrementalHFiles loader;
            String compression = conf.get("hfile.compression", Compression.Algorithm.NONE.getName());

            System.out.println("compression:" + compression);
            if (isCompression) {
                conf.set("hfile.compression", Compression.Algorithm.LZO.getName());
            }
            loader = new LoadIncrementalHFiles(conf);
            loader.doBulkLoad(path, table);
            result = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {

        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        byte[] TABLE = Bytes.toBytes("status_info");

        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "hadoop-2,hadoop-3,hadoop-4");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        // conf.addResource("core-site.xml");
        // conf.set("fs.defaultFS","hdfs://hadoop-1:9000/");

        HTable table = new HTable(conf, TABLE);
        LoadIncrementalHFiles loader;
        try {
            System.out.println("path:" + args[1]);
            // conf.set
            loader = new LoadIncrementalHFiles(conf);
            loader.doBulkLoad(new Path(args[1]), table);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}