package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 * 主线程main中启动了两个线程t1和t2。
 * t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。
 * 在t1运行过程中，虽然它会调用Thread.yield()；
 * 但是，t2是不会获取cpu执行权的。
 * 因为，t1并没有释放“obj所持有的同步锁”！
 */
public class ThreadYieldLockTest {
    private static Object obj = new Object();

    public static void main(String[] args) {
        ThreadA one = new ThreadA("one---");
        ThreadA two = new ThreadA("two---");
        one.start();
        two.start();
    }

    static class ThreadA extends Thread {
        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            // 获取obj对象的同步锁
            synchronized (obj) {
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%s  num=%d\n", this.getName(), i);
                    // i整除4时，调用yield
                    if (i % 4 == 0)
                        Thread.yield();
                }
            }
        }
    }
}
