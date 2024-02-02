package com.gy.algorithm.basic.search.binary;

/**
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * <p>
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * <p>
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
public class LC34FindFirstAndLastPositionOfElementInSortedArray {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        LC34FindFirstAndLastPositionOfElementInSortedArray lc34FindFirstAndLastPositionOfElementInSortedArray = new LC34FindFirstAndLastPositionOfElementInSortedArray();
        int[] result = lc34FindFirstAndLastPositionOfElementInSortedArray.searchRange(nums, 8);
        System.out.println(result[0] + ", " + result[1]);
    }

    // https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/solutions/504484/zai-pai-xu-shu-zu-zhong-cha-zhao-yuan-su-de-di-3-4/
    public int[] searchRange(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }


    /**
     * //先找到元素首次出现的位置,然后左,右两边遍历
     * //TODO 不过 找的索引位置不对
     * //讲解利用2次二分查询,正确找到元素位置
     */
    public int[] searchRange_ME(int[] nums, int target) {
        int[] result = new int[2];
        int left = 1;
        int right = nums.length;
        int mid = -1;
        boolean found = false;
        while (left < right) {
            mid = (left + right) / 2;
            if (nums[mid] < target) {
                right = mid - 1;
            } else if (nums[mid] > target) {
                left = mid + 1;
            } else {
                found = true;
                break;
            }
        }
        //先找到元素首次出现的位置,然后左,右两边遍历
        //TODO 不过 找的索引位置不对
        //讲解利用2次二分查询,正确找到元素位置
        if (!found) {
            return result;
        }
        int l = --mid;
        int r = ++mid;
        while (nums[l] == target && l >= 0) {
            l--;
        }
        while (nums[r] == target && r < nums.length) {
            r++;
        }
        result[0] = l;
        result[1] = r;
        return result;
    }
}
