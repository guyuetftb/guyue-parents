package com.gy.algorithm.basic.testing;

public class QuickSort20230303 {
    public static void main(String[] args) {
        System.out.println("QuickSort -------------------------");
        int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
        // 注意: high 索引 位置不能大于 数组长度.
        quickSort(unsorted, 0, unsorted.length - 1);

        display(unsorted);
    }

    public static void quickSort(int[] unsorted, int low, int high){
        // low >= high 元素排序完成.
        if(low >= high){
            return;
        }

        // 寻找「分区点」。每个分区首次都需要调用.
        int mid = partition(unsorted, low, high);

        // 左半分区排序
        quickSort(unsorted,low, mid);

        // 右半分区排序
        quickSort(unsorted,mid + 1,high);
    }

    public static int partition(int[] unsorted, int low, int high){
        // 用每个分区的首个元素做为 「标示元素」
        // 同时起到缓存的作用
        int temp = unsorted[low];

        while(low < high){

            // 下标不能越界, 从高位逐个判断元素是否>=temp「标示元素」
            while(low < high && unsorted[high] >= temp){
                high--;
            }
            // 交换位置: 高位元素 赋值到 低位元素
            unsorted[low] =  unsorted[high];

            // 下村不能越界, 从低位逐个判断元素是否 < temp「标示元素」
            while(low < high && unsorted[low] < temp){
                low++;
            }

            //交的位置: 低位元素 赋值到 高位元素;
            unsorted[high] =  unsorted[low];

        }
        unsorted[low] = temp;
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
