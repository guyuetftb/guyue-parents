package com.gy.java.thread;

/**
 * @ClassName ThreadSleepMainTest
 * @Description TOOD
 * @Author lipeng
 */
public class ThreadSleepMainTest {

	public static void main(String[] args) {
		String currentName = Thread.currentThread().getName();
		System.out.println("主线程已经运行, currentName -> " + currentName);
		ThreadTest threadTest = new ThreadTest(" ThreadTest-1");
		threadTest.start();
		try {
			System.out.println(System.currentTimeMillis());
			// 这里sleep的就是main线程，而非 threadTest 线程
			threadTest.sleep(1000);
			System.out.println(System.currentTimeMillis());
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {
			System.out.println("main" + i);
		}
	}
}

