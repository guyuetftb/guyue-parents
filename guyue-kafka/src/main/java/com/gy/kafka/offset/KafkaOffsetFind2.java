package com.gy.kafka.offset;

/**
 * @ClassName KafkaOffsetFind2
 * @Description TOOD
 * @Author lipeng
 * @Date 2019/7/4 19:38
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

// https://blog.csdn.net/define_us/article/details/84064992
public class KafkaOffsetFind2 {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.3.32.47:9092,10.3.32.8:9092,10.3.32.4:9092");
        props.put("group.id", "eventlog_offset_001");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "zsc_new_event_log";

        try {
            // 获取topic的partition信息
            List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
            List<TopicPartition> topicPartitions = new ArrayList<>();

            Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            long nowTime = now.getTime();
            System.out.println("====== 当前时间: " + df.format(now));
            long fetchDataTime = nowTime - 1000 * 60 * 5;  // 计算5分钟之前的时间戳
            for (PartitionInfo partitionInfo : partitionInfos) {
                topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
                timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), fetchDataTime);
            }

            consumer.assign(topicPartitions);

            // 获取每个partition一个小时之前的偏移量
            Map<TopicPartition, OffsetAndTimestamp> map = consumer.offsetsForTimes(timestampsToSearch);

            OffsetAndTimestamp offsetTimestamp = null;
            System.out.println("====== 开始设置各分区初始偏移量...  map.size = " + map.size());
            for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : map.entrySet()) {
                // 如果设置的查询偏移量的时间点大于最大的索引记录时间，那么value就为空
                offsetTimestamp = entry.getValue();
                if (offsetTimestamp != null) {
                    int partition = entry.getKey().partition();
                    long timestamp = offsetTimestamp.timestamp();
                    long offset = offsetTimestamp.offset();
                    System.out.println("partition = " + partition + ", time = " + df.format(new Date(timestamp)) + ", offset = " + offset);
                    // 设置读取消息的偏移量
                    // consumer.seek(entry.getKey(), offset);
                } else {
                    System.out.println(" entry.getKey() = " + entry.getKey() + ",entry.getValue() = " + entry.getValue());
                }
            }
            System.out.println("====== 设置各分区初始偏移量结束...");

//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(1000);
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.println("partition = " + record.partition() + ", offset = " + record.offset());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}

