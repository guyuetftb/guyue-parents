package com.gy.hadoop.input.xml;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.log4j.Logger;

public class AdObjectInputFormat extends FileInputFormat<Text, AdObject> {

    final static Logger logger = Logger.getLogger(AdObjectInputFormat.class);

    @Override
    public RecordReader<Text, AdObject> createRecordReader(InputSplit split, TaskAttemptContext context) {
        logger.info(" AdObjectInputFormat.RecordReader - [create AdObjectRecordReader]");
        return new AdObjectRecordReader();
    }

    @Override
    protected boolean isSplitable(JobContext job, Path file) {
        logger.info(" AdObjectInputFormat.isSplitable - [false]");
        return Boolean.FALSE;
    }
}
