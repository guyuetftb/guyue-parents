package com.gy.kafka.offset;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * @ClassName KafkaOffsetFind
 * @Description TOOD
 * @Author lipeng
 * @Date 2019/7/4 14:46
 */
public class KafkaOffsetFind {

    //超时时间
    int POLL_TIMEOUT = 2000;

    //使用时间查询
    public Map<String, Long> useTimestamp(Long timestamp,
                                          String topic,
                                          Properties kafkaProps) {

        //创建消费者,获得消费者分区
        KafkaConsumer consumer = new KafkaConsumer(kafkaProps);
        List<String> topics = new ArrayList<String>();
        topics.add(topic);

        consumer.subscribe(topics);
        consumer.poll(POLL_TIMEOUT);
        Set<TopicPartition> partitions = consumer.assignment();
        System.out.println("-----------------" + ((null == partitions) ? "null" : partitions.size()));
        System.out.println("\t -------- A - " + System.currentTimeMillis());
        //拼出一个查询map
        HashMap<TopicPartition, Long> findPartitionOffsetMap = new HashMap<TopicPartition, Long>();
        for (TopicPartition partition : partitions) {
            findPartitionOffsetMap.put(partition, timestamp);
        }

        //使用查询map去获得偏移量
        Map<TopicPartition, OffsetAndTimestamp> offsetMap = consumer.offsetsForTimes(findPartitionOffsetMap);
        System.out.println("-----------------" + ((null == offsetMap) ? "null" : offsetMap.size()));
        System.out.println("\t -------- B - " + System.currentTimeMillis());
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : offsetMap.entrySet()) {
            System.out.println("====== key.topic" + entry.getKey().topic() + ", key.partition = " + entry.getKey().partition() + ", value.offset = " + entry.getValue().offset() + ", value.timestamp = " + entry.getValue().timestamp());
        }

        //返回前关闭下消费者
        consumer.close();

        System.out.println("\t -------- C - " + System.currentTimeMillis());
        //返回分区号和对应的偏移量
        HashMap<String, Long> result = new HashMap<String, Long>();
        for (TopicPartition tp : partitions) {
            System.out.println("====== topic" + tp.topic() + ", partition = " + tp.partition() + ", TopicPartition.toString() = " + tp);
            result.put(tp.partition() + "", offsetMap.get(tp).offset());
        }
        return result;
    }
}
