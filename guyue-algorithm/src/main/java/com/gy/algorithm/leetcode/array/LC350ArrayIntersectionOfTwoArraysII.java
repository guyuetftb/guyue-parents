package com.gy.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Friendship is a sheltering tree.
 *
 * @author : guyuetftb
 * @date : 2020-09-14 15:24 [2020-09-02]
 */
public class LC350ArrayIntersectionOfTwoArraysII {

    public static int[] firstArr = new int[]{1, 2, 2, 1};
    public static int[] secondArr = new int[]{2, 2};

    /**
     * TODO 题目说明
     * 1. 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
     * ME: 我刚拿到题目时, 没读懂上面这句话, 认为只要2个组织都包含元素, 就输出, 这不对(Wrong)
     * ME: 要考虑2个数组中元素的个数, 个数相同了才输出, 如果某元素在 firstArr 中出现3次, 在 secondArr 中出现2次, 以次数低的为准
     * 2. 不考虑元素的输出顺序.
     *
     * @param args
     */
    public static void main(String[] args) {
        type1().forEach(System.out::println);
    }

    public static List<Integer> type1() {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> numberMap = new HashMap();
        // TODO 解题思路:
        //  1-method. 如果把2个数组先分别存入 firstMap, secondMap, 迭代任意一个Map, 用Map中的『元素』去另一个Map中Get, 存在就输出
        //      这种方式的时间复杂度, 空间复杂度都太低, 效率不高.
        //  2-method. 迭代firstArr, 元素存入numberMap中, 累加重复元素次数
        //      迭代secondArr, 判断元素是否在numberMap中, 存在减1, 记录重复元素, 记录元素就是最终结果;
        //      Wrong:  ME: 我之前想可以不记录, 减到最后, 次数等于0的就是相同元素, 有Bug, 少输出了 『元素次数大于0的元』。
        for (int i : firstArr) {
            if (numberMap.containsKey(i)) {
                numberMap.put(i, numberMap.get(i) + 1);
            } else {
                numberMap.put(i, 1);
            }
        }
        for (int s : secondArr) {
            if (numberMap.containsKey(s) && numberMap.get(s) > 0) {
                // 收集重复元素
                result.add(s);
                // 元素次数减1
                numberMap.put(s, numberMap.get(s) - 1);
            }
        }
        return result;
    }
}

