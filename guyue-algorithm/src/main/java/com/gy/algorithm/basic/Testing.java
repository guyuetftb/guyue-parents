package com.gy.algorithm.basic;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName Test2
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-09 23:01
 */
public class Testing {

    public static void main(String[] args) {
        System.out.println("QuickSort -------------------------");
        int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};

        quickSort(unsorted, 0, unsorted.length - 1);
        display(unsorted);
    }

    public static void quickSort(int[] unsorted, int low, int high) {
        if (low >= high) {
            return;
        }

        // 确定初始位置
        int mid = partiton(unsorted, low, high);

        // 左边元素 快排
        quickSort(unsorted, low, mid - 1);

        // 右边元素 快排
        quickSort(unsorted, mid + 1, high);
    }

    public static int partiton(int[] unsorted, int low, int high) {
        // 缓存 标示位 元素
        int pivot = unsorted[low];

        // low 向右移动, high 向左移动, low < high
        while (low < high) {
            while (low < high && unsorted[high] > pivot) {
                high--;
            }
            unsorted[low] = unsorted[high];

            while (low < high && unsorted[low] <= pivot) {
                low++;
            }
            unsorted[high] = unsorted[low];
        }

        unsorted[low] = pivot;
        return low;
    }


    public static void display(int[] a) {
        for (int i : a) {
            if (i > 0) {
                System.out.println(i);
            }
        }
    }
}
