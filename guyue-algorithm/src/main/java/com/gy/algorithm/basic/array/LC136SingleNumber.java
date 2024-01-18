package com.gy.algorithm.basic.array;

import java.util.Arrays;

/**
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * <p>
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 * <p>
 * <p>
 * <p>
 * 示例 1 ：
 * <p>
 * 输入：nums = [2,2,1]
 * 输出：1
 * 示例 2 ：
 * <p>
 * 输入：nums = [4,1,2,1,2]
 * 输出：4
 */
public class LC136SingleNumber {

    public static void main(String[] args) {
        int[] nums = new int[]{4, 1, 2, 1, 2};
        LC136SingleNumber lc136SingleNumber = new LC136SingleNumber();
        System.out.println(lc136SingleNumber.singleNumber(nums));
    }

    /**
     *
     * 题目要求时间复杂度 O(N)O(N)O(N) ，空间复杂度 O(1)O(1)O(1) ，因此首先排除 暴力法 和 辅助哈希表法 。
     *
     * 设整型数组 numsnumsnums 中出现一次的数字为 xxx ，出现两次的数字为 a,a,b,b,...a, a, b, b, ...a,a,b,b,... ，即：
     *
     * nums=[a,a,b,b,...,x]nums = [a, a, b, b, ..., x]
     * nums=[a,a,b,b,...,x]
     * 异或运算有个重要的性质，两个相同数字异或为 000 ，即对于任意整数 aaa 有 a⊕a=0a \oplus a = 0a⊕a=0 。因此，若将 numsnumsnums 中所有数字执行异或运算，留下的结果则为 出现一次的数字 xxx
     *
     * 作者：Krahets
     * 链接：https://leetcode.cn/problems/single-number/solutions/2361995/136-zhi-chu-xian-yi-ci-de-shu-zi-wei-yun-iyd0/
     *
     * ---------------------------------------------------------------
     * 用"异或"解题
     * "异或" 运算符： 两个操作数相同为0,不相同为1.
     */
    public int singleNumber(int[] nums) {
        if (nums.length <= 1) {
            return nums[0];
        }
        Arrays.sort(nums);

        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ans = ans ^ nums[i];
        }

        return ans;
    }
}
