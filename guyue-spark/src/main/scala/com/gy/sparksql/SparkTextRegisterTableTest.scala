package com.gy.sparksql


import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  */
object SparkJsonRegisterTableTest1 {
  val textFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/people.txt"

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "SparkTextRegisterTest"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    //
    val structFieldList = List(StructField("id", LongType), StructField("name", StringType), StructField("age", IntegerType))
    val dataStruct = StructType(structFieldList)

    val sc = new SparkContext(sparkConf)
    val initRDD = sc.textFile(textFile).map(line => {
      val segment = line.split(",")
      Row(segment(0).trim.toLong, segment(1).trim, segment(2).trim.toInt)
    })

    val sqlContext = new SQLContext(sc)

    // 通过SqlContext来创建 DataFrame 并且指定 表类型 和 结构
    val peopleDF = sqlContext.createDataFrame(initRDD, dataStruct)
    peopleDF.registerTempTable("people")

    println("------------------------- [DataFrame]    show all")
    peopleDF.show()

    println("------------------------- [DataFrame]   printSchema ")
    peopleDF.printSchema()

    println("------------------------- [SqlContext]   select id,name from people ")
    sqlContext.sql("select id,name from people").show()

    println("------------------------- [SqlContext]   select age from people ")
    sqlContext.sql("select age from people").show()

    println("------------------------- [SqlContext]   select id,name from people where age >= 25")
    sqlContext.sql("select id,name from people where age >= 25").show()

    println("------------------------- [SqlContext]   select age,count(*) as num from people group by age")
    sqlContext.sql("select age,count(*) as num from people group by age").show()

    println("------------------------- [SqlContext]   select id,name,age as num from people")
    sqlContext.sql("select age,count(*) as num from people group by age").foreach(r => {
      println(" age=" + r.getLong(0) + ", count_number=" + r.getLong(1))
    })

    sc.stop()
  }
}
