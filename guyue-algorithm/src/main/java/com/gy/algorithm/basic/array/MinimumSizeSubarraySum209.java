package com.gy.algorithm.basic.array;

/**
 * 1. 2023-05-04
 * --Tag: 吴师兄 算法-前缀和
 */
public class MinimumSizeSubarraySum209 {

    public static void main(String[] args) {

    }

    public int minSubArrayLen(int target, int[] nums) {
        // 窗口左侧下村
        int windowLeft = 0;

        // 子数组累加和
        int sum = 0;

        // 结果集的最大值
        int result = Integer.MAX_VALUE;

        for (int windowRight = 0; windowRight < nums.length; windowRight++) {

            //  不断累积计算 滑动窗口总和
            sum += nums[windowRight];

            // 累加值 已经 大于 target 值
            while (sum >= target) {

                // 在获取到一个满足要求的子数组时，更新 result 的值
                result = Math.min(result, windowRight - windowLeft + 1);

                // 从 snum 中 去掉 滑动窗口左侧 nums[right]的值
                sum -= nums[windowLeft];

                // 窗口向右滑动
                windowLeft++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
