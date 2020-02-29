package com.gy.sparksql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  */
object SparkJsonRegisterTableTest {
  val jsonFile = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/people.json"

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "SparkParquetTest"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val taoBaoItemDF = sqlContext.read.json(jsonFile)
    taoBaoItemDF.registerTempTable("people")

    println("------------------------- [DataFrame]    show all")
    taoBaoItemDF.show()

    println("------------------------- [DataFrame]   printSchema ")
    taoBaoItemDF.printSchema()

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
