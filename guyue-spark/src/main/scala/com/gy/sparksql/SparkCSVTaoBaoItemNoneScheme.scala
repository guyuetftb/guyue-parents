package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  */
object SparkCSVTaoBaoItemNoneScheme {
  val csvFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/tao_bao_data_item.txt"

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "SparkCSVTaoBaoItemNoneScheme"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val taoBaoItemDF = sqlContext.read.format("com.databricks.spark.csv")
      .option("header", "true") // 这里如果在csv第一行有属性的话，没有就是"false"
      .option("inferSchema", "true") // 这是自动推断属性列的数据类型。
      .option("delimiter", "\t") // 指定分隔符
      .load(csvFile)

    taoBaoItemDF.registerTempTable("tao_bao_data_item")

    println("------------------------- [DataFrame]    show all")
    taoBaoItemDF.show()

    println("------------------------- [DataFrame]    select(name,price).show(10) ")
    taoBaoItemDF.select("title", "price").show(10)

    println("------------------------- [DataFrame]    show schema ")
    taoBaoItemDF.printSchema()

    println("------------------------- [SqlContext]    select * from tao_bao_data_item where price > 100  ")
    sqlContext.sql("select * from tao_bao_data_item where price > 5 ").show()

    sc.stop()
  }
}
