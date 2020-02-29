package com.gy.hadoop.partionner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.gy.util.ConfigurationUtils;
import com.gy.util.HdfsUtils;

public class WordCount extends Configured implements Tool {

    private static final Logger logger = Logger.getLogger(WordCount.class);

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new WordCount(), args);
        System.exit(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.printf("Args missing. Input path and output path is required.");
            return -1;
        }

        String input = args[0];
        String output = args[1];

        Configuration conf = ConfigurationUtils.createConfiguratoin();
        conf.setStrings("mapreduce.job.queuename", "QProject");
        Job job = ConfigurationUtils.createJob(conf, "Calculate words frequencies");
        // 设置reducer读个数。每个reducer最终会产生一个输出文件
        job.setNumReduceTasks(10);

        FsPermission a = null;

        // 自定义分区类
        job.setJarByClass(WordCount.class);

        FileInputFormat.addInputPaths(job, input);

        HdfsUtils.deleteFile(conf, output);
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.setMapperClass(WordsFrequenciesMapper.class);
        job.setReducerClass(WordsFrequenciesReducer.class);
        job.setCombinerClass(WordsFrequenciesCombiner.class);
        // 本例中，长度相同的单词会被同一个reducer处理，最终也会出现在同一个输出文件中
        job.setPartitionerClass(WordsFrequenciesPartitioner.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
