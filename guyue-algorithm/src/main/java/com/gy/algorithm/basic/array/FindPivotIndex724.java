package com.gy.algorithm.basic.array;

/**
 * 1. 2023-05-04
 * --Tag: 吴师兄 算法-前缀和
 */
public class FindPivotIndex724 {

    public static void main(String[] args) {

    }

    public int pivotIndex(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return -1;
        }

        // 先去计算数组所有元素的和
        int sum = 0;
        for (int everyNum : nums) {
            sum += everyNum;
        }

        int leftSum = 0;
        for (int index = 0; index < nums.length; index++) {
            // 那么 i 这个索引是否是中心下标呢？
            // 取决于其左侧所有元素相加的和等于右侧所有元素相加的和
            // 而右侧所有元素相加的和可以根据总和、i 左侧所有元素相加的和、当前元素的值获取到
            int rightSum = sum - nums[index] - leftSum;

            // 如果发现其左侧所有元素相加的和等于右侧所有元素相加的和
            if (leftSum == rightSum) {
                return index;
            }

            // 否则 i 不是中心下标
            // 那么需要继续去访问，而开始访问下一个元素前，nums[i] 这个元素就要归于 leftSum 了
            leftSum += nums[index];
        }
        return -1;
    }

}
