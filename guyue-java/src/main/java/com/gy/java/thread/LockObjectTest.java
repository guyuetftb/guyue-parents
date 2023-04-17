package com.gy.java.thread;

import com.gy.util.DateUtils;
import java.util.Date;
import org.apache.commons.httpclient.util.DateUtil;

/**
 * @ClassName LockObjectTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-12 16:22
 */
public class LockObjectTest {

	public void lockTest() {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println(" 我是 线程 " + currentThreadName + ", time = " + DateUtils.yearMonthDayHourMinuteSecond.format(new Date(System.currentTimeMillis())) + " 准备 LockObjectTest 对象的同步锁. ");
		synchronized (this) {
			System.out.println(" 我是 线程 " + currentThreadName + ", time = " + DateUtils.yearMonthDayHourMinuteSecond.format(new Date(System.currentTimeMillis())) + ", LockObjectTest 成功, 我要睡 3000 毫秒. ");
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(" 我是 线程 " + currentThreadName + ", time = " + DateUtils.yearMonthDayHourMinuteSecond.format(new Date(System.currentTimeMillis())) + ", 释放 LockObjectTest 对象的同步锁. ");
	}
}
