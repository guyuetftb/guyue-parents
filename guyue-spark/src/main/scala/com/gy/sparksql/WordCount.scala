package com.gy.sparksql

import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

class WordCount private(a: Int, b: String) {

}

object WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkSql-WordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    //加载
    val fileName = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/sparksql/readMe.txt"
    val lines = sc.textFile(fileName)

    val rows = lines.flatMap(x => x.split(" ")).map(y => Row(y))

    val structType = StructType(Array(StructField("name", DataTypes.StringType, true)))
    val df = sqlContext.createDataFrame(rows, structType)
    df.registerTempTable("t_word_count")
    sqlContext.udf.register("num_word", (name: String) => 1)
    sqlContext.sql("select name, num_word(name) from t_word_count").groupBy(df.col("name")).count().show()
    sc.stop()
  }
}
