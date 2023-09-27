package com.gy.algorithm.basic.doublepoints;

public class LC287FindTheDuplicateNumber {

    public static void main(String[] args) {

    }

    public int findDuplicate(int[] nums) {

        // 慢指针, 慢指针走一步
        int slowPoint = 0;
        slowPoint = nums[slowPoint];

        // 快指针, 快指针走二步
        int fastPoint = 0;
        fastPoint = nums[fastPoint];
        fastPoint = nums[fastPoint];

        /**
         * 直到2个指针不相遇
         */
        while (slowPoint != fastPoint) {
            // 慢指针走一步
            slowPoint = nums[slowPoint];

            // 快指针走二步
            fastPoint = nums[fastPoint];
            fastPoint = nums[fastPoint];
        }

        /**
         * 程序到此, 说明2个指针相遇了
         */
        int b = fastPoint;

        int a = 0;
        while (a != b) {
            // 指针走一步
            a = nums[a];
            // 指针走一步
            b = nums[b];
        }

        return b;
    }


}
