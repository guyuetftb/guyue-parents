package com;

import java.util.Date;

/**
 * Created by lipeng on 6/14/17.
 */
public class ArrayMaxAdd {
    private static int n = 10;
    // private static int[] toSearch = new int[n];

 private static int[] toSearch = { 31, -41, 59, 26, -53, 58, 97, -93, -23,
 84 };

    public static void main(String[] args) {
        // initArray();
        System.out.println(slowMethod());
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(fastMethod());
    }

    private static void initArray() {
        for (int i = 0; i < n; i++) {
            int rdInt = (int) Math.random() * 10;
            if (rdInt <= 5) {
                toSearch[i] = (int) (Math.random() * 100);
            } else {
                toSearch[i] = (int) (-Math.random() * 100);
            }
        }
    }

    private static int slowMethod() {
        long beginTime = (new Date()).getTime();
        int maxSofar = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += toSearch[j];
                maxSofar = Math.max(maxSofar, sum);
            }
        }
        long endTime = (new Date()).getTime();
        long useTime = (endTime - beginTime);
        System.out.println("普通算法.运行所用时间:" + useTime + "毫秒!");
        return maxSofar;
    }

    private static int fastMethod() {
        long beginTime = (new Date()).getTime();
        int maxSofar = 0;
        int maxEnding = 0;
        for (int i = 0; i < n; i++) {
            maxEnding = Math.max(maxEnding + toSearch[i], 0);
            maxSofar = Math.max(maxSofar, maxEnding);
        }
        long endTime = (new Date()).getTime();
        long useTime = (endTime - beginTime);
        System.out.println("扫描法法.运行所用时间:" + useTime + "毫秒!");
        return maxSofar;
    }
}
