package com.gy.hadoop.partionner;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class WordsFrequenciesMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	private static final Logger logger = Logger.getLogger(WordCount.class);

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		logger.info("\t Mapper Key -->" + key);
		String line = value.toString().trim();
		if (line.length() == 0) {
			return;
		}

		String[] wordStrings = line.split("\\s+");
		for (String word : wordStrings) {
			context.write(new Text(word.toLowerCase()), new LongWritable(1));
		}
	}
}
