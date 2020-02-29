package com.gy.kafka.offset;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;
import kafka.tools.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName KafkaOffsetObtain
 * @Description TOOD
 * @Author lipeng
 * @Date 2019/7/4 19:13
 */

// https://my.oschina.net/realmatrix/blog/1844640
public class KafkaOffsetObtain {

    public static void main(String[] args) {

        String bootstrapServers = "10.3.32.47:9092,10.3.32.8:9092,10.3.32.4:9092";
        String groupId = "eventlog_off_001";
        String topic = args[0];
        String startTime = args[1];
        String endTime = args[2];
        getOffsetsForTimes(bootstrapServers, topic, groupId, startTime, endTime);
    }

    public static void getOffsetsForTimes(String bootstrapServers,
                                          String topic,
                                          String groupId,
                                          String startTime,
                                          String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        long start = 0;
        long end = 0;
        try {
            start = sdf.parse(startTime).getTime();
            end = sdf.parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        TopicPartition topicPartition0 = new TopicPartition(topic, 0);
        TopicPartition topicPartition1 = new TopicPartition(topic, 1);

        Map<TopicPartition, Long> startMap = new HashMap<>();
        startMap.put(topicPartition0, start);
        startMap.put(topicPartition1, start);

        Map<TopicPartition, OffsetAndTimestamp> startOffsetMap = consumer.offsetsForTimes(startMap);
        Map<TopicPartition, Long> endMap = new HashMap<>();
        endMap.put(topicPartition0, end);
        endMap.put(topicPartition1, end);
        Map<TopicPartition, OffsetAndTimestamp> endOffsetMap = consumer.offsetsForTimes(endMap);


        long partition0StartOffset = -1;
        if (startOffsetMap.get(topicPartition0) != null) {
            partition0StartOffset = startOffsetMap.get(topicPartition0).offset();
        }

        long partition0EndOffset = -1;
        if (endOffsetMap.get(topicPartition0) != null) {
            partition0EndOffset = endOffsetMap.get(topicPartition0).offset();
        } else {
            if (partition0StartOffset > 0) {
                partition0EndOffset = consumer.endOffsets(Arrays.asList(topicPartition0)).get(topicPartition0);
            }
        }

        long partition1StartOffset = -1;
        if (startOffsetMap.get(topicPartition1) != null) {
            partition1StartOffset = startOffsetMap.get(topicPartition1).offset();
        }

        long partition1EndOffset = -1;
        if (endOffsetMap.get(topicPartition1) != null) {
            partition1EndOffset = endOffsetMap.get(topicPartition1).offset();
        } else {
            if (partition1StartOffset > 0) {
                partition1EndOffset = consumer.endOffsets(Arrays.asList(topicPartition1)).get(topicPartition1);
            }
        }
        System.out.println("-------------------------- partition0StartOffset = " + partition0StartOffset);
        System.out.println("-------------------------- partition0EndOffset = " + partition0EndOffset);
        System.out.println("-------------------------- partition1StartOffset = " + partition1StartOffset);
        System.out.println("-------------------------- partition1EndOffset = " + partition1EndOffset);
        long total = (partition0EndOffset - partition0StartOffset) + (partition1EndOffset - partition1StartOffset);
        System.out.println(topic + " offsets:" + total);


    }
}
