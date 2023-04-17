package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 * (01) 主线程中新建并且启动了3个线程"t1", "t2"和"t3"。
 * (02) 主线程通过sleep(3000)休眠3秒。在主线程休眠3秒的过程中，我们假设"t1", "t2"和"t3"这3个线程都运行了。以"t1"为例，当它运行的时候，
 * 它会执行obj.wait()等待其它线程通过notify()或额nofityAll()来唤醒它；
 * 相同的道理，"t2"和"t3"也会等待其它线程通过nofity()或nofityAll()来唤醒它们。
 * (03) 主线程休眠3秒之后，接着运行。执行 obj.notifyAll() 唤醒obj上的等待线程，即唤醒"t1", "t2"和"t3"这3个线程。
 * 紧接着，主线程的synchronized(obj)运行完毕之后，主线程释放“obj锁”。
 * 这样，"t1", "t2"和"t3"就可以获取“obj锁”而继续运行了！
 * <p>
 * wait-nofity-all
 */
public class ThreadNotifyAllTest {

    private static Object obj = new Object();

    public static void main(String[] args) {

        ThreadA one = new ThreadA("--- one ---");
        ThreadA two = new ThreadA("--- two ---");
        ThreadA three = new ThreadA("--- three ---");
        one.start();
        two.start();
        three.start();

        try {
            System.out.println(Thread.currentThread().getName() + " sleep(3000)");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (obj) {
            // 主线程等待唤醒。
            System.out.println(Thread.currentThread().getName() + " notifyAll()");
            obj.notifyAll();
        }
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " 获取 obj 同步锁, 1 prepare !");
            synchronized (obj) {
                try {
                    System.out.println(Thread.currentThread().getName() + " 获取 obj 同步锁, 2 success !");
                    // 打印输出结果
                    // 这里是 3个ThreadA 线程.
                    System.out.println(Thread.currentThread().getName() + " 3 wait !");

                    // 唤醒当前的wait线程
                    // 3个ThreadA, 分别调用 wait() 方法
                    // 为方便记忆, 可理解为 3个线程在 obj 对象上等待.
                    obj.wait();

                    // 打印输出结果
                    System.out.println(Thread.currentThread().getName() + " 获取 obj 同步锁, 4 continue !, nano-time = " + System.nanoTime() + ", timestamp = " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
