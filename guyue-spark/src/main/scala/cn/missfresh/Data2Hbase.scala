package cn.missfresh

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.text.SimpleDateFormat
import java.util
import java.util.Date

import cn.missfresh.utils._
import org.apache.hadoop.hbase.client.{Put, _}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.{Bytes, MD5Hash}
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory


/**
  * Created by Irving on 2018/5/14.
  */
object Data2Hbase {

  private val logger = LoggerFactory.getLogger(Data2Hbase.getClass)

  /**
    * 获取用户未来访时长、未加车时长、最后一次来访时间、最后一次加车时间
    *
    * @param session
    * @param data
    * @param tablename
    */
  def userVisitAndAddCart2Hbase(session: SparkSession, data: String, tablename: String) = {
    val starttime: String = TimesTools.getCurrentTime()
    println("程序启动时间:" + starttime)
    //获取前2个小时的小时时间
    val Hour: String = TimesTools.getHour2HourAgo()
    val CurrentInfo: DataFrame = session.sql(
      s"""
         			   |select
         			   |user_id,
         			   |cast(( unix_timestamp() - unix_timestamp(max(visit_time), 'yyyy-MM-dd HH:mm:ss'))/3600 as bigint) not_visit_hour,
         			   |cast(( unix_timestamp() - unix_timestamp(max(addcart_time), 'yyyy-MM-dd HH:mm:ss'))/3600 as bigint) not_addcart_hour,
         			   |if(substring(max(visit_time),13)>='${Hour}',max(visit_time),null) as last_visit_time,
         			   |if(substring(max(addcart_time),13)>='${Hour}',max(addcart_time),null) as last_add_cart_time
         			   |from
         			   |(select
         			   |user_id,
         			   |created_at as visit_time,
         			   |case when (platform<>'H5' and eventid regexp '.*add_cart') or (platform='H5' and eventid='add_cart')  or eventid like '%shopping_cart' then created_at end addcart_time
         			   |from db_log_analyse.dw_event_stream_log_tiny
         			   |where created_at like '%-%' and length(user_id)<200
         			   |union all
         			   |select
         			   |user_id,
         			   |created_at as visit_time,
         			   |case when (platform<>'H5' and eventid regexp '.*add_cart') or (platform='H5' and eventid='add_cart')  or eventid like '%shopping_cart' then created_at end addcart_time
         			   |from ods_log.ods_log_eventlog
         			   |where dt>='${data}' and created_at like '%-%' and length(user_id)<200) t1 group by user_id
		      """.stripMargin)

    val conf = HBaseConfiguration.create
    val jobConf: JobConf = new JobConf(conf)
    jobConf.set("hbase.zookeeper.quorum", "10.3.4.59:2181,10.3.4.114:2181,10.3.4.34:2181")
    jobConf.set(TableOutputFormat.OUTPUT_TABLE, tablename)
    jobConf.setOutputFormat(classOf[TableOutputFormat])

    CurrentInfo.rdd.map(x => {
      var put: Put = null
      if (x.get(0) != null && x.get(0) != "") {
        val user_id = x.getAs[String]("user_id")
        val user_id_Bytes = Bytes.toBytes(user_id)
        val rowKey: String = MD5Hash.getMD5AsHex(user_id_Bytes)
        put = new Put(Bytes.toBytes(rowKey))
        put.addColumn(Bytes.toBytes("basic"), Bytes.toBytes("user_id"), user_id_Bytes)
        if (x.get(1) != null && x.get(1) != "") {
          val not_visit_hour = x.get(1).toString
          put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("not_visit_hour"), Bytes.toBytes(not_visit_hour))
        }
        if (x.get(2) != null && x.get(2) != "") {
          val not_addcart_hour = x.get(2).toString
          put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("not_addcart_hour"), Bytes.toBytes(not_addcart_hour))
        }
        if (x.get(3) != null && x.get(3) != "") {
          val last_visit_time = x.get(3).toString
          put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("last_visit_time"), Bytes.toBytes(last_visit_time))
        }
        if (x.get(4) != null && x.get(4) != "") {
          val last_add_cart_time = x.get(4).toString
          put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("last_add_cart_time"), Bytes.toBytes(last_add_cart_time))
        }
      }
      (new ImmutableBytesWritable, put)
    })
      .filter(x => x._2 != null)
      .saveAsHadoopDataset(jobConf)

    val endtime: String = TimesTools.getCurrentTime()
    println("程序结束时间:" + starttime)
  }

  /**
    * 获取用户最后一次支付时间到现在的时间间隔、最后一次支付时间
    */
  def userNotPayHour2Hbase(tablename: String) = {
    val hConf = new HbaseConfig()
    val tabName = tablename
    val quorum = "10.3.4.59,10.3.4.114,10.3.4.34"
    val port = "2181"

    val sql = "select user_id,hour(timediff(now(), max(pay_time))) as not_pay_hour, max(pay_time) as last_pay_time from view_orders where voucher_application_id != 5 and status != 'CANCELED' and total !=0 group by user_id "
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    val url2 = "jdbc:mysql://10.10.161.246:3306/as-order"
    var connection: Connection = null
    var st: Statement = null
    var portraitTab = new HTable(hConf.hConFig(quorum, port, tabName), TableName.valueOf(tabName))
    portraitTab.setWriteBufferSize(50 * 1024 * 1024); //50MB
    portraitTab.setAutoFlush(false, false)
    var list = new util.ArrayList[Put]()
    var nomber = 0
    var n = 0
    try {
      connection = DriverManager.getConnection(url2, "tmp_data_r", "MmQxYTY5OTA1OTRm")
      st = connection.createStatement()
      val rs: ResultSet = st.executeQuery(sql)
      while (rs.next()) {
        val user_id = rs.getString("user_id")
        val userId = Bytes.toBytes(user_id)
        val rowKey = MD5Hash.getMD5AsHex(userId)
        val put: Put = new Put(Bytes.toBytes(rowKey))
        put.addColumn(Bytes.toBytes("basic"), Bytes.toBytes("user_id"), userId)
        put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("not_pay_hour"), Bytes.toBytes(rs.getString("not_pay_hour")))
        put.addColumn(Bytes.toBytes("station"), Bytes.toBytes("last_pay_time"), Bytes.toBytes(rs.getString("last_pay_time")))
        list.add(put)
        nomber = nomber + 1
        if (nomber % 100000 == 0) {
          n = n + 1
          portraitTab.put(list)
          portraitTab.flushCommits()
          println(n, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
          list = new util.ArrayList[Put]()
        }
      }
      portraitTab.put(list)
      portraitTab.flushCommits()
    } catch {
      case ex: Exception => {
        ex.printStackTrace()
      }
    } finally {
      portraitTab.close()
      connection.close()
    }
    println("小时标签程序执行完成")
  }

  /**
    * 获取用户信息标签
    *
    * @param session
    * @param yesterdaydata 未访问的按照时间过滤数据
    */
  def userInfo2Hbase(session: SparkSession, yesterdaydata: String, tablename: String) = {
    val millis = System.currentTimeMillis()
    println("UserTagByUserInfo-userInfo2Hbase程序开始时间:" + millis)
    // 获取SparkContext对象
    val sc: SparkContext = session.sparkContext
    //将映射关系广播（列->列族）
    val clmsMapBT = sc.broadcast(MatchUpTools.cln2Family)
    for (f <- 0 to 9) {
      val userVisitInfo: DataFrame = session.sql(s"""select * from bi_app_mryx_crm.tmp_da_log_visit_profile_info where user_id like '%${f}'""".stripMargin)

      println(s"UserTagByUserInfo-userInfo2Hbase|sql循环取数|循环次数${f}")
      val columns: Array[String] = userVisitInfo.columns

      userVisitInfo.foreachPartition(row => {
        var list = new util.ArrayList[Put]()
        // 初始化Hbase连接(单例)
        val conn = HbaseClient.init()
        // 获取Hbase表的操作工具
        val hTable = conn.getTable(TableName.valueOf(tablename)).asInstanceOf[HTable]
        // 将自动flush关闭
        hTable.setAutoFlushTo(false)
        hTable.setWriteBufferSize(50 * 1024 * 1024)
        // 广播变量接收映射关系
        val clnFamilyMap = clmsMapBT.value
        row.foreach(x => {
          val user_id = x.getAs[String]("user_id")
          val rowkey = MD5Hash.getMD5AsHex(Bytes.toBytes(user_id))
          val put = new Put(Bytes.toBytes(rowkey))
          columns.foreach(cln => {
            if (!MatchUpTools.showCapital(clnFamilyMap.get(cln)).equals("-")) {
              if (x.getAs(cln) != null) {
                put.addColumn(Bytes.toBytes(MatchUpTools.showCapital(clnFamilyMap.get(cln))), Bytes.toBytes(cln), Bytes.toBytes(x.getAs[String](cln)))
              }
            }
          })
          list.add(put)
        })
        hTable.put(list)
        hTable.flushCommits()
        hTable.close()
      })

      // 判断昨天是否为周六,为周六导入T+1数据
      val isweekend: String = JudgeTools.isWeekend(yesterdaydata)
      println("导入T+1数据：" + isweekend)
      if (isweekend.equals("yes")) {
        val userNotVisitInfo = session.sql(
          s"""
             					   |select
             					   |*
             					   |from bi_app_mryx_crm.tmp_da_log_user_profile_info
             					   |where (one_day_visit_num is null  or two_day_visit_num is null
             					   |or three_day_visit_num is null or five_day_visit_num is null
             					   |or one_week_visit_num  is null or two_week_visit_num is null
             					   |or one_month_visit_num is null or four_month_visit_num is null
             					   |or two_month_visit_num is null or three_month_visit_num is null
             					   |or five_month_visit_num is null or six_month_visit_num is null)
             					   |and dt='${yesterdaydata}'
             					   |and user_id like '%${f}'
                    """.stripMargin)

        println("T+1数据循环次数" + f)
        // 向hbase写入数据
        val columns1 = userNotVisitInfo.columns
        userNotVisitInfo.foreachPartition(row => {
          var list = new util.ArrayList[Put]()
          // 初始化Hbase连接(单例)
          val conn = HbaseClient.init()
          // 获取Hbase表的操作工具
          val hTable = conn.getTable(TableName.valueOf(tablename)).asInstanceOf[HTable]
          // 将自动flush关闭
          hTable.setAutoFlushTo(false)
          hTable.setWriteBufferSize(50 * 1024 * 1024)
          // 广播变量接收映射关系
          val clnFamilyMap = clmsMapBT.value
          row.foreach(x => {
            val user_id = x.getAs[String]("user_id")
            val rowkey = MD5Hash.getMD5AsHex(Bytes.toBytes(user_id))
            val put = new Put(Bytes.toBytes(rowkey))
            columns1.foreach(cln => {
              if (!MatchUpTools.showCapital(clnFamilyMap.get(cln)).equals("-")) {
                if (x.getAs(cln) != null) {
                  put.addColumn(Bytes.toBytes(MatchUpTools.showCapital(clnFamilyMap.get(cln))), Bytes.toBytes(cln), Bytes.toBytes(x.getAs[String](cln)))
                }
              }
            })
            list.add(put)
          })
          hTable.put(list)
          hTable.flushCommits()
          hTable.close()
        })
      }
    }
    val millis1 = System.currentTimeMillis()
    println("UserTagByUserInfo-userInfo2Hbase程序结束时间：" + millis1)
  }
}
