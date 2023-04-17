package com.gy.hadoop.input.obj;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.log4j.Logger;

public class MyPoint3DInputFormat extends FileInputFormat<Text, MyPoint3D> {

	final static Logger logger = Logger.getLogger(MyPoint3DInputFormat.class);

	@Override
	public RecordReader<Text, MyPoint3D> createRecordReader(InputSplit split, TaskAttemptContext context) {
		logger.info(" MyPoint3DInputFormat.RecordReader - [create MyPoint3DRecordReader]");
		return new MyPoint3DRecordReader();
	}

	@Override
	protected boolean isSplitable(JobContext job, Path file) {
		logger.info(" MyPoint3DInputFormat.isSplitable - [false]");
		return Boolean.FALSE;
	}
}
