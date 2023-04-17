package com.gy

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

;

/**
  * Created by admin on 2018/4/24.
  */
object test {

  Logger.getLogger("org").setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {


    import org.apache.spark.sql.types._

    val sparkcontext= new SparkContext()
    val sqlContext = new SQLContext(sparkcontext)
    val schema = StructType(List(
      StructField("integer_column", IntegerType, nullable = false),
      StructField("string_column", StringType, nullable = true),
      StructField("date_column", DateType, nullable = true)
    ))

    val array = Array(Array("1",2,true),Array("2",2,false),Array("3",3,true))
    val seq = array.toSeq
    val rdd = sparkcontext.parallelize(array.toSeq).map(Row(_:_*));
    val df = sqlContext.createDataFrame(rdd,schema)
    df.withColumn("integer_column",df.col("integer_column").cast("Int"))

    val sparkConf = new SparkConf().setAppName("Binlogtest").setMaster("local[*]") //04
    val sc = new SparkContext(sparkConf)
    val data = sc.textFile("/Users/lipeng/Downloads/ab_test03")
      .map(x => x)
      .filter(x => x.toString.contains("2018-04-19"))
      .repartition(1)
      .saveAsTextFile("/Users/lipeng/Downloads/ab_test_result")
  }
}
