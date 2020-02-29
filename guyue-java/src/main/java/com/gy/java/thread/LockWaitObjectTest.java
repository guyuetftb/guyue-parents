package com.gy.java.thread;

import com.gy.util.DateUtils;
import java.util.Date;

/**
 * @ClassName LockObjectTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-12 16:22
 */
public class LockWaitObjectTest {

	public void lockTest() throws InterruptedException{
		String currentThreadName = Thread.currentThread().getName();
		System.out.println( currentThreadName + ", time = " + System.currentTimeMillis() + ", interrupt = " + Thread.currentThread().isInterrupted()+ " 准备 LockWaitObjectTest 对象的同步锁. ");
		synchronized (this) {
			System.out.println( currentThreadName + ", time = " + System.currentTimeMillis() +  ", interrupt = "+ Thread.currentThread().isInterrupted() +", LockWaitObjectTest 成功, 等待，并释放锁. ");
			wait();
			System.out.println( currentThreadName + ", time = " + System.currentTimeMillis() +  ", interrupt = "+ Thread.currentThread().isInterrupted() +", LockWaitObjectTest 等待, 返回");
		}
		System.out.println( currentThreadName + ", time = " + System.currentTimeMillis() +  ", interrupt = " + Thread.currentThread().isInterrupted() + ", 释放 LockWaitObjectTest 对象的同步锁. ");
	}
}
