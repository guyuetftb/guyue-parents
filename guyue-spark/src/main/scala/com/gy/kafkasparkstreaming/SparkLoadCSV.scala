package com.gy.kafkasparkstreaming

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 1/4/18.
  */


case class Employee(id: Int, name: String)

object SparkLoadCSV {
  def main(args: Array[String]): Unit = {

    /**
      * WebSite:
      * http://blog.csdn.net/LW_GHY/article/details/51480358
      *
      */
    val masterURL = "local[2]"
    val fileName = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/kafkasparkstreaming/students_info.csv"
    val sparkConf = new SparkConf().setMaster(masterURL).setAppName("SparkLoadCSV")
    val sparkContext = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sparkContext)
    import com.databricks.spark.csv._
    val students = sqlContext.csvFile(filePath = fileName, useHeader = true, delimiter = '|')

    val empList = List(Employee(1, "lipeng"), Employee(2, "guyue"), Employee(3, "wangniu"))

    var empDF = sqlContext.createDataFrame(empList)
    empDF.printSchema()
    println("-- column renamed --------------------")
    empDF = empDF.withColumnRenamed("id", "empId").withColumnRenamed("name", "empName")
    empDF.printSchema()

    println("-- register table --------------------")
    empDF.registerTempTable("emp_tab")

    println("-- select emp_tab --------------------")
    // 如果重命名了列名,比如 id 改为 empID, 那在后继的查询中,只能使用 empId
    val sortedTabRecords = sqlContext.sql("select * from emp_tab order by empId desc")
    sortedTabRecords.show()

    println("-- Tuple crate DataFrame --------------------")
    /**
      * 它如何工作的
      * createDataFrame函数可以接收一切继承scala.Product类的集合对象:
      * def createDataFrame[A <: Product : TypeTag](rdd: RDD[A]): DataFrame
      * 而case class类就是继承了Product。
      * 我们所熟悉的TupleN类型也是继承了scala.Product类的，所以我们也可以通过TupleN来创建DataFrame：
      */
    val mobiles = sqlContext.createDataFrame(Seq((1, "iphone"), (2, "XiaoMi"), (3, "Smartis")))
    mobiles.printSchema()
    mobiles.show()

    println("-- Tuple source DataFrame --------------------")
    val mobilesRenamed = mobiles.withColumnRenamed("_1", "id").withColumnRenamed("_2", "name")
    mobilesRenamed.printSchema()
    mobilesRenamed.show()


    println("-- Students Sample Show --------------------")
    /**
      * 最简单的就是使用show方法，show方法有四个版本：
      * （1）:第一个需要我们指定采样的行数def show(numRows: Int)；
      * （2）:第二种不需要我们指定任何参数，这种情况下，show函数默认会加载出20行的数据def show()；
      * （3）:第三种需要指定一个boolean值，这个值说明是否需要对超过20个字符的列进行截取def show(truncate: Boolean)；
      * （4）:最后一种需要指定采样的行和是否需要对列进行截断def show(numRows: Int, truncate: Boolean)。实际上，前三个函数都是调用这个函数实现的。
      */

    println("-- Show all --------------------")
    students.show()

    println("-- Sample 5 --------------------")
    students.show(12)

    println("-- substring true --------------------")
    students.show(12, false)

    /**
      * 我们select列的时候，需要保证select的列是有效的，换句话说，
      * 就是必须保证select的列是printSchema打印出来的。
      * 如果列的名称是无效的，将会出现org.apache.spark.sql.AnalysisException异常，
      */
    println("-- Show (id, email)--------------------")
    students.select("id", "email").show(7)

    println("-- Show id > 5 and id < 7 --------------------")
    students.filter(" id > 5 and id < 7").show()

    println("-- Show studentName is null or empty --------------------")
    students.filter("studentName ='' OR studentName = 'NULL'").show()

    println("-- Show studentName of the first letter is M --------------------")
    students.filter("SUBSTR(studentName,0,1) ='M'").show()

    println("-- Sort student by id --------------------")
    students.sort("id").show()

    println("-- Sort student by name asc, id desc --------------------")
    students.sort(students("studentName").desc, students("id").asc).show()

    println("-- select students by renamed --------------------")
    students.select(students("studentName").as("real-name"), students("id").as("stu-no")).show()

    println("-- Students register a table --------------------")
    students.registerTempTable("student_tab")
    val stuTabRecords = sqlContext.sql("select * from student_tab where studentName != '' order by email desc")
    stuTabRecords.show()

    println("-- Students inner join --------------------")
    val students2 = sqlContext.csvFile("/Users/lipeng/workspace_engine/engine_project/engine-corpus/src/main/scala/com/gy/kafkasparkstreaming/students_info2.csv", true, '|')
    val studentsJoin = students.join(students2, students("id") === students2("id"), "inner")
    studentsJoin.show()

    println("-- Students left outer join --------------------")
    val studentsLeft = students.join(students2, students("id") === students2("id"), "left_outer")
    studentsLeft.show()

    println("-- Students right outer join --------------------")
    val studentsRight = students.join(students2, students("id") === students2("id"), "right_outer")
    studentsRight.show()

    println("-- Students save to file --------------------")
    // 注意,保存最后是文件夹名,而不是文件名,文件还是以hdfs文件的part-00000形式来存储的.
    val saveOptions = Map("header" -> "true", "path" -> "/Users/lipeng/workspace_engine/engine_project/engine-corpus/src/main/scala/com/gy/kafkasparkstreaming/id_name")
    val copyStudents = students.select(students("id").as("userId"), students("studentName").as("userName"))
    copyStudents.write.format("com.databricks.spark.csv").mode(SaveMode.Overwrite).options(saveOptions).save()

  }
}
