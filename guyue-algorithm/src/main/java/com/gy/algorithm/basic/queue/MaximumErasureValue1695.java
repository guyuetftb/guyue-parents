package com.gy.algorithm.basic.queue;

import java.util.HashSet;

public class MaximumErasureValue1695 {
    public static void main(String[] args) {

    }

    public int maximumUniqueSubarray(int[] nums) {

        //1. 定义变量
        int sums = 0;

        int largest = 0;

        // Hash表维护, 滑动窗口中的元素
        HashSet<Integer> hash = new HashSet<>();

        // 滑动窗口的开始索引
        int start = 0;
        for (int end = 0; end < nums.length; end++) {

            /**
             * hash中已经包夜了该元素
             */
            while (hash.contains(nums[end])) {
                // 移除元素
                hash.remove(nums[start]);

                // sum 减去该元素的值, 从左向右, 不断的删除元素值, 即使不是该元素, 也要删除, 因为 hash表中存在
                // 说明 重复元素 依然没有被删除
                sums -= nums[start];

                // 窗口右移
                start++;
            }

            /**
             * 此时, hash中不饼 nums[index] 元素
             */
            hash.add(nums[end]);

            sums += nums[end];

            largest = Math.max(largest, sums);
        }
        return largest;
    }
}
