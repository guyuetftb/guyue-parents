package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 * <p>
 * 2018-1-9
 * http://blog.csdn.net/bzwm/article/details/3881392
 * 面试前，java多线学习
 * <p>
 * <p>
 * 可以把join理解为插入的意思：
 * 就比如，你父子2个，同样去 窗口买票
 * 最先到 火车站的是 父亲，在快轮到父亲之前，
 * 儿子到了，儿子要买一张，去其他地方的票，那么
 * 这时候，儿子可以调用 自己的 join方法，来向父亲 声名，我要插你的队
 * 那这时，父亲只能等儿子买完票之后，才能再接着买，否则一直等儿子执行。
 */

public class ThreadJoinTest {
    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");

        SonThread son = new SonThread();
        FatherThread father = new FatherThread(son);
        try {
            son.start();
            Thread.sleep(2000);
            father.start();
            father.join();       //在代碼2里，將此處注釋掉
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end!");
    }
}

class SonThread extends Thread {
    public SonThread() {
        super("[SonThread] Thread");
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(threadName + " loop at " + i);
                Thread.sleep(1000);
            }
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}

class FatherThread extends Thread {
    SonThread t1;

    public FatherThread(SonThread t1) {
        super("[FatherThread] Thread");
        this.t1 = t1;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            System.out.println(" son will join.");
            t1.join();
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}


