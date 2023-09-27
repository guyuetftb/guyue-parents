package com.gy.algorithm.basic.array;

/**
 * https://leetcode.cn/problems/move-zeroes/
 */
public class LC283ZeroMove {

    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 0, 3, 12};
        LC283ZeroMove lc283ZeroMove = new LC283ZeroMove();
        lc283ZeroMove.moveZeroes(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    /**
     * 1. 解此问题，可以换个角度,从反向思考:
     * 把0移动到最后,并保持数组内其他元素相对位置不变，
     * 含义是把所有元素移动到数组的一头，其他位置=0
     * <p>
     * 2. 思路: 本题使用双指针来解决.
     */
    public void moveZeroes(int[] nums) {
        int curEleIndex = 0;

        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {

            /**
             * 如果 fastIndex 指向的元素=0, 接着向后迭代
             */
            if (nums[fastIndex] == 0) {
                continue;
            }

            /**
             * 1. fastIndex 指向的元素 赋值给 curEleIndex.
             * 2. curEleIndex向后移动
             */
            nums[curEleIndex] = nums[fastIndex];
            curEleIndex++;
        }

        /**
         * fastIndex 迭代完了, 从curEleIndex向后, 把数组的值赋为0
         */
        for (; curEleIndex < nums.length; curEleIndex++) {
            nums[curEleIndex] = 0;
        }
    }
}
