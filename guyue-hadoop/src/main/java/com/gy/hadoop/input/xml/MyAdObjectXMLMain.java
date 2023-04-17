package com.gy.hadoop.input.xml;

import com.gy.util.ConfigurationUtils;
import com.gy.util.HdfsUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MyAdObjectXMLMain extends Configured implements Tool {

    final static Logger logger = Logger.getLogger(MyAdObjectXMLMain.class);

    public static class MyMapper extends
            Mapper<Text, AdObject, Text, AdObject> {

        @Override
        public void map(Text key, AdObject value, Context context) {
            logger.info(" MyMapper key = " + key + ", p = " + value);
            try {
                context.write(key, value);
            } catch (IOException e) {
                logger.error(" Error : ", e);
            } catch (InterruptedException e) {
                logger.error(" Error : ", e);
            } catch (Exception e) {
                logger.error(" Error : ", e);
            }
        }
    }

    public static class MyPartioner extends Partitioner<Text, AdObject> {

        @Override
        public int getPartition(Text key, AdObject value, int numPartitions) {
            /**
             * 如果只有一个reduce的话,该方法将不会调用
             */
            logger.info(" MyPartioner key = " + key);
            return (value.hashCode() & 0x7FFFFFFF) % numPartitions;
        }
    }

    public static class MyCombiner extends
            Reducer<Text, AdObject, Text, AdObject> {

        @Override
        public void reduce(Text key, Iterable<AdObject> values, Context context) {
            logger.info(" MyCombiner key = " + key);
            for (AdObject p : values) {
                try {
                    context.write(key, p);
                } catch (IOException e) {
                    logger.error(" Error : ", e);
                } catch (InterruptedException e) {
                    logger.error(" Error : ", e);
                } catch (Exception e) {
                    logger.error(" Error : ", e);
                }
            }
        }
    }

    public static class MyReducer extends
            Reducer<Text, AdObject, Text, AdObject> {

        @Override
        public void reduce(Text key, Iterable<AdObject> values, Context context) {
            logger.info(" MyReducer key = " + key);
            for (AdObject p : values) {
                try {
                    context.write(key, p);
                } catch (IOException e) {
                    logger.error(" Error : ", e);
                } catch (InterruptedException e) {
                    logger.error(" Error : ", e);
                } catch (Exception e) {
                    logger.error(" Error : ", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(" result-code = "
                    + ToolRunner.run(new MyAdObjectXMLMain(), args));
        } catch (Exception e) {
            logger.error(" Error : ", e);
        }
    }

    @Override
    public int run(String[] args) {
        String input = args[0];
        String output = args[1];

        Configuration conf = ConfigurationUtils.createConfiguratoin(null, null);

        // create job
        String jobName = ClassUtils.getShortClassName(MyAdObjectXMLMain.class);
        Job job = ConfigurationUtils.createJob(conf, jobName);

        // main class
        job.setJarByClass(MyAdObjectXMLMain.class);

        // mapper class
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(AdObject.class);

        // reducer class
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(AdObject.class);

        // combiner
        job.setCombinerClass(MyCombiner.class);

        // partioner
        job.setPartitionerClass(MyPartioner.class);

        // set input,output format.
        job.setInputFormatClass(AdObjectInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        // reduce-number
        job.setNumReduceTasks(2);

        int returnCode = -1;
        try {
            // input-path
            FileInputFormat.addInputPaths(job, input);

            // output-path
            HdfsUtils.deleteFile(conf, output);
            TextOutputFormat.setOutputPath(job, new Path(output));
            returnCode = job.waitForCompletion(true) ? 0 : 1;
        } catch (IOException e) {
            logger.error(" Error : ", e);
        } catch (InterruptedException e) {
            logger.error(" Error : ", e);
        } catch (ClassNotFoundException e) {
            logger.error(" Error : ", e);
        }
        return returnCode;
    }
}
