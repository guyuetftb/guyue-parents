package com.gy.hadoop.sort;

import com.gy.hadoop.input.xml.CxfInputFormat;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.InputSampler;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SortByMapReduce {

    /**
     * @param args
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        runJob(args);
    }

    private static void runJob(String[] args) throws IOException, URISyntaxException {

        JobConf conf = new JobConf(SortByMapReduce.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        conf.setJobName("SortByMapReduce");

        conf.setInputFormat(CxfInputFormat.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputFormat(TextOutputFormat.class);
        conf.setNumReduceTasks(5);
        conf.setPartitionerClass(TotalOrderPartitioner.class);
        InputSampler.RandomSampler<LongWritable, NullWritable> sampler = new InputSampler.RandomSampler<LongWritable, NullWritable>(0.1, 10000, 10);

        Path input = FileInputFormat.getInputPaths(conf)[0];
        input = input.makeQualified(input.getFileSystem(conf));
        Path partitionFile = new Path(input, "_partitions");
        TotalOrderPartitioner.setPartitionFile(conf, partitionFile);
//        InputSampler.writePartitionFile(conf, sampler);

        URI partitionURI = new URI(partitionFile.toString() + "#_partitions");
        DistributedCache.addCacheFile(partitionURI, conf);
        DistributedCache.createSymlink(conf);
        JobClient.runJob(conf);
    }
}
