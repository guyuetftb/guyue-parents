package com.gy.algorithm.basic.array;

/**
 * https://leetcode.cn/problems/jump-game-ii/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class LC45JumpGameII {
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        LC45JumpGameII lc55JumpGame = new LC45JumpGameII();
        int b = lc55JumpGame.jump(nums);
        System.out.println(b);
    }

    /**
     * 解题思路：
     * <p>
     * {2,3,1,1,4,2,1}
     * 从数组的第 0 个位置开始跳，跳的距离小于等于数组上对应的数。
     * 求出跳到最后个位置需要的最短步数。比如上图中的第 0 个位置是 2，那么可以跳 1 个距离，或者 2 个距离，
     * 我们选择跳 1 个距离，就跳到了第 1 个位置，也就是 3 上。
     * 然后我们可以跳 1，2，3 个距离，我们选择跳 3 个距离，就直接到最后了。所以总共需要 2 步。
     * <p>
     * 解法一 ：顺藤摸瓜
     * LeetCode 讨论里，大部分都是这个思路，贪婪算法，我们每次在可跳范围内选择可以使得跳的更远的位置。
     * 如下图，开始的位置是 2，可跳的范围是橙色的。然后因为 3 可以跳的更远，所以跳到 3 的位置。
     */
    public int jump(int[] nums) {
        int everyEnd = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int index = 0; index < nums.length - 1; index++) {
            //找能跳的最远的位置:
            // 第一次: maxPosition = 0, nums[0] + 0 = 2, maxPosition = 2
            // 第2次: maxPosition = 2, nums[1] + 1 = 2, maxPosition = 4
            maxPosition = Math.max(maxPosition, nums[index] + index);
            //遇到边界，就更新边界，并且步数加一
            if (index == everyEnd) {
                everyEnd = maxPosition;
                steps++;
            }
        }
        return steps;
    }
}
