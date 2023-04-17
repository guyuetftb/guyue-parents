package com.gy.hadoop.input.obj;

import java.io.IOException;

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

public class MyPoint3DRecordReader extends RecordReader<Text, MyPoint3D> {

    final static Logger logger = Logger.getLogger(MyPoint3DRecordReader.class);

    private Text key = null;
    private Text line = null;
    private MyPoint3D value = null;
    private LineReader in;
    private FSDataInputStream fsDIS;
    private long start;
    private long stop;
    private int pos;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException {
        logger.info(" MyPoint3DRecordReader.initialize - 1.");
        logger.info(" MyPoint3DRecordReader.initialize - start=" + start);
        logger.info(" MyPoint3DRecordReader.initialize - stop=" + stop);
        logger.info(" MyPoint3DRecordReader.initialize - pos=" + pos);
        // 获取InputSplit
        // 确定开始,结束位置
        // 构建输入文件
        // 构建LineReader
        FileSplit fileSplit = (FileSplit) split;
        Configuration conf = context.getConfiguration();

        // 2.
        start = fileSplit.getStart();
        stop = (start + fileSplit.getLength());

        // 3.
        final Path filePath = fileSplit.getPath();

        // 4.
        FileSystem fileSystem = filePath.getFileSystem(conf);
        fsDIS = fileSystem.open(filePath);

        // 5.
        in = new LineReader(fsDIS, conf);
    }

    @Override
    public boolean nextKeyValue() throws IOException {
        logger.info(" MyPoint3DRecordReader.nextKeyValue - pos=" + pos);
        if (null == key) {
            key = new Text();
        }
        if (null == value) {
            value = new MyPoint3D();
        }
        if (null == line) {
            line = new Text();
        }

        int newLineSize = 0;
        if (pos <= stop) {
            newLineSize = in.readLine(line);
            if (newLineSize <= 0) {
                return Boolean.FALSE;
            }

            pos += newLineSize;
            String eachLine = line.toString();
            String[] pointArr = eachLine.split(",");
            if (pointArr.length != 4) {
                throw new IOException("Invalid record received");
            }

            int x, y, z;
            try {
                x = Integer.parseInt(pointArr[1].trim());// x
                y = Integer.parseInt(pointArr[2].trim());// y
                z = Integer.parseInt(pointArr[3].trim());// z
            } catch (NumberFormatException e) {
                throw new IOException("Error parsing floating poing value in record", e);
            }
            key.set(pointArr[0]);
            value.set(x, y, z);
            logger.info(" MyPoint3DRecordReader.nextKeyValue - set[key,value]");
        }
        return newLineSize == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public Text getCurrentKey() {
        logger.info(" MyPoint3DRecordReader.getCurrentKey - key=" + key);
        return key;
    }

    @Override
    public MyPoint3D getCurrentValue() {
        logger.info(" MyPoint3DRecordReader.getCurrentValue - value=" + value);
        return value;
    }

    @Override
    public float getProgress() {
        logger.info(" MyPoint3DRecordReader.getProgress - progress=0");
        return 0;
    }

    @Override
    public void close() throws IOException {
        logger.info(" MyPoint3DRecordReader.close -");
        if (null != in) {
            in.close();
        }
    }

}
