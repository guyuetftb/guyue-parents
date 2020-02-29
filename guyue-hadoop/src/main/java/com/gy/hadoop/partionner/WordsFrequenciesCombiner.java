package com.gy.hadoop.partionner;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

public class WordsFrequenciesCombiner extends org.apache.hadoop.mapreduce.Reducer<Text, LongWritable, Text, LongWritable> {

	private static final Logger logger = Logger.getLogger(WordCount.class);

	@Override
	protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		long counts = 0;
		logger.info("\t Combiner Key -->" + key);
		for (LongWritable value : values) {
			counts += value.get();
		}
		logger.info("\t Combiner Key -->" + key + ", counts=" + counts);
		context.write(key, new LongWritable(counts));
	}
}
