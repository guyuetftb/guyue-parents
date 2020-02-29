package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  *
  * URL : https://www.cnblogs.com/ilinuxer/p/6851652.html
  */
object SparkTextTaoBaoItemWithCaseClassImplict {

  val textFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/tao_bao_data_item.txt"

  def main(args: Array[String]): Unit = {

    val master = "local[2]"
    val appName = "SparkTextTaoBaoItemWithCaseClassImplict"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val initRdd = sc.textFile(textFile).map(line => {
      val segArr = line.split("\t")
      TaoBaoItem(segArr(0).trim, segArr(1).trim.toLong, segArr(2).trim, segArr(3).trim, segArr(4).trim.toDouble, segArr(5).trim.toInt)
    })

    import sqlContext.implicits._
    val taoBaoItemDF = initRdd.toDF

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

case class TaoBaoItem(title: String, cid: Long, locationState: String, locationCity: String, price: Double, num: Int)