package com.gy.algorithm.basic.array;

public class LC485MaxConsecutiveOnes {

    public static void main(String[] args) {

        int[] nums = new int[]{1, 1, 0, 1, 1, 1};
        LC485MaxConsecutiveOnes lc485MaxConsecutiveOnes = new LC485MaxConsecutiveOnes();
        int maxConsecutiveOnes = lc485MaxConsecutiveOnes.findMaxConsecutiveOnes(nums);
        System.out.println(maxConsecutiveOnes);
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int result = -1;
        int tempCount = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] == 1) {
                tempCount += 1;
            } else {
                result = Math.max(result, tempCount);
                tempCount = 0;
            }
        }
        result = Math.max(result, tempCount);
        return result;
    }

    /**
     * LeetCode 较优解
     *
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes2(int[] nums) {
        // 最大连续个数
        int max = 0;
        int cur = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 1) {
                cur++;
            } else {
                cur = 0;
            }
            // 更新最大
            if (cur > max) {
                max = cur;
            }
        }
        return max;
    }
}
