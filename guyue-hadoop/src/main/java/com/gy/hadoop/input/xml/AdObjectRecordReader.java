package com.gy.hadoop.input.xml;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;
import org.apache.log4j.Logger;

public class AdObjectRecordReader extends RecordReader<Text, AdObject> {

    final static Logger logger = Logger.getLogger(AdObjectRecordReader.class);

    private Text key;
    private Text tmp;
    private AdObject adObjectVal;
    private LineReader lineReader;
    private FSDataInputStream fsDataIS;
    private long start;
    private long end;
    private long pos;
    private int fullRecord;

    private String elementStart = "<offers>";
    private String elementEnd = "</offers>";
    private String regExHtml = "<[^>]+>";

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException {
        // 获取InputSplit
        // 确定开始,结束位置
        // 构建输入文件
        // 构建文件输入流
        // 构建LineReader
        logger.info(" .initialize - start=" + start);
        logger.info(" .initialize - end=" + end);
        logger.info(" .initialize - pos=" + pos);

        // 1. inputsplit
        FileSplit fileSplit = (FileSplit) split;
        Configuration conf = context.getConfiguration();
        logger.info(" 1-1-- \t initialize");

        // location
        start = fileSplit.getStart();
        end = (start + fileSplit.getLength());
        logger.info(" 1-2-- \t initialize");

        // new file
        final Path path = fileSplit.getPath();
        FileSystem hdfs = FileSystem.get(conf);
        logger.info(" 1-3-- \t initialize");

        // file-input stream
        fsDataIS = hdfs.open(path);
        logger.info(" 1-4-- \t initialize");

        // line reader
        lineReader = new LineReader(fsDataIS);
        logger.info(" 1-5-- \t initialize");
    }

    @Override
    public boolean nextKeyValue() throws IOException {
        if (null == key) {
            key = new Text();
            logger.info(" 2-1-- \t nextKeyValue");
        }
        if (null == tmp) {
            tmp = new Text();
            logger.info(" 2-2-- \t nextKeyValue");
        }
        if (null == adObjectVal) {
            adObjectVal = new AdObject();
            logger.info(" 2-3-- \t nextKeyValue");
        }

        int newLineSize = 0;
        if (pos <= end) {
            logger.info(" 2-4-- \t nextKeyValue");
            fullRecord = 0;
            do {
                newLineSize = lineReader.readLine(tmp);
                // file end.
                if (newLineSize <= 0) {
                    return Boolean.FALSE;
                }
                end += newLineSize;

                String tmpStr = tmp.toString().trim();
                // logger.info(tmpStr);
                // file head
                if (tmpStr.indexOf("<?xml") > -1) {
                    continue;
                }

                // element-start <offers>
                if (elementStart.equals(tmpStr)) {
                    logger.info(" 2-5-elementStart- \t nextKeyValue");
                    continue;
                }

                // element-end <offers>
                if (StringUtils.isNotEmpty(elementStart) && elementEnd.equals(tmpStr)) {
                    fullRecord = 2;
                    logger.info(" 2-5-elementEnd- \t nextKeyValue");
                    break;
                }

                // title
                if (tmpStr.indexOf("<title>") > -1) {
                    adObjectVal.setTitle(tmpStr.replaceAll(regExHtml, ""));
                }

                // id
                if (tmpStr.indexOf("<campaign_id>") > -1) {
                    String id = tmpStr.replaceAll(regExHtml, "");
                    adObjectVal.setId(id);
                    key.set(id);
                }

                // url
                if (tmpStr.indexOf("<click_url>") > -1) {
                    adObjectVal.setUrl(tmpStr.replaceAll(regExHtml, ""));
                }
            } while (true);
        }
        return newLineSize > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Text getCurrentKey() {
        logger.info(" 3--- \t getCurrentKey");
        return key;
    }

    @Override
    public AdObject getCurrentValue() {
        logger.info(" 4--- \t getCurrentValue");
        return adObjectVal;
    }

    @Override
    public float getProgress() {
        logger.info(" 5--- \t getProgress");
        return 0;
    }

    @Override
    public void close() throws IOException {
        if (null != fsDataIS) {
            logger.info(" 6--- \t close");
            fsDataIS.close();
        }
    }
}
