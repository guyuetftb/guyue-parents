package com.gy.java.lock;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName LockBlockingSynchronizedTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-15 16:58
 */
public class LockBlockingSynchronizedTest {

	public static void main(String[] args) {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

		for (int index = 0; index < 1000; index++) {
			Thread threadProductA = new Thread(new LinkBlockThread("pro-a", queue), " PRODUCT-A");
			Thread threadProductB = new Thread(new LinkBlockThread("pro-b", queue), " PRODUCT-B");
			Thread threadConsumerA = new Thread(new LinkBlockThread("consumer", queue), "CONSUMER-A");
			Thread threadConsumerB = new Thread(new LinkBlockThread("consumer", queue), "CONSUMER-B");
			threadConsumerA.start();
			threadConsumerB.start();
			threadProductA.start();
			threadProductB.start();

			try {
				Thread.sleep(5000l);
				System.out.println("---------------------------------------------------------------------------------------------------- 库存=" + queue.size());
				if (queue.isEmpty()) {
					continue;
				} else {
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

	static class LinkBlockThread implements Runnable {

		private LinkedBlockingQueue<String> queue;
		private String flag;
		final int size = 5;

		public LinkBlockThread(String flag, LinkedBlockingQueue<String> queue) {
			this.flag = flag;
			this.queue = queue;
		}

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			System.out.println(name + ", 启动线程, time=" + System.nanoTime());
			if (flag.indexOf("pro") > -1) {
				for (int i = 0; i < size; i++) {
					String product = flag + "-" + i;
					try {
						queue.put(product);
						System.out.println(name + "\t 生产 " + product + "\t 库存=" + queue.size() + "\t time=" + System.nanoTime());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {

				for (int i = 0; i < size; i++) {
					try {
						System.out.println(name + "\t 购买 " + queue.take() + "\t 库存=" + queue.size() + "\t time=" + System.nanoTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

}