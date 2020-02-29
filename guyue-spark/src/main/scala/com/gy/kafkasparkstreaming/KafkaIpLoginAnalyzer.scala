package com.gy.kafkasparkstreaming

import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.databind.ObjectMapper
import kafka.serializer.StringDecoder
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.JedisPool

/**
  * Created by lipeng on 1/3/18.
  * spark version 1.6.0
  */
object KafkaIpLoginAnalyzer {

  private val HOST_NAME = "localhost"

  def main(args: Array[String]): Unit = {
    val sdf = new SimpleDateFormat("yyyyMMdd")
    val masterURL = "local[2]"
    val appName = "KafkaIpLoginAnalyze"
    val sparkConf = new SparkConf().setMaster(masterURL).setAppName(appName)
    val streamingContext = new StreamingContext(sparkConf, Seconds(5))

    val topics = Set("kafka-sparkstreaming-topic")
    val brokers = HOST_NAME + ":9092," + HOST_NAME + ":9093," + HOST_NAME + ":9094"

    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers,
      "key.deserializer" -> classOf[StringDeserializer].toString,
      "value.deserializer" -> classOf[StringDeserializer].toString,
      "auto.offset.reset" -> "earliest"

    )

    val objectMapper = new ObjectMapper()
    val ipLoginHashKey = sdf.format(new Date())
    println("------------------------- ipLoginHashKey = " + ipLoginHashKey)

    // create a direct stream
    val kafkaStream = KafkaUtils.createDirectStream[String,
      String,
      StringDecoder,
      StringDecoder](streamingContext, kafkaParams, topics)

    val events = kafkaStream.map(message => {
      objectMapper.readTree(message._2.toString)
    })

    events.foreachRDD(rdd => {
      // get sql context
      val sqlContext = SQLContext.getOrCreate(rdd.sparkContext)

      // 导入sqlcontext中的隐式转换,将case class转换为 record(自定义对象)
      import sqlContext.implicits._

      // create
      val wordDataFrame = rdd.map(f => {
        Record(f.get("_plat").asText(), f.get("_uid").asText(), f.get("_tm").asText(), f.get("country").asText(), f.get("location").asText())
      }).toDF() // 将 RDD 转成 dataFrame

      // register a template table
      wordDataFrame.registerTempTable("events")
      val wordCountsDataFrame = sqlContext.sql("select location,count(distinct plat,uid) as value from events where from_unixtime(tm,'yyyyMMdd') = '"
        + sdf.format(new Date())
        + "' group by location")


      // a iterator contain Rows
      val resArray = wordCountsDataFrame.collect()
      println("--------------resArray.length = " + resArray.length)
      val resIter = resArray.iterator

      // Redis Client
      object InternalRedisClient extends Serializable {

        @transient private var pool: JedisPool = null

        def makePool(redisHost: String, redisPort: Int, redisTimeout: Int,
                     maxTotal: Int, maxIdle: Int, minIdle: Int): Unit = {
          makePool(redisHost, redisPort, redisTimeout, maxTotal, maxIdle, minIdle, true, false, 10000)
        }

        def makePool(redisHost: String, redisPort: Int, redisTimeout: Int,
                     maxTotal: Int, maxIdle: Int, minIdle: Int, testOnBorrow: Boolean,
                     testOnReturn: Boolean, maxWaitMillis: Long): Unit = {
          if (pool == null) {
            val poolConfig = new GenericObjectPoolConfig()
            poolConfig.setMaxTotal(maxTotal)
            poolConfig.setMaxIdle(maxIdle)
            poolConfig.setMinIdle(minIdle)
            poolConfig.setTestOnBorrow(testOnBorrow)
            poolConfig.setTestOnReturn(testOnReturn)
            poolConfig.setMaxWaitMillis(maxWaitMillis)
            pool = new JedisPool(poolConfig, redisHost, redisPort, redisTimeout)

            val hook = new Thread {
              override def run = pool.destroy()
            }
            sys.addShutdownHook(hook.run)
          }
        }

        def getPool: JedisPool = {
          assert(pool != null)
          pool
        }
      }

      // Redis configurations
      val maxTotal = 10
      val maxIdle = 10
      val minIdle = 1
      val redisHost = HOST_NAME
      val redisPort = 6379
      val redisTimeout = 30000
      InternalRedisClient.makePool(redisHost, redisPort, redisTimeout, maxTotal, maxIdle, minIdle)
      val jedis = InternalRedisClient.getPool.getResource

      while (resIter.hasNext) {
        var item = resIter.next()
        var key = item.getString(0)
        var value = item.getLong(1)
        jedis.hincrBy(ipLoginHashKey, key, value)
      }
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}

case class Record(plat: String, uid: String, tm: String, country: String, location: String)
