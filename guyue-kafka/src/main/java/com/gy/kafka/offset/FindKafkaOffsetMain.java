package com.gy.kafka.offset;

import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_BOOTSTRAP_SERVERS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_ENABLE_AUTO_COMMIT;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_FETCH_MAX_WAIT_MS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_GROUP_ID;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_KEY_DESERIALIZER;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_REQUEST_TIMEOUT_MS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_SESSION_TIMEOUT_MS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_VALUE_DESERIALIZER;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_ZOOKEEPER_CONNECT;

import java.util.Map;
import java.util.Properties;

public class FindKafkaOffsetMain {

	public static void main(String[] args) {

		/** ***************************************************************************************************************
		 * kafka info
		 */
		String topic = "test4";
		Long timestamp = 1565798400000L;

		/** ***************************************************************************************************************
		 * create kafka stream
		 */
		Properties props = new Properties();
		props.put("bootstrap.servers", PROP_ONLINE_BOOTSTRAP_SERVERS);
		props.put("zookeeper.connect", PROP_ONLINE_ZOOKEEPER_CONNECT);
		props.put("request.timeout.ms", PROP_ONLINE_REQUEST_TIMEOUT_MS);
		props.put("session.timeout.ms", PROP_ONLINE_SESSION_TIMEOUT_MS);
		props.put("fetch.max.wait.ms", PROP_ONLINE_FETCH_MAX_WAIT_MS);
		props.put("enable.auto.commit", PROP_ONLINE_ENABLE_AUTO_COMMIT);
		props.put("group.id", PROP_ONLINE_GROUP_ID);
		props.put("key.deserializer", PROP_ONLINE_KEY_DESERIALIZER);
		props.put("value.deserializer", PROP_ONLINE_VALUE_DESERIALIZER);

		/* ***********************************************************************************************************
		 * stream
		 */
		//找到时间戳对应偏移量
		KafkaOffsetFind offsetFinder = new KafkaOffsetFind();
		Map<String, Long> offset = offsetFinder.useTimestamp(timestamp, topic, props);
		for (Map.Entry entry : offset.entrySet()) {
			System.out.println(" partition = " + entry.getKey() + ", offset = " + entry.getValue());
		}
	}
}


