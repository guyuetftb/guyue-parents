package com.gy.kafkasparkstreaming

import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

/**
  * Created by lipeng on 1/3/18.
  */
object KafkaIpLoginProducer {

  private val HOST_NAME = "localhost"

  private val uid = Array("123-abc", "456-opq", "789-xyz")

  private val random = new Random()

  private var pointer = -1

  private val SLEEP_SECONDS = 30000

  private val objectMapper = new ObjectMapper()

  def getUserId(): String = {
    pointer += 1
    if (pointer >= uid.length) {
      pointer = 0
      uid(pointer)
    } else {
      uid(pointer)
    }
  }

  def plat(): String = {
    random.nextInt(10) + "10"
  }

  def ip(): String = {
    random.nextInt(10) + ".12.1.211"
  }

  def country(): String = {
    "中国" + random.nextInt(10)
  }

  def city(): String = {
    "深圳" + random.nextInt(10)
  }

  def location(): ArrayNode = {
    val loc = objectMapper.createArrayNode();
    loc.add(random.nextInt(10))
    loc.add(random.nextInt(10))
    loc
  }

  def main(args: Array[String]): Unit = {
    val topic = "kafka-sparkstreaming-topic"
    val brokers = HOST_NAME + ":9092," + HOST_NAME + ":9093," + HOST_NAME + ":9094"
    val props = new Properties()

    props.put("bootstrap.servers", brokers)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    while (true) {
      val producerRecord = objectMapper.createObjectNode();

      // User One
      producerRecord.put("_plat", "1001")
      producerRecord.put("_uid", getUserId())
      producerRecord.put("_tm", (System.currentTimeMillis / 1000).toString())
      producerRecord.put("ip", ip())
      producerRecord.put("country", country())
      producerRecord.put("city", city())
      producerRecord.put("location", location())
      println("Message sent: " + producerRecord.toString)
      producer.send(new ProducerRecord[String, String](topic, producerRecord.toString))

      // User Two
      producerRecord.put("_plat", "1001")
      producerRecord.put("_uid", getUserId())
      producerRecord.put("_tm", (System.currentTimeMillis / 1000).toString())
      producerRecord.put("ip", ip())
      producerRecord.put("country", country())
      producerRecord.put("city", city())
      producerRecord.put("location", location())
      println("Message sent: " + producerRecord.toString)
      producer.send(new ProducerRecord[String, String](topic, producerRecord.toString))

      // User Three
      producerRecord.put("_plat", "1001")
      producerRecord.put("_uid", getUserId())
      producerRecord.put("_tm", (System.currentTimeMillis / 1000).toString())
      producerRecord.put("ip", ip())
      producerRecord.put("country", country())
      producerRecord.put("city", city())
      producerRecord.put("location", location())
      println("Message sent: " + producerRecord.toString)
      producer.send(new ProducerRecord[String, String](topic, producerRecord.toString))

      Thread.sleep(SLEEP_SECONDS)
    }
  }
}
