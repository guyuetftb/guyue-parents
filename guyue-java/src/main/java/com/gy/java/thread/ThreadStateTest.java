package com.gy.java.thread;

/**
 * @ClassName ThreadTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-12 15:04
 */
public class ThreadStateTest extends Thread {

	public ThreadStateTest(String name) {
		this.setName(name);
	}

	public void run() {
		String threadTest = Thread.currentThread().getName();
		for (int i = 0; i < 3; i++) {
			System.out.println(threadTest + "线程" + i + "次执行！, time=" + System.currentTimeMillis());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
