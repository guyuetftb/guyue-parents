package com.gy.java.thread;

/**
 * @ClassName ThreadTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-12 15:04
 */
public class ThreadLockTest extends Thread {

	private LockObjectTest lockObjectTest;

	public ThreadLockTest(String name, LockObjectTest lockObjectTest){
		this.setName(name);
		this.lockObjectTest = lockObjectTest;
	}

	public void run() {
		String threadTest = Thread.currentThread().getName();
		System.out.println("子线程已经运行, 名称是 -> " + threadTest);
		lockObjectTest.lockTest();
	}
}
