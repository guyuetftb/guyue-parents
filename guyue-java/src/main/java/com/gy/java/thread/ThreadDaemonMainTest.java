package com.gy.java.thread;

/**
 * @ClassName ThreadDaemonTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-13 15:08
 */
public class ThreadDaemonMainTest {

	public static void main(String[] args) {
		Thread t1 = new ThreadCommon("-- 普通 thread --");
		Thread t2 = new ThreadDaemon("-- 守护 thread --");

		// 1. 设置为守护线程, 必须在线程启动这前设置
		t2.setDaemon(true);

		t2.start();
		t1.start();
	}
}


class ThreadCommon extends Thread {

	public ThreadCommon(String name) {
		super(name);
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + ", " + i + " 次执行, time = " + System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ThreadDaemon extends Thread {

	public ThreadDaemon(String name) {
		super(name);
	}

	public void run() {
		for (long i = 0; i < 9999999L; i++) {
			System.out.println(Thread.currentThread().getName() + ", " + i + " 次执行, time = " + System.currentTimeMillis());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
