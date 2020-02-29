package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  *
  * 参考 URL : https://www.jianshu.com/p/b1398f9f5a06?from=timeline
  * Created by lipeng on 2018/1/31.
  *
  *
  *
  * field.dataType match {
  * case IntegerType => "INTEGER"
  * case LongType => "BIGINT"
  * case DoubleType => "DOUBLE PRECISION"
  * case FloatType => "REAL"
  * case ShortType => "INTEGER"
  * case ByteType => "BYTE"
  * case BooleanType => "BIT(1)"
  * case StringType => "TEXT"
  * case BinaryType => "BLOB"
  * case TimestampType => "TIMESTAMP"
  * case DateType => "DATE"
  * case t: DecimalType => s"DECIMAL(${t.precision},${t.scale})"
  * case _ => throw new IllegalArgumentException(s"Don't know how to save $field to JDBC")
  * })
  *
  *
  */
object SparkSqlTaoBaoItemNoneScheme {
  val csvFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/tao_bao_data_item.txt"

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "SparkSqlTaoBaoItemNoneScheme"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val taoBaoItemDF = sqlContext.read.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://localhost:3306/hive")
      .option("dbtable", "user_tao_bao_item")
      .option("user", "root")
      .option("password", "123456")
      .option("lowerBound", "1")
      .option("upperBound", "1")
      .option("numPartitions", "4").load()

    println("------------------------- [SqlContext-MySQL]    partition_num")
    println("------- partition number = " + taoBaoItemDF.rdd.partitions.length)

    println("------------------------- [SqlContext-MySQL]    register_table")
    taoBaoItemDF.registerTempTable("tao_bao_data_item")

    println("------------------------- [SqlContext-MySQL]    show all")
    taoBaoItemDF.show()

    println("------------------------- [SqlContext-MySQL]    select(name,price).show(10) ")
    taoBaoItemDF.select("title", "price").show(10)

    println("------------------------- [SqlContext-MySQL]    show schema ")
    taoBaoItemDF.printSchema()

    println("------------------------- [SqlContext]    select * from tao_bao_data_item where price > 100  ")
    sqlContext.sql("select * from tao_bao_data_item where price > 5 ").show()

    sc.stop()
  }
}
