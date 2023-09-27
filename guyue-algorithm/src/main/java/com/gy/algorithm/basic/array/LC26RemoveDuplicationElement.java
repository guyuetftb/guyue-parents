package com.gy.algorithm.basic.array;

public class LC26RemoveDuplicationElement {

    public static void main(String[] args) {
        int[] arr = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        LC26RemoveDuplicationElement lc26RemoveDuplicationElement = new LC26RemoveDuplicationElement();
        int arrLength = lc26RemoveDuplicationElement.removeDuplicates(arr);
        System.out.println(arrLength);
    }

    public int removeDuplicates(int[] nums) {
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            /**
             * 1. fastIndex 初始为0
             * 2. fastIndex 为初始值, 并且 fastIndex 元素与 前一个元素不等，把 fastIndex 值赋给 slowIndex
             */
            if (fastIndex == 0 || (nums[fastIndex - 1] != nums[fastIndex])) {
                /**
                 * 1. 初始 fastIndex = slowIndex = 0, 赋值, 操作不变
                 * 2. fastIndex 的值 赋给 slowIndex
                 * 3. slowIndex++: 向右移动
                 */
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        return slowIndex;
    }
}
