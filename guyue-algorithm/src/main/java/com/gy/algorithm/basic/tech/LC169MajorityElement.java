package com.gy.algorithm.basic.tech;

import java.util.Arrays;

public class LC169MajorityElement {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 1, 1, 1, 2, 2};
        LC169MajorityElement lc169MajorityElement = new LC169MajorityElement();
        int i = lc169MajorityElement.majorityElement(nums);
        System.out.println(i);
    }

    /**
     * 思路:
     * 如果将数组 nums 中的所有元素按照单调递增或单调递减的顺序排序，
     * 那么下标为 n/2 的元素（下标从 0 开始）一定是众数。
     * <p>
     * 算法:
     * 对于这种算法，我们先将 nums 数组排序，然后返回上文所说的下标对应的元素。
     * 下面的图中解释了为什么这种策略是有效的。
     * 在下图中，第一个例子是 nnn 为奇数的情况，第二个例子是 nnn 为偶数的情况。
     */

    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }


}
