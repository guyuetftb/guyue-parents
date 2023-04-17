package com.gy.java.thread;

/**
 * Created by lipeng on 1/9/18.
 */
public class ThreadJoinTestA {

    /**
     * 图片说明地址：<a target="_blank" href="https://images0.cnblogs.com/blog/497634/201312/18184312-a72a58e2bda54b17bf669f325ecda377.png">图片</a>
     *
     * @param args Unseted
     */

    public static void main(String[] args) {
        try {
            ThreadA father = new ThreadA(" --- father ---"); // 新建“线程t1”
            ThreadA son = new ThreadA(" --- son ---"); // 新建“线程t2”
            son.start();        // 启动“儿子线程”
            father.start();     // 启动“父亲线程”
            son.join();         // 将 “儿子线程” 加入到 “主线程main”中，并且 “主线程main()会等待它的完成”
            System.out.printf("%s finish\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            System.out.printf("%s start\n", this.getName() + ", " + System.currentTimeMillis());

            // 延时操作
            for (int i = 0; i < 1000000; i++)
                ;

            System.out.printf("%s finish\n", this.getName() + ", " + System.currentTimeMillis());
        }
    }
}
