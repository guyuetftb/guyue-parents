package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  *
  * URL : https://www.cnblogs.com/gaopeng527/p/4961464.html
  */
object SparkCSVTaoBaoItemWithImplict {

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
    val appName = "SparkCSVTaoBaoItemWithImplict"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    import com.databricks.spark.csv._
    val taoBaoItemDF = sqlContext.csvFile(csvFile,true,'\t')

    taoBaoItemDF.registerTempTable("tao_bao_data_item")

    println("------------------------- [DataFrame]    show all")
    taoBaoItemDF.show()

    println("------------------------- [DataFrame]    show schema ")
    taoBaoItemDF.printSchema()

    println("------------------------- [sqlContext]    select * from tao_bao_data_item where price > 0.5 ")
    sqlContext.sql("select * from tao_bao_data_item where price > 0.5").show()

    sc.stop()
  }
}