package com.gy.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Friendship is a sheltering tree.
 *
 * @author : guyuetftb
 * @date : 2020-09-14 15:24 [2020-09-02]
 */
public class LC350ArrayIntersectionOfTwoArraysIISortedArray {

    public static int[] firstArr = new int[]{1, 3, 4, 4, 8, 8, 9};
    public static int[] secondArr = new int[]{2, 4, 4, 5, 8, 7, 9, 9};

    /**
     * TODO 题目说明
     * 1. 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
     * ME: 我刚拿到题目时, 没读懂上面这句话, 认为只要2个组织都包含元素, 就输出, 这不对(Wrong)
     * ME: 要考虑2个数组中元素的个数, 个数相同了才输出, 如果某元素在 firstArr 中出现3次, 在 secondArr 中出现2次, 以次数低的为准
     * 2. 不考虑元素的输出顺序.
     *
     * <p>
     * TODO [进阶] 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * firstArr = [1,3,4,4,8,8,9]
     * secondArr = [2,4,4,5,8,7,9,9]
     *
     * @param args
     */
    public static void main(String[] args) {
        type1().forEach(System.out::println);
    }

    public static List<Integer> type1() {
        List<Integer> result = new ArrayList<>();
        // TODO 解题思路:
        //  firstArr = [1,3,4,4,8,8,9]
        //  secondArr = [2,4,4,5,8,7,9,9]
        //  1. 如果2个数组是排好序的, 可以使用 『双指针』方式
        //  2. step1: 创建2个指针, 分别指向2个数组第1个元素, 判断指向元素是否相等
        //  3. step2: 相等输出, 2个指针加1
        //  4. step3: 小于, 增加较小的指针
        //  5. step4: 直到数据超出范围
        int first = 0, second = 0;
        while (first < firstArr.length && second < secondArr.length) {
            if (firstArr[first] == secondArr[second]) {
                result.add(firstArr[first]);
                first++;
                second++;
            } else if (firstArr[first] <= secondArr[second]) {
                first++;
            } else {
                second++;
            }
        }
        return result;
    }
}

