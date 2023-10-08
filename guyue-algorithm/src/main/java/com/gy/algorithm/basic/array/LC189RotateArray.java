package com.gy.algorithm.basic.array;

import java.util.Arrays;


/**
 * 1. 首先对整个数组实行翻转，这样子原数组中需要翻转的子数组，就会跑到数组最前面。
 * 2. 这时候，从 kkk 处分隔数组，左右两数组，各自进行翻转即可。
 */
public class LC189RotateArray {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        LC189RotateArray lc189RotateArray = new LC189RotateArray();
        lc189RotateArray.rotate(nums, k);
        Arrays.stream(nums).forEach(System.out::println);
    }

    public void rotate2(int[] nums, int k) {
        int length = nums.length;
        int temp[] = new int[length];
        //把原数组值放到一个临时数组中，
        for (int i = 0; i < length; i++) {
            temp[i] = nums[i];
        }

        //然后在把临时数组的值重新放到原数组，并且往右移动k位
        for (int i = 0; i < length; i++) {
            // 数组位置 与 k 位置相差 k个位置.
            // k = 3.
            // 原数组0位置元素, 放到 新数组 3位置
            // 原数组1位置元素, 放到 新数组 4位置
            // 原数组2位置元素, 放到 新数组 5位置
            // 原数组3位置元素, 放到 新数组 0位置
            // 原数组4位置元素, 放到 新数组 1位置
            // 原数组5位置元素, 放到 新数组 2位置
            nums[(i + k) % length] = temp[i];
        }
    }

    public void rotate(int[] nums, int k) {
        k %= nums.length;
        // 1. 翻转整个数组
        reverse(nums, 0, nums.length - 1);

        // 2. 翻转从0, k-1
        reverse(nums, 0, k - 1);

        // 3. 翻转从 k-1, 到 length-1个数组
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            // 保存start位置 至temp
            int temp = nums[start];
            // 将 end位置元素 赋值给 start位置
            nums[start] = nums[end];
            // end位置元素 = temp元素
            nums[end] = temp;
            // start右移
            start += 1;
            // end左移
            end -= 1;
        }
    }
}
