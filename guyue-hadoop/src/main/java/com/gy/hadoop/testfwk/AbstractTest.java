package com.gy.hadoop.testfwk;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.gy.util.HdfsUtils;

public abstract class AbstractTest {

	private final static Logger logger = Logger.getLogger(AbstractTest.class);
	private final static String DEFAULT_EXAMPLE = "安理会一致通过D要求叙利亚销毁化EN学武器决议;";

	protected static void createTestData(Configuration conf, String outputPath, int recordSize, String example) {
		HdfsUtils.deleteFile(conf, outputPath);
		writeCommonData(conf, outputPath, recordSize, (StringUtils.isEmpty(example) ? DEFAULT_EXAMPLE : example));
	}

	private static void writeCommonData(Configuration conf, String filePath, int recordSize, String example) {
		FileSystem hdfs = null;
		FSDataOutputStream outputStream = null;
		try {
			hdfs = FileSystem.get(conf);
			Path path = new Path(filePath);
			outputStream = hdfs.create(path);
			// key(fans,favs)
			for (int index = 0; index < recordSize; index++) {
				try {
					outputStream.write(Bytes.toBytes(index + "-" + example + "END\n"));
				} catch (IOException e) {
					logger.error(" createFile : ERROR : ", e);
				}
			}
		} catch (IOException e1) {
			logger.error(" writeData. ERROR : ", e1);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				logger.error(" writeData-finally. ERROR : ", e);
			}
		}
	}
}
