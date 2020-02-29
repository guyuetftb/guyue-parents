package com.gy.java.thread;

/**
 * @ClassName ThreadInterruptMainTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-13 16:00
 */
public class ThreadInterruptMainTest {

	public static void main(String[] args) throws InterruptedException {
		ThreadInterrupt threadInterrupt = new ThreadInterrupt("-- 中断子线程 --");
		System.out.println(Thread.currentThread().getName() + ", 子线程 状态 = " + threadInterrupt.getState() + ", [新建],  time=" + System.currentTimeMillis());
		threadInterrupt.start();

		System.out.println(Thread.currentThread().getName() + ", 子线程 状态 = " + threadInterrupt.getState() + ", [可运行],  time=" + System.currentTimeMillis());
		Thread.sleep(2000);

		threadInterrupt.interrupt();
		System.out.println(Thread.currentThread().getName() + ", 子线程 状态 = " + threadInterrupt.getState() + ", [标记中断] ,  time=" + System.currentTimeMillis());

		Thread.sleep(2000);
		System.out.println(Thread.currentThread().getName() + ", 子线程 状态 = " + threadInterrupt.getState() + ", [死亡],  time=" + System.currentTimeMillis());
	}

}

class ThreadInterrupt extends Thread {

	public ThreadInterrupt(String name) {
		super(name);
	}

	public void run() {
		int loopTimes = 0;
		while (!isInterrupted()) {
			try {
				loopTimes++;
				Thread.sleep(500);
				System.out.println(Thread.currentThread().getName() + ", 运行 " + loopTimes + "次, 状态 = " + this.getState() + ", [运行中] time=" + System.currentTimeMillis());
			} catch (InterruptedException e) {
				// e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + ", 运行 " + loopTimes + "次, 状态 = " + this.getState() + ", [异常] time=" + System.currentTimeMillis());
				break;
			}
		}
	}
}
