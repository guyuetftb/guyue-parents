package com.gy.nlp;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.io.IntWritable;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngineBigramPretreatment extends Configured implements Tool {

    private final static Logger logger = Logger.getLogger(EngineBigramPretreatment.class);

    private enum StatisticEnum {
        STATIC_WORD_NULL_OR_EMPTY,
        STATIC_WORD_LENGTH_GRATER_THAN_25,
        STATIC_WORD_ARR_LESS_4,
        STATIC_WORD_ARR_LESS_2,
        STATIC_WORD_PROCESS_NULL_OR_EMPTY,
        STATIC_WORD_YES,
        STATIC_WORD_NO
    }

    public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

        static String SPACE = " ";

        static Pattern wordUSPattern = Pattern.compile("^[A-Za-z']+$");
        static Pattern wordESPattern = Pattern.compile("^[\\u00C0-\\u00FFA-Za-z']+$");
        static Pattern wordPTPattern = Pattern.compile("^[\\u00E0\\u00C0\\u00E1\\u00C1\\u00E2\\u00C2\\u00E3\\u00C3\\u00E7\\u00C7\\u00E9\\u00C9\\u00EA\\u00CA\\u00ED\\u00CD\\u00F3\\u00D3\\u00F4\\u00D4\\u00F5\\u00D5\\u00FA\\u00EA\\u00FC\\u00ECA-Za-z']+$");
        static Pattern wordFRPattern = Pattern.compile("^[\\u00C0\\u00C1\\u00C2\\u00C3\\u00C7\\u00C8\\u00C9\\u00CA\\u00CC\\u00CD\\u00CE\\u00CF\\u00D2\\u00D3\\u00D4\\u00D5\\u00D9\\u00DA\\u00DB\\u00E0\\u00E1\\u00E2\\u00E3\\u00E7\\u00E8\\u00E9\\u00EA\\u00EC\\u00ED\\u00EE\\u00EF\\u00F2\\u00F3\\u00F4\\u00F5\\u00F9\\u00FA\\u00FBA-Za-z']+$");
        static Pattern wordPattern = null;

        static Text wordKey = new Text();
        static Text wordSrcVal = new Text();
        static IntWritable timeVal = new IntWritable(1);

        static HashSet<String> wordDec = new HashSet<String>();
        static List<String> wordList = new ArrayList();

        @Override
        protected void cleanup(Context context) {
            wordDec = null;
        }

        protected void setup(Context context) {
            String code = context.getConfiguration().get("code", "US");
            if (code.equals("US")) {
                wordPattern = wordUSPattern;
            } else if (code.equals("PT")) {
                wordPattern = wordPTPattern;
            } else if (code.equals("FR")) {
                wordPattern = wordFRPattern;
            } else {
                wordPattern = wordESPattern;
            }
        }

        @Override
        public void map(Object keyObj, Text line, Context context) {

            if (line.getLength() <= 0) {
                context.getCounter(StatisticEnum.STATIC_WORD_NULL_OR_EMPTY).increment(1);
                return;
            }
            String row = line.toString();
            String[] rowSctionArr = row.split("\t");
            if (rowSctionArr.length < 4) {
                context.getCounter(StatisticEnum.STATIC_WORD_ARR_LESS_4).increment(1);
                return;
            }

            String sentence = rowSctionArr[1].replaceAll("[&?.!,:;\\-_()\\[\\]{}]", " ");
            if (null == sentence || sentence.length() <= 0) {
                context.getCounter(StatisticEnum.STATIC_WORD_PROCESS_NULL_OR_EMPTY).increment(1);
                return;
            }

            // 内容
            String[] wordArr = sentence.split(SPACE);
            for (String w : wordArr) {
                w = w.trim().toLowerCase();
                // 去空
                if (StringUtils.isEmpty(w)) {
                    continue;
                }
                // 只是一个单引号
                if ("'".equals(w)) {
                    continue;
                }
                // 去掉 ‘首尾',’首'单引号
                while (true) {
                    if (w.startsWith("'") && w.endsWith("'")) {
                        w = w.substring(1, w.length() - 1);
                    } else if (w.startsWith("'")) {
                        w = w.substring(1);
                    } else {
                        break;
                    }
                    // 去空
                    if (StringUtils.isEmpty(w)) {
                        return;
                    }
                    // 只是一个单引号
                    if ("'".equals(w)) {
                        return;
                    }
                }
                // 是单词
                wordList.add(w);
            }

            if (null != wordList && wordList.size() > 1) {
                try {
                    for (int index = 1; index < wordList.size(); index++) {

                        String pre = wordList.get(index - 1);
                        String suf = wordList.get(index);
                        if (pre.length() > 25 || suf.length() > 25) {
                            continue;
                        }
                        Matcher matcherPre = wordPattern.matcher(pre);
                        Matcher matcherSuf = wordPattern.matcher(suf);
                        if (matcherPre.find() && matcherSuf.find()) {
                            wordKey.set(pre + " " + suf);
                            context.write(wordKey, timeVal);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            wordList.clear();
        }
    }

    public static class MyReduce extends Reducer<Text, IntWritable, Text, LongWritable> {

        static LongWritable timeVal = new LongWritable();

        @Override
        public void reduce(Text word, Iterable<IntWritable> iterator, Context context) throws IOException, InterruptedException {
            long times = 0;
            for (IntWritable t : iterator) {
                times++;
            }
            timeVal.set(times);
            context.write(word, timeVal);
        }
    }

    public int run(String[] args) throws Exception {
        String input = args[0];
        String output = args[1];
        String countryCode = null;
        if (args.length > 2) {
            countryCode = args[2].toUpperCase();
        } else {
            countryCode = "US";
        }
        if (logger.isInfoEnabled()) {
            logger.info(" main.run [ input = " + input + ", output = " + output
                    + ", country_code = " + countryCode + " ]");
        }

        // create configuration.
        Configuration conf = new Configuration();
        conf.set(HConstants.HBASE_RPC_TIMEOUT_KEY, "120000");
        conf.set("code", countryCode);

        // cached file
        //  DistributedCache.addCacheFile(new URI("/input/en_US_wordlist.combined#dictionary"), conf);
        // create job. The name of the job is "Calc_Status_Comment_Relatoin"
        Job job = Job.getInstance(conf, ClassUtils.getShortClassName(EngineBigramPretreatment.class) + "[" + countryCode + "]");
        if (job == null) {
            logger.error(" ERROR : Job is " + job);
            return 1;
        }

        // set mapper
        job.setJarByClass(EngineBigramPretreatment.class);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
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
            code = ToolRunner.run(new EngineBigramPretreatment(), args);
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
