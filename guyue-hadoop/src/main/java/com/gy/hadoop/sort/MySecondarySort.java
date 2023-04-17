/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gy.hadoop.sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;

import com.gy.util.ConfigurationUtils;
import com.gy.util.HdfsUtils;

public class MySecondarySort {

    final static Logger logger = Logger.getLogger(MySecondarySort.class);

    public static class IntPair implements WritableComparable<IntPair> {

        int first;
        int second;

        public void set(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public IntPair(int first, int second) {
            this();
            this.first = first;
            this.second = second;
        }

        public IntPair() {
            super();
        }

        @Override
        public void readFields(DataInput input) throws IOException {
            this.first = input.readInt();
            this.second = input.readInt();
        }

        @Override
        public void write(DataOutput output) throws IOException {
            output.writeInt(first);
            output.writeInt(second);
        }


        @Override
        public int compareTo(IntPair o) {
            // 对值进行二次排序
            // 先按: first排序,如果first相同的
            // 再按: second排序
            logger.info(" IntPair.compareTo first = " + first);
            if (this.first != o.first) {
                return this.first < o.first ? -1 : 1;
            } else if (this.second != o.second) {
                return this.second < o.second ? -1 : 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "IntPair [first=" + first + ", second=" + second + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (null == o) {
                return Boolean.FALSE;
            }
            if (!(o instanceof IntPair)) {
                return Boolean.FALSE;
            }
            if (this == o) {
                return Boolean.TRUE;
            }
            IntPair another = (IntPair) o;
            if (another.getFirst() == this.getFirst() && another.getSecond() == this.getSecond()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        /**
         * A Comparator that compares serialized IntPair.
         */
        public static class Comparator extends WritableComparator {
            public Comparator() {
                super(IntPair.class);
            }

            public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
                logger.info(" IntPair.Comparator");
                return compareBytes(b1, s1, l1, b2, s2, l2);
            }
        }

        static { // register this comparator
            WritableComparator.define(IntPair.class, new Comparator());
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }
    }

    /**
     * 数据输入来源：map输出
     */
    public static class FirstPartitioner extends Partitioner<IntPair, IntWritable> {

        @Override
        public int getPartition(IntPair key, IntWritable value, int numPartitions) {

            /**
             * 注意：这里采用默认的hash分区实现方法
             * 根据组合键的第一个值作为分区
             *
             * 这里需要说明一下，如果不自定义分区的话，mapreduce框架会根据默认的hash分区方法
             * 将整个组合将相等的分到一个分区中，这样的话显然不是我们要的效果
             *
             * 此处的分区方法选择比较重要，其关系到是否会产生严重的数据倾斜问题
             * 采取什么样的分区方法要根据自己的数据分布情况来定，尽量将不同key的数据打散
             * 分散到各个不同的reduce进行处理，实现最大程度的分布式处理。
             */
            logger.info(" FirstPartitioner.getPartition first = " + key.getFirst());
            return Math.abs(key.getFirst() * 127) % numPartitions;
        }
    }

    public static class GroupComparator extends WritableComparator {

        protected GroupComparator() {
            super(IntPair.class, true);
        }

        @Override
        public int compare(WritableComparable one, WritableComparable two) {
            IntPair first = (IntPair) one;
            IntPair second = (IntPair) two;
            logger.info(" GroupComparator.compare first = " + first.getFirst());
            return first.getFirst() - second.getFirst();
        }
    }

    public static class MyMap extends Mapper<LongWritable, Text, IntPair, IntWritable> {

        private final IntPair key = new IntPair();
        private final IntWritable value = new IntWritable();

        @Override
        public void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(inValue.toString());
            int left = 0;
            int right = 0;
            if (itr.hasMoreTokens()) {
                left = Integer.parseInt(itr.nextToken());
                if (itr.hasMoreTokens()) {
                    right = Integer.parseInt(itr.nextToken());
                }
                key.set(left, right);
                value.set(right);
                context.write(key, value);
            }
        }
    }

    public static class MyReduce extends Reducer<IntPair, IntWritable, Text, IntWritable> {
        private static final Text SEPARATOR = new Text("------------------------------------------------");
        private final Text first = new Text();

        /**
         * 这里要注意一下reduce的调用时机和次数:
         * reduce每处理一个分组的时候会调用一次reduce函数。
         * 也许有人会疑问，分组是什么？看个例子就明白了：
         * eg:
         * {
         * {sort1,{1,2}},
         * {sort2,{3,54,77}},
         * {sort6,{20,22,221}}
         * }
         * 这个数据结果是分组过后的数据结构，那么一个分组分别为{sort1,{1,2}}、
         * {sort2,{3,54,77}}、{sort6,{20,22,221}}
         */
        @Override
        public void reduce(IntPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            context.write(SEPARATOR, null);
            first.set(Integer.toString(key.getFirst()));
            for (IntWritable value : values) {
                context.write(first, value);
            }
        }
    }

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];
        String input = args[2];
        String output = args[3];
        String reduceNum = args[4];
        Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);
        Job job = ConfigurationUtils.createJob(conf, "secondary sort");
        job.setJarByClass(MySecondarySort.class);

        // set mapper class.
        job.setMapperClass(MyMap.class);
        job.setMapOutputKeyClass(IntPair.class);
        job.setMapOutputValueClass(IntWritable.class);

        /**
         * 在Mapper的map函数写出之后，就立即调用.
         */
        job.setPartitionerClass(FirstPartitioner.class);

        /**
         * 在Reduce端，即将要进入reduce方法之前调用
         * 对 IntPair 的第一个值相同的数据进行排序，然后将其分配到同一个组中.
         */
        job.setGroupingComparatorClass(GroupComparator.class);

        /**
         * 如果没有通过job.setSortComparatorClass设置key比较函数类，则使用key的实现的compareTo方法。
         */

        // set reducer class.
        job.setReducerClass(MyReduce.class);
        job.setNumReduceTasks(Integer.valueOf(reduceNum));
        job.setOutputKeyClass(IntPair.class);
        job.setOutputValueClass(IntWritable.class);

        // set input-format,output-format.
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        try {
            TextInputFormat.setInputPaths(job, input);

            HdfsUtils.deleteFile(conf, output);
            TextOutputFormat.setOutputPath(job, new Path(output));
        } catch (IOException e) {
            logger.error(" ERROR : ", e);
        }

        try {
            System.out.println(job.waitForCompletion(Boolean.TRUE));
        } catch (IOException e) {
            logger.error(" ERROR : ", e);
        } catch (InterruptedException e) {
            logger.error(" ERROR : ", e);
        } catch (ClassNotFoundException e) {
            logger.error(" ERROR : ", e);
        }
    }
}
