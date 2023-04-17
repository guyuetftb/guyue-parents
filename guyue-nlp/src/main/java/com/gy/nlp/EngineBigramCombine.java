package com.gy.nlp;

import org.apache.commons.lang.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

public class EngineBigramCombine extends Configured implements Tool {

    private final static Logger logger = Logger.getLogger(EngineBigramCombine.class);

    private enum StatisticEnum {
        STATIC_WORD_NULL_OR_EMPTY,
        STATIC_WORD_LENGTH_GRATER_THAN_25,
        STATIC_WORD_ARR_LESS_4,
        STATIC_WORD_ARR_LESS_2,
        STATIC_WORD_PROCESS_NULL_OR_EMPTY,
        STATIC_WORD_YES,
        STATIC_WORD_NO
    }

    public static class MyMapper extends Mapper<Object, Text, Text, LongWritable> {

        static Text wordKey = new Text();
        static LongWritable timeVal = new LongWritable(0);

        @Override
        protected void cleanup(Context context) {
        }

        protected void setup(Context context) {
        }

        @Override
        public void map(Object keyObj, Text line, Context context) {

            if (line.getLength() <= 0) {
                context.getCounter(StatisticEnum.STATIC_WORD_NULL_OR_EMPTY).increment(1);
                return;
            }
            String row = line.toString();
            String[] rowSctionArr = row.split("\t");
            if (rowSctionArr.length < 2) {
                context.getCounter(StatisticEnum.STATIC_WORD_ARR_LESS_2).increment(1);
                return;
            }

            try {
                wordKey.set(rowSctionArr[0]);
                timeVal.set(Long.valueOf(rowSctionArr[1]));
                context.write(wordKey, timeVal);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyReduce extends Reducer<Text, LongWritable, Text, LongWritable> {

        static LongWritable timeVal = new LongWritable();

        @Override
        public void reduce(Text word, Iterable<LongWritable> iterator, Context context) throws IOException, InterruptedException {
            long times = 0;
            for (LongWritable t : iterator) {
                times += t.get();
            }
            timeVal.set(times);
            context.write(word, timeVal);
        }

    }

    public int run(String[] args) throws Exception {
        String input = args[0];
        String output = args[1];
        if (logger.isInfoEnabled()) {
            logger.info(" main.run [ input = " + input + ", output = " + output
                    + " ]");
        }

        // create configuration.
        Configuration conf = new Configuration();
        conf.set(HConstants.HBASE_RPC_TIMEOUT_KEY, "120000");

        // cached file
        //  DistributedCache.addCacheFile(new URI("/input/en_US_wordlist.combined#dictionary"), conf);
        // create job. The name of the job is "Calc_Status_Comment_Relatoin"
        Job job = Job.getInstance(conf, ClassUtils.getShortClassName(EngineBigramCombine.class));
        if (job == null) {
            logger.error(" ERROR : Job is " + job);
            return 1;
        }

        // set mapper
        job.setJarByClass(EngineBigramCombine.class);
        job.setMapperClass(EngineBigramCombine.MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.setInputPaths(job, input);

        // set reducer
        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setNumReduceTasks(36);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextOutputFormat.setOutputPath(job, new Path(output));
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static int executeTask(String args[]) {
        // run job
        logger.info(" main.executeTask [ start = " + System.currentTimeMillis()
                + " ] ");
        int code = 0;
        try {
            code = ToolRunner.run(new EngineBigramCombine(), args);
        } catch (Exception e) {
            logger.error("Error : ", e);
        }
        logger.info(" main.executeTask [ end = " + System.currentTimeMillis()
                + " ] ");
        return code;
    }

    public static void main(String[] args) {
        executeTask(args);
    }
}
