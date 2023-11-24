package com.gy.algorithm.basic.threads;

import java.util.concurrent.Semaphore;

public class FooBar {

    private int n;
    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            s1.acquire();
            printFoo.run();
            s2.release();

            // printFoo.run() outputs "foo". Do not change or remove this line.
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            s2.acquire();
            printBar.run();
            s1.release();
        }

        // printBar.run() outputs "bar". Do not change or remove this line.
    }

    public static void main(String[] args) {
    }
}