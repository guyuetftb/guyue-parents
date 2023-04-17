package com.gy.hadoop.input.obj;

import java.io.IOException;

import org.apache.commons.lang.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.gy.util.ConfigurationUtils;
import com.gy.util.HdfsUtils;

public class MyPoint3DMain extends Configured implements Tool {

	final static Logger logger = Logger.getLogger(MyPoint3DMain.class);

	public static class MyMapper extends
			Mapper<Text, MyPoint3D, Text, MyPoint3D> {

		@Override
		public void map(Text key, MyPoint3D value, Context context) {
			logger.info(" MyMapper key = " + key + ", p = " + value);
			try {
				context.write(key, value);
			} catch (IOException e) {
				logger.error(" Error : ", e);
			} catch (InterruptedException e) {
				logger.error(" Error : ", e);
			} catch (Exception e) {
				logger.error(" Error : ", e);
			}
		}
	}

	public static class MyPartioner extends Partitioner<Text, MyPoint3D> {

		@Override
		public int getPartition(Text key, MyPoint3D value, int numPartitions) {
			logger.info(" MyPartioner key = " + key);
			return Long.valueOf(key.toString()).intValue() % numPartitions;
		}
	}

	public static class MyCombiner extends
			Reducer<Text, MyPoint3D, Text, MyPoint3D> {

		@Override
		public void reduce(Text key, Iterable<MyPoint3D> values, Context context) {
			logger.info(" MyCombiner key = " + key);
			for (MyPoint3D p : values) {
				try {
					context.write(key, p);
				} catch (IOException e) {
					logger.error(" Error : ", e);
				} catch (InterruptedException e) {
					logger.error(" Error : ", e);
				} catch (Exception e) {
					logger.error(" Error : ", e);
				}
			}
		}
	}

	public static class MyReducer extends
			Reducer<Text, MyPoint3D, Text, MyPoint3D> {

		@Override
		public void reduce(Text key, Iterable<MyPoint3D> values, Context context) {
			logger.info(" MyReducer key = " + key);
			for (MyPoint3D p : values) {
				try {
					context.write(key, p);
				} catch (IOException e) {
					logger.error(" Error : ", e);
				} catch (InterruptedException e) {
					logger.error(" Error : ", e);
				} catch (Exception e) {
					logger.error(" Error : ", e);
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(" result-code = "
					+ ToolRunner.run(new MyPoint3DMain(), args));
		} catch (Exception e) {
			logger.error(" Error : ", e);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		String host = args[0];
		String port = args[1];
		String input = args[2];
		String output = args[3];

		Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);

		// create job
		String jobName = ClassUtils.getShortClassName(MyPoint3DMain.class);
		Job job = ConfigurationUtils.createJob(conf, jobName);

		// set input,output format.
		job.setInputFormatClass(MyPoint3DInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// main class
		job.setJarByClass(MyPoint3DMain.class);

		// mapper class
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MyPoint3D.class);

		// reducer class
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MyPoint3D.class);

		// combiner
		job.setCombinerClass(MyCombiner.class);

		// partioner
		job.setPartitionerClass(MyPartioner.class);

		// reduce-number
		job.setNumReduceTasks(5);

		ControlledJob cj = new ControlledJob(conf);
		cj.setJob(job);

		JobControl jobC = new JobControl("1234");
		jobC.addJob(cj);
		
		ChainMapper.addMapper(job, MyMapper.class, Text.class, Text.class, Text.class, Text.class, job.getConfiguration());
		ChainReducer.setReducer(job, MyReducer.class, Text.class, Text.class, Text.class, Text.class, job.getConfiguration());
		

		int returnCode = -1;

		try {
			// input-path
			FileInputFormat.addInputPaths(job, input);

			// output-path
			HdfsUtils.deleteFile(conf, output);
			TextOutputFormat.setOutputPath(job, new Path(output));
			returnCode = job.waitForCompletion(true) ? 0 : 1;
		} catch (IOException e) {
			logger.error(" Error : ", e);
		} catch (InterruptedException e) {
			logger.error(" Error : ", e);
		} catch (ClassNotFoundException e) {
			logger.error(" Error : ", e);
		}
		return returnCode;
	}
}
