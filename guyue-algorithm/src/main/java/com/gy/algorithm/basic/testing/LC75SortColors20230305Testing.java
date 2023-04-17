package com.gy.algorithm.basic.testing;

/**
 * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * <p>
 * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * <p>
 * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 */
public class LC75SortColors20230305Testing {

    public static void main(String[] args) {
        int[] nums = {2,0,2,1,1,0};
        new LC75SortColors20230305Testing().sortColors(nums);
    }

    // 本题也采用双指针来解决问题
    // 只有3种颜色，用0,1,2表示
    // left 指针在数组左侧
    // right 指针在数组右侧
    // cur 游标指针
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int index = 0;


        // 注意: 如果 这里 要改写成for循环，要少比一次.
        // for (int index = 0; index < nums.length; ) {
        while (index <= right) {
            int cur = nums[index];
            // 交换到左侧
            if (cur == 0) {
                swap(nums, left, index);
                left++;
                index++;

                // 中间不动
            } else if (cur == 1) {
                index++;
                // 交换到右侧
            } else if (cur == 2) {

                swap(nums, right, index);

                right--;
            }
        }
        System.out.println("....");
    }

    public void swap(int[] nums, int i, int k) {
        int temp = nums[i];

        nums[i] = nums[k];

        nums[k] = temp;
    }
}
