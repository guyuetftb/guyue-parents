package com.gy.java.thread;

/**
 * @ClassName ThreadTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-12 15:04
 */
public class ThreadTest extends Thread {

	public ThreadTest(String name){
		this(name,Thread.NORM_PRIORITY);
	}

	public ThreadTest(String name, int priority){
		super(name);
		this.setPriority(priority);
	}

	public void run() {
		String threadTest = Thread.currentThread().getName();
		System.out.println("子线程已经运行, threadTest -> " + threadTest);
	}
}
