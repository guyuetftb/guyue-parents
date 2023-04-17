package com.gy.kafka.producer;

import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_ACKS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_BATCH_SIZES;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_BOOTSTRAP_SERVERS;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_KEY_SERIALIZER;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_RETRIES;
import static com.gy.kafka.util.KafkaUtil.PROP_ONLINE_VALUE_SERIALIZER;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @ClassName LocalProducer
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-08-16 14:59
 */
public class LocalProducer {

	public static void main(String[] args) {
		if (null == args || args.length <= 0) {
			System.out.println(" The args is null or empty.");
			System.exit(1);
		}

		if (args.length < 2) {
			System.out.println(" The Usages: ");
			System.out.println(" 			0: [must] topic. ");
			System.out.println(" 			1: [must] local file that will be send. ");
			System.exit(1);
		}

		String topic = args[0];
		String localFile = args[1];

		// properties
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PROP_ONLINE_BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.ACKS_CONFIG, PROP_ONLINE_ACKS);
		props.put(ProducerConfig.RETRIES_CONFIG, PROP_ONLINE_RETRIES);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, PROP_ONLINE_BATCH_SIZES);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, PROP_ONLINE_KEY_SERIALIZER);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PROP_ONLINE_VALUE_SERIALIZER);

		// file
		Producer<String, String> procuder = new KafkaProducer<String, String>(props);
		int rowsSend = 0;
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(new File(localFile)));
			//生产者发送消息
			while ((line = br.readLine()) != null) {
				ProducerRecord<String, String> msg = null;
				if (line.indexOf("\t") > 0) {
					String[] tmpArr = line.trim().split("\t");
					msg = new ProducerRecord<String, String>(topic, tmpArr[0], tmpArr[1]);
				} else {
					msg = new ProducerRecord<String, String>(topic, line.trim());
				}
				rowsSend++;
				procuder.send(msg);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//列出topic的相关信息
		System.out.println(" ---- send message over. rows = " + rowsSend);
		if (null != procuder) {
			procuder.close(100, TimeUnit.MILLISECONDS);
		}
	}
}
