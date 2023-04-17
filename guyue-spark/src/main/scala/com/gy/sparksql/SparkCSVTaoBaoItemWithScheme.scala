package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  */
object SparkCSVTaoBaoItemWithScheme {
  val csvFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/tao_bao_data_item.txt"

  def main(args: Array[String]): Unit = {

    val dataTitle = List(
      StructField("title", StringType), // 宝贝名称
      StructField("cid", LongType), // 宝贝类目
      StructField("location_state", StringType), // 省
      StructField("location_city", StringType), // 城市
      StructField("price", DoubleType), // 宝贝价格
      StructField("num", IntegerType)) // 宝贝数量

    val dataStruct = StructType(dataTitle)

    val master = "local[2]"
    val appName = "SparkCSVTaoBaoItemWithScheme"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val taoBaoItemDF = sqlContext.read.format("com.databricks.spark.csv")
      .option("header", "true") // 这里如果在csv第一行有属性的话，没有就是"false"
      .option("delimiter", "\t") //指定 '文件分隔符'
      .schema(dataStruct) // 指定列具体信息
      .load(csvFile) // 加载文件

    taoBaoItemDF.registerTempTable("tao_bao_data_item")

    println("------------------------- [DataFrame]    show all")
    taoBaoItemDF.show()

    println("------------------------- [DataFrame]    show schema ")
    taoBaoItemDF.printSchema()

    println("------------------------- [DataFrame]    show all")
    sqlContext.sql("select * from tao_bao_data_item where price > 1").show()

    sc.stop()
  }
}