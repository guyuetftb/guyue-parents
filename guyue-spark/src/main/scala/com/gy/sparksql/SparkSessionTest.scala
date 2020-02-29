package com.gy.sparksql

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/31.
  *
  * 参考 URL: https://www.cnblogs.com/ilinuxer/p/6851652.html
  *
  */
object SparkSessionTest {
  val json_1 = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/spark_session.json"
  val json_2 = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/commondata/spark_session2.json"

  def main(args: Array[String]): Unit = {
    val master = "local[*]"
    val appName = "SparkSessionTest"
    val sparkConf = new SparkConf()
    sparkConf.setMaster(master)
    sparkConf.setAppName(appName)


    val session = SparkSession.builder().master(master).appName(appName).getOrCreate()
    // val session = SparkSession.builder().config(sparkConf).getOrCreate()
    var df1 = session.read.json(json_1)
    df1.show()
    df1.printSchema()

    df1.where("ID > 230001160").show()


    import session.implicits._
    val ds = session.read.json(json_1)
    val ds1 = df1.filter($"ID" > 230001160)

    df1.intersect(ds1).show()
    df1.repartition(1,null)
    val hm = new HashPartitioner(7)

    println("------------------------------------")
    df1.except(ds1).show()

    println("------------------------------------ distinct ")
    df1.distinct().show()

    println("------------------------------------ dropDuplicates ")
    df1.dropDuplicates("ID", "LID").show()

    println("------------------------------------ select[id] ")
    df1.drop("ID").show()

    println("------------------------------------ select[id,address] ")
    df1.select("ID", "ADDRESS").show() //选择ID，ADDRESS

    println("------------------------------------ select[id as 账号, address as 地址] ")
    df1.select($"ID" as "账号", $"ADDRESS" as "地址").show() //选择ID，ADDRESS

    println("------------------------------------ selectExpr")
    ds.selectExpr("ID", "ID + 1 as New_ID", "abs(ID - 230001165) as New_abs").show()

    val rowDF = session.sparkContext.parallelize(1 to df1.count().toInt).toDF("ID")
    println("------------------------------------ withColumn")
    // 对DataFrame增加一列，或者 替换现有的列
    df1.withColumn("ID", df1("ID") + 1).show() //列名相同，更新列值
    df1.withColumn("ID_NEW", df1("ID") + 1).show() //列名不同，添加一列
    // df1.withColumn("ID", rowDF("ID")).show() //报错

    println("------------------------------------ withColumnRenamed")
    df1.withColumnRenamed("ID", "NEW_ID").show()

    println("------------------------------------ filter ")
    df1.filter(_.getLong(2) > 230001160).show()

    println("------------------------------------ filter($\"ID\" > 230001160) ")
    df1.filter($"ID" > 230001160).show()

    println("------------------------------------ filter(ID > 230001160) ")
    df1.filter("ID > 230001160").show()

    println("------------------------------------ df2.filter.show() ")
    val df2 = session.read.json(json_2).toDF()
    df2.filter("ID > 230001160").show()

    println("------------------------------------ join ")
    // df1.join(df2,"ID").select().show()
    df1.join(df2, "id").select(df1("id") as "ID", df1("LID") as "LID", df2("name") as "NAME", df1("DEVICENAME") as "DEVICENAME").show()

    println("------------------------------------ join-2 ")
    df1.join(df2, df1("id") === df2("id"), "inner").select(df1("id"), df1("LID"), df2("name"), df1("DEVICENAME")).show()

    println("------------------------------------ crossJoin ")
    df1.crossJoin(df2).show()


    println("------------------------------------ toJSON.show() ")
    df1.toJSON.show()
    session.stop()


  }
}
