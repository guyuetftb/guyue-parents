package com.gy.algorithm.basic.array;

public class LC26RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        LC26RemoveDuplicatesFromSortedArray lc26RemoveDuplicatesFromSortedArray = new LC26RemoveDuplicatesFromSortedArray();
        int i = lc26RemoveDuplicatesFromSortedArray.removeDuplicates(nums);
        System.out.println(i);
    }

    public int removeDuplicates(int[] nums) {
        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            // 1. fast 初始为0: slow = 0, fast = 0, 执行赋值操作不影响任何东西.
            // 2. fast为初始值, 并且 fast当前元素与前一个元素不等,把fast值赋给slow
            if (fast == 0 || nums[fast - 1] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    public int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int slowPoint = 0;
        int fastPoint = 1;
        while (fastPoint < nums.length) {
            if (nums[slowPoint] != nums[fastPoint]) {
                // fastPoint - slowPoint 大于1, 说明 fastPoint 已经向右走了 超过2步
                if (fastPoint - slowPoint > 1) {
                    // 这时候把 fastPoint的值, 赋给 slowPoint+1
                    nums[slowPoint + 1] = nums[fastPoint];
                }
                slowPoint++;
            }
            // fastPoint 每次循环都要向右走一步
            fastPoint++;
        }
        return slowPoint + 1;
    }
}
