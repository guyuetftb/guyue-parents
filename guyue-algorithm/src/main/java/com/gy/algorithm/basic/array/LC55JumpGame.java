package com.gy.algorithm.basic.array;

public class LC55JumpGame {
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        LC55JumpGame lc55JumpGame = new LC55JumpGame();
        boolean b = lc55JumpGame.canJump(nums);
        System.out.println(b);
    }

    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }

        int arrLength = nums.length;
        int curPoint = 0;
        //遍历数组，尝试扩大最大可达范围
        for (int index = 0; index < arrLength; ++index) {
            // arr = {2, 3, 1, 1, 4};
            // 第一次循环 : curPoint = 0. index = 0, nums[0] = 2
            // 第2次循环: index = 1, curPoint = 1 + nums[1] = 4, 已经超过数组最大下标了
            if (curPoint >= index) {
                curPoint = Math.max(curPoint, index + nums[index]);
            }
        }

        // 如果 curPoint >= 数组下标, 就说明可以达到末尾
        if (curPoint >= arrLength - 1) {
            return true;
        }
        return false;
    }
}
