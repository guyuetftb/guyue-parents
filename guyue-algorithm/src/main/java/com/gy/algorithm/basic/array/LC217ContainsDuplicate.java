package com.gy.algorithm.basic.array;

import com.gy.algorithm.basic.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次，返回 true ；如果数组中每个元素互不相同，返回 false 。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3,1]
 * 输出：true
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：false
 */
public class LC217ContainsDuplicate {


    public static void main(String[] args) {
        int[] arrayWithDefaultValue = ArrayUtils.createArrayWithDefaultValue();
        LC217ContainsDuplicate lc217ContainsDuplicate = new LC217ContainsDuplicate();
        System.out.println(lc217ContainsDuplicate.containsDuplicate(arrayWithDefaultValue));
    }

    public boolean containsDuplicate_me(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return false;
        }

        HashSet<Integer> duplicates = new HashSet<Integer>();
        for (int i : nums) {
            if (duplicates.contains(i)) {
                return true;
            }
            duplicates.add(i);
        }
        return false;
    }

    public boolean containsDuplicate(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return false;
        }

        Arrays.sort(nums);

        for(int i = 0; i < nums.length-1;i++){
            if(nums[i] ==  nums[i+1]){
                return true;
            }
        }
        return false;
    }
}
