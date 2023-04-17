package com.gy.java.thread;

/**
 * @ClassName ThreadSleepMainTest
 * @Description TOOD
 * @Author lipeng
 */
public class ThreadSleepStateMainTest {

	public static void main(String[] args) {
		String currentName = Thread.currentThread().getName();
		System.out.println("主线程已经运行, currentName -> " + currentName);
		ThreadStateTest threadStateTestA = new ThreadStateTest(" ThreadStateTest-1 ");
		ThreadStateTest threadStateTestB = new ThreadStateTest(" ThreadStateTest-2 ");
		threadStateTestA.start();
		threadStateTestB.start();
	}
}

