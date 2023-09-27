package com.gy.algorithm.basic.array;

/**
 * https://leetcode.cn/problems/merge-sorted-array/submissions/
 */
public class LC88MergeTwoArrayIntoOneArray {

    public static void main(String[] args) {
        int[] arr1 = new int[]{2, 3, 19, 20, 90, 0, 0, 0, 0, 0};
        int[] arr2 = new int[]{1, 2, 11, 15, 17};

        LC88MergeTwoArrayIntoOneArray lc88MergeTwoArrayIntoOneArray = new LC88MergeTwoArrayIntoOneArray();
        lc88MergeTwoArrayIntoOneArray.merge(arr1, 5, arr2, 5);

        for (int index = 0; index < arr1.length; index++) {
            System.out.println(arr1[index]);
        }
    }

    public void merge(int[] arr1, int m, int[] arr2, int n) {
        int index1 = m - 1;
        int index2 = n - 1;

        /**
         * 1. curEleIndex = 数组的长度, 因为 nums1的 数组空间已经分配了, 所以 curEleIndex = 8
         * 2. curEleIndex 从 arr1的末尾， 不断向前移动, 把 符合条件的 int 值插入到指定位置
         */
        int curEleIndex = arr1.length - 1;
        /**
         * 因为要把 num2 的元素 添加到 num1中去, 所以 控制条件 肯定是 index2 >= 0
         */
        while (index2 >= 0) {
            /**
             * 比较2个组数中的元素.
             * 第2个数组末尾元素，大于第1个数组末尾
             *
             * <br/>
             * 为了防止越界 index1 必须是大于等于 0
             * 注意: 判断条件写反了, 容易引发数组下标越界异常.
             */
            if (index1 >= 0 && arr1[index1] > arr2[index2]) {
                /**
                 * 1. arr1的元素 大于 arr2位置的元素;
                 * 2. arr1的元素 往后放: (第1个数组的元素放到 第1个数组未尾)
                 * 3. arr1 的索引前移
                 */
                //
                arr1[curEleIndex] = arr1[index1];

                index1--;
            } else {
                /**
                 * 1. arr1 的元素 小于 arr2位置的元素;
                 * 2. arr2 的元素 放到 arr1 的后面
                 * 3. arr2 的索引前移
                 */
                arr1[curEleIndex] = arr2[index2];
                index2--;
            }

            // 当前索引递减
            curEleIndex--;
        }
    }
}

/**
 * int one1 = m - 1;
 * int two2 = n - 1;
 * int eleIndex = m + n - 1;
 * while (two2 >= 0) {
 * while(two2 >= 0) 满足条件时, if 条件判断 逻辑有可能在某些条件下不会执行
 * 从而无法结束循环, eleIndex, one1 的值会一直 减下去, 造成 数组下标越界.
 * if (one1 >= 0 && nums2[two2] > nums1[one1]) {
 * nums1[eleIndex] = nums2[two2];
 * two2--;
 * eleIndex--;
 * } else {
 * nums1[eleIndex] = nums1[one1];
 * one1--;
 * eleIndex--;
 * }
 * }
 */
