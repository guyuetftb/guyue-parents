package com.gy.algorithm.basic.sort;

import com.gy.algorithm.basic.ArrayUtils;

import java.util.Arrays;

public class LC215KthLargestElementInAnArray {

    public static void main(String[] args) {
        int[] arrayWithDefaultValue = ArrayUtils.createArrayWithDefaultValue();
        LC215KthLargestElementInAnArray lc215KthLargestElementInAnArray = new LC215KthLargestElementInAnArray();
        int kthLargest1 = lc215KthLargestElementInAnArray.findKthLargest(arrayWithDefaultValue, 2);
        System.out.println(kthLargest1);
    }

    public int findKthLargest(int[] nums, int k) {
        return findKthLargestInnerSort(nums, k);
    }

    public int findKthLargestQuickSort(int[] nums, int k) {
        return 0;
    }


    public int findKthLargestInnerSort(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
