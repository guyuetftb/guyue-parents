import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.DataFrame

/**
  * Created by lipeng on 1/3/18.
  */
object DataFrameToDB {

  def main(args: Array[String]): Unit = {
    val a = new JavaSparkContext()
    // val df = new DataFrame();
    // 此处使用的是spark2.2.0版本
//    val spark = SparkSession
//      .builder()
//      .master("local")
//      .appName("Mysql example")
//      .getOrCreate()
//
//    val jdbcMysql = spark.read.format("jdbc")
//      .option("url", "jdbc:mysql://192.168.8.161:3306/databasename?useUnicode=true&amp;characterEncoding=UTF-8")
//      .option("dbtable", "tablename")
//      .option("user", "username")
//      .option("password", "password")
//      .load()
//
//    // 根据自己的情况来实现，如果是插入同一个数据库可以使用dfColumns.sql("sql语句")函数直接插入，java、python类似
//    val dfColumns = jdbcMysql.select("column1", "column2")
//
//    // 如果是不同的数据库，可以使用下列方法
//    dfColumns.write
//      .format("jdbc")
//      .option("url", "jdbc:mysql://192.168.8.161:3306/databasename?useUnicode=true&amp;characterEncoding=UTF-8")
//      .option("dbtable", "tablename")
//      .option("user", "username")
//      .option("password", "password")
//      .save()
//
//    spark.close()

  }
}
