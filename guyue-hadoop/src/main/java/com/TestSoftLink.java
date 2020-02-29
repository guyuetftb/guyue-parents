package com;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;

import java.io.IOException;
import java.net.URI;

/**
 * Created by lipeng
 * com
 * lipeng
 * 2018/9/27
 */
public class TestSoftLink {

    public static void main(String[] args) throws IOException {
        if (null == args || args.length < 3) {
            System.out.println(" Args Error. ");
            System.exit(1);
        }
        String targetPath = args[0];
        String linkPath = args[1];
        boolean createParent = Boolean.valueOf(args[2]);
        createSymlink(targetPath, linkPath, createParent);
    }

    public static void createSymlink(String targetPath,
                                     String linkPath,
                                     boolean createParent) {
        System.out.println(" ------------ Start ------------");
        System.out.println(" targetPath = " + targetPath);
        System.out.println(" linkPath = " + linkPath);
        System.out.println(" createParent = " + createParent);
        DistributedFileSystem fs = null;
        FileSystem.enableSymlinks();
        DistributedFileSystem.enableSymlinks();

        String HdfsServer = "hdfs://ns1/";
        Configuration conf = new Configuration();
        conf.set("mapred.create.symlink", "yes");
        conf.set("fs.defaultFS", HdfsServer);
        URI uri = URI.create(HdfsServer);
        try {
            FileContext fc = FileContext.getFileContext(uri, conf);
            fc.createSymlink(new Path(targetPath), new Path(linkPath), createParent);
            // conf.set("test.SymlinkEnabledForTesting","true");
            fs = (DistributedFileSystem) FileSystem.get(conf);
        } catch (IOException e) {
            System.out.println(" Created FileSystem Error : ");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            fs.createSymlink(new Path(targetPath), new Path(linkPath), createParent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" ------------ End ------------");
    }
}
