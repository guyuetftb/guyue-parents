package com.gy.hadoop.input.reader;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.log4j.Logger;

public class MyMultiLetterSeparatorLineRecordReader extends RecordReader<LongWritable, Text> {

	final static Logger logger = Logger.getLogger(MyMultiLetterSeparatorLineRecordReader.class);

	private LongWritable key = null;
	private Text value = null;
	private MyMultiLetterSeparatorRecordReader in;
	private FSDataInputStream fsDIS;
	private long start;
	private long stop;
	private int pos;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException {
		logger.info(" initialize - 1.");
		logger.info(" initialize - start=" + start);
		logger.info(" initialize - stop=" + stop);
		logger.info(" initialize - pos=" + pos);
		// 1.将InputSplit转为FileSplit
		// 2.确定Start,Stop
		// 3.获取物理文件
		// 4.构建输入流
		// 5.创建读入行.
		FileSplit fileSplit = (FileSplit) split;
		Configuration conf = context.getConfiguration();

		// 2.
		start = fileSplit.getStart();
		stop = (start + fileSplit.getLength());

		// 3.
		final Path filePath = fileSplit.getPath();

		// 4.
		FileSystem fileSystem = filePath.getFileSystem(conf);
		fsDIS = fileSystem.open(filePath);

		// 5.
		in = new MyMultiLetterSeparatorRecordReader(fsDIS, conf);
	}

	@Override
	public boolean nextKeyValue() throws IOException {
		logger.info(" nextKeyValue - pos=" + pos);
		if (null == key) {
			key = new LongWritable();
		}
		key.set(pos);
		if (null == value) {
			value = new Text();
		}

		int newLineSize = 0;
		if (pos <= stop) {
			newLineSize = in.readLine(value);
			if (newLineSize <= 0) {
				return Boolean.FALSE;
			}

			pos += newLineSize;
			logger.info(" nextKeyValue - set[key,value]");
		}
		return newLineSize == 0 ? Boolean.FALSE : Boolean.TRUE;
	}

	@Override
	public LongWritable getCurrentKey() {
		logger.info(" getCurrentKey - key=" + key);
		return key;
	}

	@Override
	public Text getCurrentValue() {
		logger.info(" getCurrentValue - value=" + value);
		return value;
	}

	@Override
	public float getProgress() {
		logger.info(" getProgress - progress=0");
		if (start == stop) {
			return 0.0f;
		} else {
			return Math.min(1.0f, (this.pos - this.start) / (this.stop - this.start));
		}
	}

	@Override
	public void close() throws IOException {
		logger.info(" close -");
		if (null != in) {
			in.close();
		}
	}
}
