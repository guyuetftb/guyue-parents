package com.gy.java.thread;

/**
 * @ClassName StartAndRun
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-11 16:32
 */
public class StartAndRun {

	public static void main(String[] args) {
		ThreadA a = new ThreadA("StartAndRun-ThreadA");
		a.run();
		a.start();
		a.notify();

	}

	static class ThreadA extends Thread {

		public ThreadA(String name) {
			super(name);
		}

		public void run() {

			System.out.println(Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
			try {
				sleep(3000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
