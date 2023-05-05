package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 2023-05-04 数组最大子序和
 * -- Tag: 前缀和(吴师兄学算法)
 *
 * @ClassName MaximumSubArrayTest53
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-10 17:00
 */
public class MaximumSubArray53 {

    public static void main(String[] args) {
        int[] arr = new int[]{1};
        MaximumSubArray53 a = new MaximumSubArray53();
        int maxValue = a.maxSubArray(arr);
        System.out.println("最大子序列合是: " + maxValue);
    }


    public int maxSubArray(int[] arr) {
        // 数组判断: 为空, 或 长度为0, 返回-1
        if (null == arr || arr.length <= 0) {
            return -1;
        }

        // 初始化结果容器
        List<Integer> result = new ArrayList<Integer>(arr.length);

        // TODO 初始化状态集合, 设置默认值.
        for (int index = 0; index < arr.length; index++) {
            result.add(index, Integer.MIN_VALUE);
        }

        // 第1个元素 的最大子数组和: 是 arr[0] 这个元素
        result.set(0, arr[0]);

        // 初始化最大值
        int maxValue  = Math.max(arr[0], Integer.MIN_VALUE);

        //  计算索引从1开始
        for (int i = 1; i < arr.length; i++) {
            // 子数组和最大值: 取 arr的当前元素 与 (result[index -1] + arr[index]) 的最大值
            int subArrMaxVal = Math.max(arr[i], result.get(i - 1) + arr[i]);

            // 赋值
            result.set(i, subArrMaxVal);

            // 始终记录最大值
            maxValue = Math.max(maxValue, subArrMaxVal);
        }

//        for (int i = 0; i < result.size(); i++) {
//            System.out.printf("数组索引为: %d 的子序列和为: %d \n", i, result.get(i));
//        }

        return maxValue;
    }
}
