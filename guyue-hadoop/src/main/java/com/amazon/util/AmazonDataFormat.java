package com.amazon.util;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * 
 * @author daniel
 * 
 *         现在着重写这个输入的类,因为amazon的数据格式是我们定制的
 * 
 */
public class AmazonDataFormat extends FileInputFormat<Text, Text> {

	private AmazonDataReader dataReader = null;

	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException,
			InterruptedException {
		dataReader = new AmazonDataReader();
		dataReader.initialize(split, context);
		return dataReader;
	}

}
