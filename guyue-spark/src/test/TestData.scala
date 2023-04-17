import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 1/4/18.
  */
object TestData {
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf())
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val rdd = sc.parallelize(list).aggregate((0, 0))(
      (acc, number) => (acc._1 + number, acc._2 + 1),
      (first, second) => (first._1 + second._1, first._2 + second._2)
    )

    val listT = List((1, 3), (1, 2), (1, 4), (2, 3), (3, 6), (3, 8))
    val rddT = sc.parallelize(listT).aggregateByKey(0)(
      (acc, number) => (acc + number),
      (one, two) => (one + two)
    )
    rddT.foreach(t => println(t._1 + ", value=" + t._2))


    val listC = List((1, 1), (1, 2), (1, 3), (2, 2), (2, 3), (2, 4), (3, 6), (3, 7), (3, 8))
    val rddC = sc.parallelize(listC).combineByKey(
      (f => f),
      (acc:Int, num) => (acc + num),
      (acc1:Int, acc2:Int) => (acc1 + acc2)
    )
    rddC.foreach(t => println(t._1 + ", acc = " + t._2))

    val a = sc.textFile("aa").map((_, 1))
    a.reduceByKey((f: Int, s: Int) => {
      f + s
    })

  }
}


