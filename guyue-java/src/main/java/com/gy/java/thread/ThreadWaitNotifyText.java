package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 * 对于上面的代码？曾经有个朋友问到过：t1.wait()应该是让“线程t1”等待；但是，为什么却是让“主线程main”等待了呢？
 * <pre>
 * 在解答该问题前，我们先看看jdk文档中关于wait的一段介绍：
 * Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object.
 * In other words, this method behaves exactly as if it simply performs the call wait(0).
 * The current thread must own this object's monitor. The thread releases ownership of this monitor and waits until another thread notifies threads waiting on this object's monitor to wake up either through a call to the notify method or the notifyAll method. The thread then waits until it can re-obtain ownership of the monitor and resumes execution.
 * 中文意思大概是：
 * </pre>
 * <pre>
 * 引起“当前线程”等待，直到另外一个线程调用notify()或notifyAll()唤醒该线程。
 * 换句话说，这个方法和wait(0)的效果一样！(补充，对于wait(long millis)方法，当millis为0时，表示无限等待，直到被notify()或notifyAll()唤醒)。
 * “当前线程”在调用wait()时，必须拥有该对象的同步锁。该线程调用wait()之后，会释放该锁；
 * 然后一直等待直到“其它线程”调用对象的同步锁的notify()或notifyAll()方法。
 * 然后，该线程继续等待直到它重新获取“该对象的同步锁”，然后就可以接着运行。
 * 注意：jdk的解释中，说wait()的作用是让“当前线程”等待，而“当前线程”是指正在cpu上运行的线程！
 * 这也意味着，虽然t1.wait()是通过“线程t1”调用的wait()方法，但是调用t1.wait()的地方是在“主线程main”中。
 * 而主线程必须是“当前线程”，也就是运行状态，才可以执行t1.wait()。所以，此时的“当前线程”是“主线程main”！
 * 因此，t1.wait()是让“主线程”等待，而不是“线程t1”！
 * </pre>
 */
public class ThreadWaitNotifyText {


    /**
     * 带有wait(time)的版本，看这里：http://www.cnblogs.com/skywang12345/p/3479224.html
     *
     * @param args 参数
     */

    public static void main(String[] args) {

        MeiMei meiMei = new MeiMei("meiMei");

        /**
         *
         * 流程图片: wait-yield.png
         *
         * (01) 注意，图中"主线程" 代表“主线程main”。"线程t1" 代表WaitTest中启动的“线程t1”。 而“锁” 代表“t1这个对象的同步锁”。
         * (02) “主线程”通过 new ThreadA("t1") 新建“线程t1”。随后通过synchronized(t1)获取“t1对象的同步锁”。然后调用t1.start()启动“线程t1”。
         * (03) “主线程”执行t1.wait() 释放“t1对象的锁”并且进入“等待(阻塞)状态”。等待t1对象上的线程通过notify() 或 notifyAll()将其唤醒。
         * (04) “线程t1”运行之后，通过synchronized(this)获取“当前对象的锁”；接着调用notify()唤醒“当前对象上的等待线程”，也就是唤醒“主线程”。
         * (05) “线程t1”运行完毕之后，释放“当前对象的锁”。紧接着，“主线程”获取“t1对象的锁”，然后接着运行。
         */
        synchronized (meiMei) {
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName() + " start meiMei ");
                meiMei.start();

                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName() + " wait(). ");
                meiMei.wait();

                System.out.println(Thread.currentThread().getName() + " continue. ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " over. ");
    }
}

class MeiMei extends Thread {

    public MeiMei(String name) {
        super(name);
    }

    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " call notify()");
            // 唤醒当前的wait线程
            notify();
        }
    }
}
