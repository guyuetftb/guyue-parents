package com.gy.algorithm.basic.array;

/**
 * https://leetcode.cn/problems/plus-one/description/
 */
public class LC66ArrayPlusOne {


    public static void main(String[] args) {
        int[] arr = new int[]{9, 9, 9};
        LC66ArrayPlusOne lc66ArrayPlusOne = new LC66ArrayPlusOne();
        int[] newArr = lc66ArrayPlusOne.plusOne(arr);
        for (int i = 0; i < newArr.length; i++) {
            System.out.print(newArr[i]);
        }
    }

    public int[] plusOne(int[] arr) {
        /**
         * 1. 数组中的元素表示 从高位，到低位的 一个数.
         * 2. 数据计算 + 1, 涉及从 低位 到 高位 的进位.
         * 3. 因此 从后向前, 从低位到高位不断的迭代每一个数字.
         */
        for (int index = arr.length - 1; index >= 0; index--) {
            /**
             * 位置上的数字小于9, 加1, 不会进位, 加1后退出
             */
            if (arr[index] < 9) {
                /**
                 * 加1有2重含义:
                 * 1. 不涉及进位: 加1
                 * 2. 涉及进位: 如个位 -> 十位, 十位到百位, 加1, 退出
                 */
                arr[index]++;
                return arr;
            }
            /**
             * 1. 位置上的数字 == 9, 加1 会 进位, 当前位置元素变成0
             */
            else {
                /**
                 * 1. 假如个位数字等于9, 加1后, 会引起进位, 那个数上的数字 会变成0.
                 * 2. 假如十位数字依然是9, 得到个位进位后, 十位也要向百位进1, 十位上的数字也会变成0.
                 * 3. 所以不能直接在  这, 写死 对进位的加1操作
                 */
                arr[index] = 0;
            }
        }

        //如果所有位都是进位，则长度+1
        arr= new int[arr.length + 1];
        arr[0] = 1;
        return arr;
    }
}
