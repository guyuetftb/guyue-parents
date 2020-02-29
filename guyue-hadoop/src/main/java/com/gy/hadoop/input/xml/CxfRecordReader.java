package com.gy.hadoop.input.xml;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;

public class CxfRecordReader implements RecordReader<IntWritable, Text> {

	private LineRecordReader in;
	private LongWritable junk = new LongWritable();
	private Text line = new Text();
	private int KEY_LENGTH = 10;

	public CxfRecordReader(JobConf job, FileSplit split) throws IOException {
		in = new LineRecordReader(job, split);
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	@Override
	public IntWritable createKey() {
		return new IntWritable();
	}

	@Override
	public Text createValue() {

		return new Text();
	}

	@Override
	public long getPos() throws IOException {

		return in.getPos();
	}

	@Override
	public float getProgress() throws IOException {

		return in.getProgress();
	}

	@Override
	public boolean next(IntWritable key, Text value) throws IOException {
		if (in.next(junk, line)) {
			if (line.getLength() < KEY_LENGTH) {
				key.set(Integer.parseInt(line.toString()));
				value = new Text();
				// value.clear();
			} else {
				byte[] bytes = line.getBytes();
				key.set(Integer.parseInt(new String(bytes).substring(0, KEY_LENGTH)));
				value = new Text();
			}
			return true;
		} else {
			return false;
		}
	}
}
