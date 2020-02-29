package com.gy.hadoop.sort.secondsort.copyblog;

/**
 * Created by lipeng on 1/14/18.
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义分区
 *
 * @author zengzhaozheng
 */
public class DefinedPartition extends Partitioner<CombinationKey, IntWritable> {
    private static final Logger logger = LoggerFactory.getLogger(DefinedPartition.class);

    /**
     * 数据输入来源：map输出
     *
     * @param key           map输出键值
     * @param value         map输出value值
     * @param numPartitions 分区总数，即reduce task个数
     * @author zengzhaozheng
     */
    @Override
    public int getPartition(CombinationKey key, IntWritable value, int numPartitions) {
        logger.info("--------enter DefinedPartition flag--------");
        /**
         * 注意：这里采用默认的hash分区实现方法
         * 根据组合键的第一个值作为分区
         * 这里需要说明一下，如果不自定义分区的话，mapreduce框架会根据默认的hash分区方法，
         * 将整个组合将相等的分到一个分区中，这样的话显然不是我们要的效果
         */
        logger.info("--------out DefinedPartition flag--------");
        /**
         * 此处的分区方法选择比较重要，其关系到是否会产生严重的数据倾斜问题
         * 采取什么样的分区方法要根据自己的数据分布情况来定，尽量将不同key的数据打散
         * 分散到各个不同的reduce进行处理，实现最大程度的分布式处理。
         */
        return (key.getFirstKey().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}
