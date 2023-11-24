package com.gy.algorithm.basic.array;

/**
 * https://leetcode.cn/problems/first-missing-positive/description/?envType=study-plan-v2&envId=top-100-liked
 *
 * 由于要找缺失的最小正数，从1开始，返回值最大的情况就是数组长度+1（exp：数组为1、2、3、4，则返回值为5）
 * 为了进一步降低内存的消耗，直接使用byte数组，其他就是很简单的逻辑，相信大家看一下就懂了。
 */
public class LC41FirstMissingPositive {

    public static void main(String[] args) {
        int[] nums = {7, 8, 9, 11, 12};
        LC41FirstMissingPositive lc41FirstMissingPositive = new LC41FirstMissingPositive();
        int i = lc41FirstMissingPositive.firstMissingPositive(nums);
        System.out.println(i);
    }

    public int firstMissingPositive(int[] nums) {
        int len = nums.length + 1;
        byte[] arr = new byte[len];
        for (int num : nums) {
            // 如果存在某个正数 且 数据 小于 数组长度 将该位置设置为1
            if (num > 0 && num < len) {
                arr[num - 1] = 1;
            }
        }
        // 迭代数组, 遇到第1个是0的位置, 就是需要找的最小 正整数.
        // 输出就好了
        for (int i = 0; i < len; i++) {
            if (arr[i] == 0) {
                return i + 1;
            }
        }
        return len;
    }
}
