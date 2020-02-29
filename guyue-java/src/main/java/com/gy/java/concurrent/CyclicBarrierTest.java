package com.gy.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lipeng on 1/9/18.
 */
public class CyclicBarrierTest {


    public static void main(String[] args) {
        int N = 4;

        /**
         * public CyclicBarrier(int parties, Runnable barrierAction) {
         *
         * }
         * 参数parties指让多少个线程或者任务等待至barrier状态；
         * 参数barrierAction为当这些线程都达到barrier状态时会执行的内容。
         */
        CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 当这些线程都达到某种状态时，就开始调用我，来执行某些操作! ");
            }
        });

        for (int i = 0; i < N; i++)
            new Writer(barrier).start();
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "  正在写入数据...");
            try {
                Thread.sleep(5000);     //以睡眠来模拟写入数据操作
                System.out.println(Thread.currentThread().getName() + " 写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();          //每个线程都调用同一个await方法,等待其他线程写入完毕，程序阻塞

                // 第一个版本比较常用，用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
                // public int await() throws InterruptedException, BrokenBarrierException {
                //  doSomeThing
                // };

                
                // 第二个版本是让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。
                // public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException {
                //  doSomeThing
                // };
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "  线程写入完毕，继续处理其他任务...");
        }
    }
}
