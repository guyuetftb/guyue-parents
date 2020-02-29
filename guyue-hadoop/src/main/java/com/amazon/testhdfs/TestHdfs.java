package com.amazon.testhdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHdfs {

	private static FileSystem fileSystem;

	@Before
	public void beforMethod() throws Exception {
		fileSystem = FileSystem.get(URI.create("hdfs://172.16.0.90:9000/"),
				new Configuration());
	}

	@After
	public void afterMethod() throws Exception {
		FileSystem.closeAll();
	}

	@Test
	public void testCopyFromLocal() throws Exception {
		String localFilePath = "/Users/daniel/Desktop/miniamazon.txt";
		String hdfsFilePath = "/testme/miniamazon";

		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				localFilePath));

		FSDataOutputStream outputStream = fileSystem.create(new Path(
				hdfsFilePath), new Progressable() {
			@Override
			public void progress() {
				// 这里可以显示拷贝的进度
				System.out.println(".....");
			}

		});
		IOUtils.copyBytes(inputStream, outputStream, 4096, true);
	}

}
