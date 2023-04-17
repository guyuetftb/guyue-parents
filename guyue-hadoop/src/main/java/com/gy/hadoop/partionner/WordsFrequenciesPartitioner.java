package com.gy.hadoop.partionner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.log4j.Logger;

public class WordsFrequenciesPartitioner extends Partitioner<Text, LongWritable> {

	private static final Logger logger = Logger.getLogger(WordCount.class);

	@Override
	public int getPartition(Text key, LongWritable value, int numOfReducer) {
		// 本例设置reducer个数为25,所以比如长度为26的单词会和长度为1的单词分配到同一个分区
		logger.info("\t Partioner Key -->" + key + ", value --> " + value.get());
		return key.toString().length() % numOfReducer;
	}
}
