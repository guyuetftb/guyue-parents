package com.gy.algorithm.basic.testing;


/**
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 * <p>
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。
 * <p>
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 * <p>
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 */
public class LC26RemoveDuplicates20230305Testing {

    // 该题使用2个指针, slow指针用于记录不重复项的最新下标
    // fast指针用于遍历数组
    public int removeDuplicates(int[] nums) {

        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (fast == 0 || nums[fast] != nums[fast - 1]) {
                // fast == 0, slow向后移动一位.
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
