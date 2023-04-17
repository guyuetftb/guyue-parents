package com.gy.java.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

/**
 * @ClassName LockReentrantLockTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-14 16:28
 */
public class LockReentrantLockTest {

	private ReentrantLock lock = new ReentrantLock();

	public void execute() {
		System.out.println(Thread.currentThread().getName() + ", execute1() time=" + System.currentTimeMillis());
		lock.lock();
		try {
			try {
				Thread.sleep(2000l);
				execute2();
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " interrupted");
				Thread.currentThread().interrupt();
			}
		} finally {
			lock.unlock();
		}

		System.err.println(Thread.currentThread().getName() + ", execute1() over. ");
	}

	public void execute2(){
		System.out.println(Thread.currentThread().getName() + ", execute2() time=" + System.currentTimeMillis());
		lock.lock();
		try {
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " interrupted");
				Thread.currentThread().interrupt();
			}
		} finally {
			lock.unlock();
		}
		System.err.println(Thread.currentThread().getName() + ", execute2() over. ");
	}

	public static void main(String[] args) {
		final LockReentrantLockTest reentrantLockTest = new LockReentrantLockTest();

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				reentrantLockTest.execute();
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				reentrantLockTest.execute();
			}
		});

		thread1.start();
		thread2.start();
	}
}



