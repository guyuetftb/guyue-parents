package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  *
  * 参考 URL: https://www.cnblogs.com/ilinuxer/p/6851652.html
  *
  */
object SparkJsonDataFrame2ParquetTest {
  val jsonInputFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/people.json"
  val parquetOutputFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/people_parquet"

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "SparkJsonDataFrame2ParquetTest"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val taoBaoItemDF = sqlContext.read.json(jsonInputFile)

    println("------------------------- [DataFrame]    show all")
    taoBaoItemDF.show()

    println("------------------------- [DataFrame]    printSchema ")
    taoBaoItemDF.printSchema()

    println("------------------------- [DataFrame]    select('name').show() ")
    taoBaoItemDF.select("name").show()

    println("------------------------- [DataFrame]    select(taoBaoItemDF.col('id'),taoBaoItemDF.col('name')).show() ")
    taoBaoItemDF.select(taoBaoItemDF.col("id"), taoBaoItemDF.col("name")).show()

    println("------------------------- [DataFrame]    select(taoBaoItemDF.col(\"age\").gt(25)).show() ")
    taoBaoItemDF.select(taoBaoItemDF.col("age").gt(25)).show()

    println("------------------------- [DataFrame]    groupBy(taoBaoItemDF.col(\"age\")).count().show() ")
    taoBaoItemDF.groupBy(taoBaoItemDF.col("age")).count().show()

    println("------------------------- [DataFrame]    select().show() ")
    taoBaoItemDF.select(taoBaoItemDF.col("id"), taoBaoItemDF.col("name"), taoBaoItemDF.col("age")).foreach(r => {
      println("id=" + r.getLong(0) + ", name=" + r.getString(1) + ", age=" + r.getLong(2))
    })

    taoBaoItemDF.write.parquet(parquetOutputFile)
    sc.stop()
  }
}
