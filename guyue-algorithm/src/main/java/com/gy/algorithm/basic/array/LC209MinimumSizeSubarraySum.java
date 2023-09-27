package com.gy.algorithm.basic.array;

/**
 * 1. 2023-05-04
 * --Tag: 吴师兄 算法-前缀和
 * <p>
 * https://leetcode.cn/problems/minimum-size-subarray-sum/
 * 长度最小的子数组
 */
public class LC209MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 1, 2, 4, 3};
        LC209MinimumSizeSubarraySum lc209MinimumSizeSubarraySum = new LC209MinimumSizeSubarraySum();
        int i = lc209MinimumSizeSubarraySum.minSubArrayLen(7, arr);
        System.out.println("i = " + i);
    }

    /**
     * 要求:--------------------------------------------------
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * <p>
     * 找出该数组中满足其总和大于等于 target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，
     * 并返回其长度。如果不存在符合条件的子数组，返回 0 。
     * <p>
     * 方案:--------------------------------------------------
     * 1. 需要用到滑动窗口
     * 2. 不断增大窗口的大小, 不断的累加 sum的值.
     * 3. sum >= target, 保存满足条件的 minWindowSize
     * 4. 减去窗口windowLeft的值, 窗口接着向右滑动
     */
    public int minSubArrayLen(int target, int[] nums) {
        // 窗口左侧下村
        int windowLeft = 0;

        // 子数组累加和
        int sum = 0;

        // 结果集的最大值
        int minWindowSize = Integer.MAX_VALUE;

        /**
         ** 方案:--------------------------------------------------
         *  1. 需要用到滑动窗口
         *  2. 不断增大窗口的大小, 不断的累加 sum的值.
         *  3. sum >= target, 保存满足条件的 minWindowSize
         *  4. 减去窗口windowLeft的值, 窗口接着向右滑动
         *  5. 等下次再遇到 sum >= target后, 比较上一次 minWindowSize 与本次 minWindowSize, 取最小 minWindowSize
         */
        for (int windowRight = 0; windowRight < nums.length; windowRight++) {

            //  不断累积计算 滑动窗口总和
            sum += nums[windowRight];

            // 累加值 已经 大于 target 值
            while (sum >= target) {

                // 在获取到一个满足要求的子数组时，更新 result 的值
                minWindowSize = Math.min(minWindowSize, windowRight - windowLeft + 1);

                // 从 sum 中 去掉 滑动窗口左侧 nums[right] 的值
                sum -= nums[windowLeft];

                // 窗口向右滑动
                windowLeft++;
            }
        }

        return minWindowSize == Integer.MAX_VALUE ? 0 : minWindowSize;
    }
}
