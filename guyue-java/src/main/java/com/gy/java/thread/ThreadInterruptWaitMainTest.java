package com.gy.java.thread;

/**
 * @ClassName ThreadInterruptMainTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-13 16:00
 */
public class ThreadInterruptWaitMainTest {

	public static void main(String[] args) throws InterruptedException {
		LockWaitObjectTest lockObjectTest = new LockWaitObjectTest();
		String sonThreadName = "-- 中断子线程 --";
		ThreadInterruptWait threadInterrupt = new ThreadInterruptWait(sonThreadName, lockObjectTest);
		System.out.println(Thread.currentThread().getName() + ","+sonThreadName+" 状态 = " + threadInterrupt.getState() + ", [新建], isInterrupt = "+threadInterrupt.isInterrupted()+", time=" + System.currentTimeMillis());
		threadInterrupt.start();

		System.out.println(Thread.currentThread().getName() + ","+sonThreadName+" 状态 = " + threadInterrupt.getState() + ", [可运行], isInterrupt = "+threadInterrupt.isInterrupted()+", time=" + System.currentTimeMillis());
		Thread.sleep(2000);

		threadInterrupt.interrupt();
		System.out.println(Thread.currentThread().getName() + ","+sonThreadName+" 状态 = " + threadInterrupt.getState() + ", [标记中断], isInterrupt = "+threadInterrupt.isInterrupted()+" time=" + System.currentTimeMillis());

		Thread.sleep(2000);
		System.out.println(Thread.currentThread().getName() + ","+sonThreadName+" 状态 = " + threadInterrupt.getState() + ", [死亡], isInterrupt = "+threadInterrupt.isInterrupted()+" time=" + System.currentTimeMillis());
	}

}

class ThreadInterruptWait extends Thread {

	private LockWaitObjectTest lockWaitObjectTest;

	public ThreadInterruptWait(String name, LockWaitObjectTest lockWaitObjectTest) {
		super(name);
		this.lockWaitObjectTest = lockWaitObjectTest;
	}

	public void run() {
		int loopTimes = 0;
		while (!isInterrupted()) {
			try {
				loopTimes++;
				System.out.println(Thread.currentThread().getName() + ", 运行 " + loopTimes + "次, 状态 = " + this.getState() + ", [运行中], isInterrupt = "+isInterrupted()+" time=" + System.currentTimeMillis());
				lockWaitObjectTest.lockTest();
			} catch (InterruptedException e) {
				// e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + ", 运行 " + loopTimes + "次, 状态 = " + this.getState() + ", [异常], isInterrupt = " + isInterrupted()+ ", time=" + System.currentTimeMillis());
				break;
			}
		}
	}
}
