package com.gy.algorithm.basic.testing;

/**
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 */
public class LC88MergeSortedArray20230305Testing {

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,0,0,0};
        int m = 3;
        int[] nums2 = {2,5,6};
        int n = 3;
        new LC88MergeSortedArray20230305Testing().merge(nums1,m,nums2,n);
        System.out.println();
    }

    // 使用双重循环
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int one1 = m - 1;
        int two2 = n - 1;
        int eleIndex = m + n - 1;
        while (two2 >= 0) {
            if (one1 >= 0 && nums2[two2] > nums1[one1]) {
                nums1[eleIndex] = nums2[two2];
                two2--;
                eleIndex--;
            } else {
                nums1[eleIndex] = nums1[one1];
                one1--;
                eleIndex--;
            }
        }
    }
}
