package com.gy.hadoop.testfwk;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.log4j.Logger;

import com.gy.hadoop.input.reader.MyMultiLetterSeparatorLineRecordReader;

public class MyMultiLetterSeparatorInputFormat extends FileInputFormat<LongWritable, Text> {

	final static Logger logger = Logger.getLogger(MyMultiLetterSeparatorInputFormat.class);

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) {
		logger.info(" createRecordReader - ");
		return new MyMultiLetterSeparatorLineRecordReader();
	}

	@Override
	protected boolean isSplitable(JobContext job, Path file) {
		logger.info(" isSplitable - [false]");
		return Boolean.FALSE;
	}
}
