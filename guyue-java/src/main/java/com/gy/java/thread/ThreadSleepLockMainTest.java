package com.gy.java.thread;

/**
 * @ClassName ThreadSleepMainTest
 * @Description TOOD
 * @Author lipeng
 */
public class ThreadSleepLockMainTest {

	public static void main(String[] args) {
		String currentName = Thread.currentThread().getName();
		System.out.println("主线程已经运行, currentName -> " + currentName);
		LockObjectTest objectTest = new LockObjectTest();
		ThreadLockTest threadLockTestA = new ThreadLockTest(" ThreadLockTest-1 ",objectTest);
		ThreadLockTest threadLockTestB = new ThreadLockTest(" ThreadLockTest-2 ",objectTest);
		threadLockTestA.start();
		threadLockTestB.start();
	}
}

