package com.gy.java.thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @ClassName ThreadCreateModeTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-14 11:33
 */
public class ThreadCreateModeTest {

	public static void main(String[] args) {

		// 1. 继承 Thread
		Thread thread1 = new Thread1("thread1");
		thread1.start();

		// 2. 实现 Runnable 接口
		Thread runnable1 = new Thread(new Runnable1("runnable1"));
		runnable1.start();

		// 3. 实现 Callable 接口
		FutureTask<String> task  = new FutureTask<>(new Callable1("callable1"));
		Thread callable1 = new Thread(task);
		callable1.start();
		try{

			String callableResult = task.get();
		}catch(Exception e){
			e.printStackTrace();
		}


		// 4. 继承 TimeTask 类, TimeTask 也是实现了 Runnable
		Timer timer = new Timer();
		TimerTask1 timerTask1 = new TimerTask1("timertask1");
		timer.schedule(timerTask1, 0, Integer.MAX_VALUE);	// 测试, 只让其调度一次.

		// 5. 匿名内部类
		final String anonymityThreadName = "anonymity-thread1";
		// 匿名内部类的实现, 会自动调用父类的构造函数
		new Thread(anonymityThreadName){

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				// 匿名内部类, SimpleName() 值 为空
				// this.getClass().getSimpleName()
				System.out.println(this.getClass().getName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
			}
		}.start();

		// 6. 线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		threadPool.execute(new Runnable1("executor-runnable1"){
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(this.getClass().getName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
			}
		});
	}

}


class Thread1 extends Thread {

	public Thread1(String name) {
		super(name);
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(this.getClass().getSimpleName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
	}
}

class Runnable1 implements Runnable {

	private String name;
	public Runnable1(String name) {
		this.name = name;
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(this.getClass().getSimpleName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
	}
}

class Callable1 implements Callable<String> {

	private String name;

	public Callable1(String name) {
		this.name = name;
	}

	public String call() {
		String threadName = Thread.currentThread().getName();
		System.out.println(this.getClass().getSimpleName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
		return "call-" + threadName;
	}
}

class TimerTask1 extends TimerTask {

	private String name;

	/*
	 * Timer Task
	 */
	public TimerTask1(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(this.getClass().getSimpleName() + ", thread-name=" + threadName + ", time= " + System.currentTimeMillis());
	}
}

