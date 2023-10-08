package com.gy.algorithm.basic.array;


import java.util.Arrays;

/**
 * https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
 * <p>
 * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
 * <p>
 * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
 * <p>
 * 你所设计的解决方案必须只使用常量级的额外空间。
 */
public class LC167TwoSumIIInputArrayIsSorted {

    public static void main(String[] args) {
        int[] numbers = new int[]{2, 7, 11, 15};
        int target = 9;
        LC167TwoSumIIInputArrayIsSorted lc167TwoSumIIInputArrayIsSorted = new LC167TwoSumIIInputArrayIsSorted();
        int[] ints = lc167TwoSumIIInputArrayIsSorted.twoSum(numbers, target);
        Arrays.stream(ints).forEach(System.out::println);
    }

    /**
     * 很多人做这个题目想不到正确的 O(n)O(n)O(n) 解法，即使看了答案理解了，
     * 下次再做的时候还是会忘记。要想真正理解这道题，就要明白解法背后的道理。
     * 这样不仅可以记住这道题，还能举一反三解决类似的题目。
     * <p>
     * 很多题解只给出了双指针解法的代码，但没有说明解法的正确性。
     * 为什么双指针往中间移动时，不会漏掉某些情况呢？
     * 要解答这个问题，我们要从 缩减搜索空间 的角度思考这个解法。下面我将以文字和图片两种方式进行讲解。
     */
    public int[] twoSum(int[] numbers, int target) {
        int leftPoint = 0;
        int rightPoint = numbers.length - 1;
        while (leftPoint < rightPoint) {
            // 2个指针, 分别从 左 , 右 向中间移动
            int tmpSum = numbers[leftPoint] + numbers[rightPoint];
            // 临时Sum小于 target, 指针右移 (因为数组已经 从小到大排好序了)
            if (tmpSum < target) {
                leftPoint++;
            }
            // 临时Sum大于target, 指针左移 (因为数组已经 从小到大排好序了)
            else if (tmpSum > target) {
                rightPoint--;
            }
            // 等于 target, 返回索引
            else {
                return new int[]{leftPoint + 1, rightPoint + 1};
            }
        }
        return new int[]{-1, -1};
    }
}
