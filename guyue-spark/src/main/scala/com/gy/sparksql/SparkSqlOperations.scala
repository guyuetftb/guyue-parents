package com.gy.sparksql


/**
  *
  */
object SparkSqlOperations {

  /** ************************************************/
  /*
      对Parquet的性能分析：
      URL: https://www.ibm.com/developerworks/cn/analytics/blog/ba-parquet-for-spark-sql/index.html

      存储节省
      以下 Linux 输出显示了 TEXT 和 PARQUET 在 HDFS 上的大小比较：
      % hadoop fs -du -h -s /user/spark/hadoopds1000g
      897.9 G  /user/spark/hadoopds1000g

      % hadoop fs -du -h -s /user/spark/data/parquet
      231.4 G  /user/spark/data/parquet

      1 TB 数据的存储节省了将近 75%！
    */
  /** ************************************************/

  // Spark-SQL之DataFrame操作大全
  // http://blog.csdn.net/dabokele/article/details/52802150

  // 深入分析Parquet列式存储格式
  // http://www.infoq.com/cn/articles/in-depth-analysis-of-parquet-column-storage-format#
}
