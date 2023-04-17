package com.gy.java.thread.pool;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.SimpleFormatter;

/**
 * Created by lipeng on 12/30/17.
 */
public class JavaThreadPoolSourceCodeAnalyzing {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    static String sf = "#################################33";

    private static int ctlOf(int rs, int wc) { return rs | wc; }
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        System.out.println(" COUNT_BITS = " + COUNT_BITS);
        System.out.println(1 << 3);
        System.out.println(" CAPACITY = " + Integer.toBinaryString(CAPACITY) + ", " + String.format(sf,Integer.toBinaryString(CAPACITY)));
        System.out.println(" ~CAPACITY = " + ~CAPACITY);
        System.out.println(" RUNNING = " + RUNNING);
        System.out.println(" SHUTDOWN = " + SHUTDOWN);
        System.out.println(" STOP = " + STOP);
        System.out.println(" TIDYING = " + TIDYING);
        System.out.println(" TERMINATED = " + TERMINATED);

    }
}
