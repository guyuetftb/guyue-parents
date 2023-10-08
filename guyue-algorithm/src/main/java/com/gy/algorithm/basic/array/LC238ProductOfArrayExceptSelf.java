package com.gy.algorithm.basic.array;

import java.util.Arrays;


/**
 * https://leetcode.cn/problems/product-of-array-except-self/description/?envType=study-plan-v2&envId=top-100-liked
 * <p>
 * <p>
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 思路:
 * 1. 由于输出数组不算在空间复杂度内，那么我们可以将 LeftAllElement 或 RightAllElement 数组用输出数组来计算。
 * 先把输出数组当作 LeftAllElement 数组来计算，然后再动态构造 RightAllElement 数组得到结果。
 * 让我们来看看基于这个思想的算法。
 * <p>
 * <p>
 * 算法:
 * <p>
 * <p>
 * 1. 初始化 answer 数组，对于给定索引 i，answer[i] 代表的是 i 左侧所有数字的乘积。
 * 2. 构造方式与之前相同，只是我们试图节省空间，先把 answer 作为方法一的 L 数组。
 * 3. 这种方法的唯一变化就是我们没有构造 R 数组。而是用一个遍历来跟踪右边元素的乘积。
 * 并更新数组 answer[i]=answer[i]∗Ranswer[i]=answer[i]*Ranswer[i]=answer[i]∗R。
 * 然后 RRR 更新为 R=R∗nums[i]R=R*nums[i]R=R∗nums[i]，其中变量 RRR 表示的就是索引右侧数字的乘积。
 */
public class LC238ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        LC238ProductOfArrayExceptSelf lc238ProductOfArrayExceptSelf = new LC238ProductOfArrayExceptSelf();
        int[] ints = lc238ProductOfArrayExceptSelf.productExceptSelf(nums);
        Arrays.stream(ints).forEach(System.out::println);
    }

    public int[] productExceptSelf(int[] nums) {
        // 初始化结果数组
        int length = nums.length;
        int[] answer = new int[length];

        // answer[i] 表示索引 i 左侧所有元素的乘积
        // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
        answer[0] = 1;
        // 从数组第1个元素元素开始循环, 第0个元素设置为1, 这样就不会得到0
        for (int index = 1; index < length; index++) {
            // 第1个元素的前缀之积= 前一个元素 * answer[前一个元素]
            answer[index] = nums[index - 1] * answer[index - 1];
        }

        // allRightSum 为右侧所有元素的乘积
        // 刚开始右边没有元素，所以 allRightSum = 1
        int allRightSum = 1;
        for (int index = length - 1; index >= 0; index--) {
            // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 allRightSum
            answer[index] = answer[index] * allRightSum;
            // allRightSum 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上
            allRightSum *= nums[index];
        }
        return answer;
    }
}
