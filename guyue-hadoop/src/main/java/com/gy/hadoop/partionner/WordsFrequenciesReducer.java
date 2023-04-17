package com.gy.hadoop.partionner;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class WordsFrequenciesReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	private static final Logger logger = Logger.getLogger(WordCount.class);

	@Override
	protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		logger.info("\t Reducer Key -->" + key);
		long counts = 0;
		for (LongWritable value : values) {
			counts += value.get();
		}
		logger.info("\t Reducer Key -->" + key + ", value --> " + counts);
		context.write(key, new LongWritable(counts));
	}
}