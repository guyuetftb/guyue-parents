package com.gy.hadoop.counter;

import java.io.IOException;

import org.apache.commons.lang.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.gy.util.ConfigurationUtils;

public class MyCounterJob {

	enum GenderCounter {
		MALE, FEMALE, OTHER
	}

	public static class MyCounterMap extends Mapper<Text, Text, Text, Text> {

		public static Counter ct = null;

		protected void map(Text key, Text value, Context context) {
			String arr_value[] = value.toString().split("\t");
			if (null == arr_value || arr_value.length < 2) {
				ct = context.getCounter(GenderCounter.OTHER);
				ct.increment(1);
				return;
			}

			if ("m".equals(arr_value[5])) {
				ct = context.getCounter(GenderCounter.MALE);
				ct.increment(1);
			} else {
				ct = context.getCounter(GenderCounter.FEMALE);
				ct.increment(1);
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		String host = args[0];
		String port = args[1];
		Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: MyCounter <in> <out>");
			System.exit(2);
		}

		// create job
		String jobName = ClassUtils.getShortClassName(MyCounterJob.class);
		Job job = ConfigurationUtils.createJob(conf, jobName);

		// set input,output format.
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputFormatClass(NullOutputFormat.class);

		job.setJarByClass(MyCounterJob.class);
		job.setMapperClass(MyCounterMap.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[2]));
		int exeCode = job.waitForCompletion(true) ? 0 : 1;

		String num = job.getCounters().findCounter(GenderCounter.FEMALE).getName();
		System.out.println(" job counter = " + num);
		System.exit(exeCode);
	}
}