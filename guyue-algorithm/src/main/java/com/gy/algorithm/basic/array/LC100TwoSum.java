package com.gy.algorithm.basic.array;

import java.util.Arrays;
import java.util.HashMap;

public class LC100TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,4};
        int target = 6;
        LC100TwoSum lc100TwoSum = new LC100TwoSum();
        int[] ints = lc100TwoSum.twoSum(nums, target);
        Arrays.stream(ints).forEach(System.out::println);
    }


    public int[] twoSum(int[] nums, int target) {
        // return twoSum1(nums, target);
        return twoSum2(nums, target);
    }

    /**
     * 1. 保存 key 与 index的映射关系.
     * 2. target-key = (预期结果) 另一个数值.
     * 3. 预期结果在HashMap中, 返回
     *
     * TODO: 这种 算法无法解决, 有相同元素的情况
     */
    private int[] twoSum1(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            hashMap.put(nums[index], index);
        }

        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != (target - nums[index]) && hashMap.containsKey(target - nums[index])) {
                return new int[]{index, hashMap.get(target - nums[index])};
            }
        }
        return new int[2];
    }

    /**
     * 冒泡循环算法, 迭代计算每组值.
     * 1. 外循环索引: 从 0 至 n-1
     * 2. 内循环索引: 从 1 至 n
     * 3. 双重循环判断 2个数值相加是不是等于 target
     */
    private int[] twoSum2(int[] nums, int target) {
        int[] resInt = new int[2];
        for (int outIndex = 0; outIndex < nums.length - 1; outIndex++) {
            for (int inIndex = outIndex + 1; inIndex < nums.length; inIndex++) {
                if (target == (nums[outIndex] + nums[inIndex])) {
                    resInt[0] = outIndex;
                    resInt[1] = inIndex;
                    return resInt;
                }
            }
        }
        return new int[2];
    }


}
