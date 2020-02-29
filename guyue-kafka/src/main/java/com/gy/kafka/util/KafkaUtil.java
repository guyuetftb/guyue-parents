package com.gy.kafka.util;

/**
 * @ClassName KafkaUtil
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-08-16 14:29
 */
public class KafkaUtil {

	public static final String PROP_ONLINE_BOOTSTRAP_SERVERS = "10.3.32.47:9092,10.3.32.8:9092,10.3.32.4:9092";    // zookeeper.connect
	public static final String PROP_ONLINE_ZOOKEEPER_CONNECT = "10.3.32.20:2181";    // zookeeper.connect
	public static final String PROP_ONLINE_REQUEST_TIMEOUT_MS = "5000";    // request.timeout.ms
	public static final String PROP_ONLINE_SESSION_TIMEOUT_MS = "4000";    // session.timeout.ms
	public static final String PROP_ONLINE_FETCH_MAX_WAIT_MS = "3000";    // fetch.max.wait.ms
	public static final String PROP_ONLINE_ENABLE_AUTO_COMMIT = "false";    // enable.auto.commit
	public static final String PROP_ONLINE_GROUP_ID = "default.grp.id";    // group.id
	public static final String PROP_ONLINE_ACKS = "all";    // acks
	public static final Integer PROP_ONLINE_RETRIES = 3;    // retries
	public static final Integer PROP_ONLINE_BATCH_SIZES = 3;    // batch.sizes
	public static final String PROP_ONLINE_KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";    // key.deserializer
	public static final String PROP_ONLINE_VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";    // kvalue.deserializer

	public static final String PROP_ONLINE_KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";    // key.deserializer
	public static final String PROP_ONLINE_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";    // kvalue.deserializer
}
