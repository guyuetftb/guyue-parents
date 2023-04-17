package com.gy.java.concurrent;


import com.gy.util.DateUtils;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by lipeng on 1/9/18.
 */

public class SemaphoreTest {
    private final static Semaphore MAX_SEMA_PHORE = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final int num = i;
            final Random radom = new Random();
            new Thread() {
                public void run() {
                    boolean acquired = false;
                    try {
                        MAX_SEMA_PHORE.acquire();
                        acquired = true;
                        System.out.println("我是线程：" + num + " 我有了一间 茅坑 的使用权！" + DateUtils.yearMonthDay.format(new Date()));
                        long time = 1000 * Math.max(1, Math.abs(radom.nextInt() % 10));
                        Thread.sleep(time);
                        System.out.println("我是线程：" + num + " 我 接完 屎了！" + DateUtils.yearMonthDay.format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (acquired) {
                            MAX_SEMA_PHORE.release();
                        }
                    }
                }
            }.start();
        }
    }
}