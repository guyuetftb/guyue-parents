package com.gy.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lipeng on 1/9/18.
 */
public class CountDownLatchHorse {


    private final static int GROUP_SIZE = 5;

    public static void main(String[] args) {
        processOneGroup("分组1");
        //processOneGroup("分组2");
    }

    private static void processOneGroup(final String groupName) {
        final CountDownLatch start_count_down = new CountDownLatch(1);
        final CountDownLatch end_count_down = new CountDownLatch(GROUP_SIZE);
        System.out.println("==========================>\n分组：" + groupName + "比赛开始：");
        for (int i = 0; i < GROUP_SIZE; i++) {
            new Thread(String.valueOf(i)) {
                public void run() {
                    System.out.println("我是线程组：【" + groupName + "】,第：" + this.getName() + " 号线程,我已经准备就绪！");
                    try {
                        start_count_down.await();       //等待开始指令发出即：start_count_down.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("我是线程组：【" + groupName + "】,第：" + this.getName() + " 号线程,我已执行完成！");
                    end_count_down.countDown();
                }
            }.start();
        }
        try {
            System.out.println("-------------准备前休息------------------");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("  各就各位，预备！  ");
        start_count_down.countDown();       //开始赛跑
        System.out.println("  跑！！！！！！  ");
        try {
            end_count_down.await();         //等待多个赛跑者逐个结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("分组： " + groupName + "  比赛结束！");
    }
}
