package com.gy.algorithm.basic.array;

/**
 * 724. 寻找数组的中心下标
 * https://leetcode.cn/problems/find-pivot-index/
 *
 * 给你一个整数数组 nums ，请计算数组的 中心下标 。
 * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
 * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
 * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
 */
public class LC724FindPivotIndex {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 7, 3, 6, 5, 6};
        LC724FindPivotIndex lc724FindPivotIndex = new LC724FindPivotIndex();
        int index = lc724FindPivotIndex.pivotIndex(arr);
        System.out.println("index = " + index);
    }

    public int pivotIndex(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return -1;
        }

        /**
         * 先去计算数组所有元素的和, 这样方便查找左侧元素 累加和, 同时 方便计算 剩余元素和
         */
        int sum = 0;
        for (int everyNum : nums) {
            sum += everyNum;
        }

        int leftSum = 0;
        for (int index = 0; index < nums.length; index++) {
            /**
             * 1. 那么 index 这个索引是否是中心下标呢？
             *  取决于其左侧所有元素相加的和 == 右侧所有元素相加的和
             *
             * 2. 右侧所有元素相加的和可以根据总和、i 左侧所有元素相加的和、当前元素的值获取到
             */
            int rightSum = sum - nums[index] - leftSum;

            // 左侧所有元素相加和 == 右侧所有元素相加的和
            if (leftSum == rightSum) {
                return index;
            }

            /**
             * 否则 i 不是中心下标
             * 那么需要继续去访问，而开始访问下一个元素前，nums[i] 这个元素就要归于 leftSum 了
             */
            leftSum += nums[index];
        }
        return -1;
    }

}
