package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 * <p>
 * <p>
 * 流程图片: join.png
 *
 * 结果说明：
 * “线程t1”在能被4整数的时候，并没有切换到“线程t2”。
 * 这表明，yield()虽然可以让线程由 “运行状态” 进入到 “就绪状态”
 * 但是，它不一定会让其它线程获取CPU执行权(即，其它线程进入到“运行状态”)，
 * 即使这个“其它线程”与当前调用yield()的线程具有相同的优先级。
 */
public class ThreadYieldTest {
    public static void main(String[] args) {
        ThreadA one = new ThreadA("one---");
        ThreadA two = new ThreadA("two---");
        one.start();
        two.start();
    }
}

class ThreadA extends Thread {
    public ThreadA(String name) {
        super(name);
    }

    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s  num=%d\n", this.getName(), i);
            // i整除4时，调用yield
            if (i % 4 == 0 && i != 0)
                System.out.println(this.getName() + ", 我要让步了. ");
                Thread.yield();
        }
    }
}


