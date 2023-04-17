package com.gy.algorithm.basic.array;

public class LC26RemoveDuplicationElement {
    public int removeDuplicates(int[] nums) {
        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            // fast 初始为0
            // fast为初始值, 并且 fast当前元素与前一个元素不等,把fast值赋给slow
            if (fast == 0 || nums[fast - 1] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
