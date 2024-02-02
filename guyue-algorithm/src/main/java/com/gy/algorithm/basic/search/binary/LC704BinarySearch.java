package com.gy.algorithm.basic.search.binary;

import com.gy.algorithm.basic.ArrayUtils;

public class LC704BinarySearch {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        LC704BinarySearch lc704BinarySearch = new LC704BinarySearch();
        int result = lc704BinarySearch.search(nums, 16);
        System.out.println(result);
    }

    public int search(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int m = (i + j) / 2;
            if (nums[m] < target) {
                i = m + 1;
            } else if (nums[m] > target) {
                j = m - 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    public int search_ME(int[] nums, int target) {
        if (null == nums || nums.length <= 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length;
        int searchIndex = (left + right) / 2;
        while (true) {
            if (nums[searchIndex] == target) {
                return searchIndex;
            } else if (nums[searchIndex] < target) {
                left = searchIndex;

            } else if (nums[searchIndex] > target) {
                right = searchIndex;
            }
            if (left > right) {
                return -1;
            }
            searchIndex = (left + right) / 2;
        }
    }
}
