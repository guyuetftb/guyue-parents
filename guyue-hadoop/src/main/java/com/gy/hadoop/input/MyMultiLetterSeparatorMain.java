package com.gy.hadoop.input;

import java.io.IOException;

import org.apache.commons.lang.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.log4j.Logger;

import com.gy.hadoop.AbstractTest;
import com.gy.hadoop.input.obj.MyPoint3D;
import com.gy.util.ConfigurationUtils;

public class MyMultiLetterSeparatorMain extends AbstractTest {

	final static Logger logger = Logger.getLogger(MyMultiLetterSeparatorMain.class);

	public static class MyMap extends Mapper<LongWritable, Text, Text, MyPoint3D> {

		@Override
		public void map(LongWritable key, Text value, Context context) {
			logger.info(" key = " + key);
			logger.info(" value = " + value);
			logger.info(" -------------------------- ");
		}
	}

	public static void main(String[] args) {
		String action = args[0];
		if ("test".equals(action)) {
			String host = args[1];
			String port = args[2];
			int recordSize = Integer.valueOf(args[3]);
			String output = args[4];
			Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);
			createTestData(conf, output, recordSize, null);
			logger.info(" Data of testing has created successfully.");
			return;
		}
		String host = args[0];
		String port = args[1];
		String input = args[2];
		Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);

		// create job
		String jobName = ClassUtils.getShortClassName(MyMultiLetterSeparatorMain.class);
		Job job = ConfigurationUtils.createJob(conf, jobName);

		job.setJarByClass(MyMultiLetterSeparatorMain.class);
		job.setMapperClass(MyMap.class);

		// set input,output format.
		job.setInputFormatClass(MyMultiLetterSeparatorInputFormat.class);
		job.setOutputFormatClass(NullOutputFormat.class);

		try {
			FileInputFormat.addInputPaths(job, input);
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
