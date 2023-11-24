package com.gy.algorithm.basic.array;


/**
 * https://leetcode.cn/problems/remove-element/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * <p>
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 */
public class LC27RemoveElement {

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
        LC27RemoveElement lc27RemoveElement = new LC27RemoveElement();
        int i = lc27RemoveElement.removeElement(nums, 3);
        System.out.println(i);
    }

    /**
     * 双指针优化
     * 思路
     * 如果要移除的元素恰好在数组的开头，例如序列 [1,2,3,4,5][1,2,3,4,5][1,2,3,4,5]，当 val\textit{val}val 为 111 时，
     * 我们需要把每一个元素都左移一位。
     * 注意到题目中说：「元素的顺序可以改变」。实际上我们可以直接将最后一个元素 555 移动到序列开头，
     * 取代元素 111，得到序列 [5,2,3,4][5,2,3,4][5,2,3,4]，同样满足题目要求。这个优化在序列中 val\textit{val}val 元素的数量
     * 较少时非常有效。
     * 实现方面，我们依然使用双指针，两个指针初始时分别位于数组的首尾，向中间移动遍历该序列。
     * <p>
     * 算法
     * 如果左指针 left\textit{left}left 指向的元素等于 val\textit{val}val，此时将右指针 right\textit{right}right 指向的元素复制到左指针 left\textit{left}left 的位置，然后右指针 right\textit{right}right 左移一位。如果赋值过来的元素恰好也等于 val\textit{val}val，可以继续把右指针 right\textit{right}right 指向的元素的值赋值过来（左指针 left\textit{left}left 指向的等于 val\textit{val}val 的元素的位置继续被覆盖），直到左指针指向的元素的值不等于 val\textit{val}val 为止。
     * 当左指针 left\textit{left}left 和右指针 right\textit{right}right 重合的时候，左右指针遍历完数组中所有的元素。
     * 这样的方法两个指针在最坏的情况下合起来只遍历了数组一次。与方法一不同的是，方法二避免了需要保留的元素的重复赋值操作。
     */
    public int removeElement(int[] nums, int val) {
        // 定义左，右两个指针
        int left = 0;
        int right = nums.length;

        // 左指针不能超过右指针
        while (left < right) {
            if (nums[left] == val) {
                // 左指针值等于val, 那么将 最右侧 元素 赋值给 左边指针
                // 这也 符合题意.
                nums[left] = nums[right - 1];
                // 右指针左移, 左指针不变,
                // 如果 赋值之后，依然是要删除的元素, 继续执行上一个操作
                right--;
            } else {
                // 不等于, 左指针 右移
                left++;
            }
        }
        return left;
    }
}
