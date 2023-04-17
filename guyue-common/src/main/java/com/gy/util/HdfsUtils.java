package com.gy.util;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

public class HdfsUtils {
	private static Logger logger = Logger.getLogger(HdfsUtils.class);

	public static void deleteFile(Configuration conf, String filePath) {
		try {
			FileSystem fs = FileSystem.get(URI.create(filePath), conf);
			Path path = new Path(filePath);
			if (fs.exists(path)) {
				if (fs.isDirectory(path) || fs.isFile(path)) {
					fs.delete(path, true);
					fs.close();
				}
			}
		} catch (Exception e) {
			logger.error(" deleteFile : ERROR : ", e);
		}
	}
}
