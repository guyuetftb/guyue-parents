package com.gy.kafkasparkstreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.slf4j.LoggerFactory

/**
  * Created by fermat.zheng on 2017/10/16.
  */
object KafkaConsumer {
  def main(args: Array[String]) {
    val log = LoggerFactory.getLogger("ConsumeLog");
    val conf = new SparkConf();
    val ssc = new StreamingContext(conf, Seconds(2));

    val topics = Array("userevents");
    val topic = "testlog";
    val param = Map(
      "group.id" -> "sp-kfk-consuming",
      //			"zookeeper.connect" -> "localhost:2181",
      //			"zookeeper.connection.timeout.ms" -> "1000",
      "bootstrap.servers" -> "192.168.137.100:9092",
      "key.deserializer" -> classOf[StringDeserializer].toString,
      "value.deserializer" -> classOf[StringDeserializer].toString,
      "auto.offset.reset" -> "earliest"
    );

    val msgTun = KafkaUtils.createDirectStream(ssc, param, Set(topic))
    // LocationStrategies.PreferConsistent,
    // ConsumerStrategies.Subscribe[String, String](
    //  Iterator(topic).toIterable, param)
    // );
    //		val msgTun = KafkaUtils.createDirectStream(ssc,
    //			LocationStrategies.PreferConsistent,
    //			ConsumerStrategies.Subscribe[String, String](
    //				topics.distinct, param)
    //		);
    log.debug("msgTunID: " + msgTun.id);

    val something = msgTun.map(rec => {
      rec._1.toString + "--------------->" + rec._2.toString;
    }).saveAsTextFiles("/fer/rec");

    ssc.start();
    ssc.awaitTerminationOrTimeout(10000);
  }
}
