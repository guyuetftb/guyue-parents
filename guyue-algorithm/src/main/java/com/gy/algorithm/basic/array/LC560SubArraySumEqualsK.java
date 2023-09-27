package com.gy.algorithm.basic.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 和为 K 的子数组
 * https://leetcode.cn/problems/subarray-sum-equals-k/
 */
public class LC560SubArraySumEqualsK {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        LC560SubArraySumEqualsK lc560SubArraySumEqualsK = new LC560SubArraySumEqualsK();
        int arrNums = lc560SubArraySumEqualsK.subarraySum(arr, 3);
        System.out.println("arrNums = " + arrNums);
    }

    public int subarraySum(int[] nums, int k) {
        // k count出现的次数
        int kCount = 0;

        // 累加 pre
        int aggPre = 0;

        // 记录 累加的每种情况的次数
        /**
         * case1:----------------
         * 防止出现 [1,2,3,4], k = 6
         * 累加到 1+2+3 = 6, 6-6 = 0;
         * 如果不把 key = 0; 放入 map，就会出现漏记录的问题
         * 一开始，需要设置前缀和 aggPre=0 时，出现的次数为1 次
         * 这一行的作用就是为了应对 nums[0] +nums[1] + ... + nums[i] == k 这种情况
         *
         * case1:----------------
         * 如数组 [1, 2, 3, 6]
         * 这个数组的累加和数组为 [1, 3, 6, 12], k = 6, 假如 map 中没有预先存储: (0, 1)
         * 那么来到累加和为 6 的位置时，这时 map 中存储的就只有两个数据 (key=1, value=1), (key=3, value=1)
         * 想去判断 mp.containsKey(pre - k) , 这时 pre - k = 6 - 6 = 0
         * 但 map 中没有 (0, 1) ，
         * 因为是: 忽略了从下标 0 累加到下标 index 等于 k 的情况
         * 仅仅是: 统计了从下标 大于0 到下标 index 等于 k 的情况
         */
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        /**
         * 开始从头到尾遍历 nums 数组，在遍历过程中，会执行两个操作
         * 1、存储 index 的这个元素时，前缀和的值是多少，并且把这个值出现的频次存储到 map 中
         * 2. 判断之前有没有存储 pre - k 这种前缀和，如果有，说明 pre - k 到 pre 直接的那些元素值之和就是 k
         */
        for (int index = 0; index < nums.length; index++) {
            // 累加数组中的元素
            aggPre += nums[index];

            /**
             * map中有符合条件的数据
             * case1: aggPre = 12, k = 6, aggPre - 6 = 6
             * case1: aggPre = 3, k = 3, aggPre - 3 = 0, 0没有在map中, 所以需要事先放进去1个
             */
            if (map.containsKey(aggPre - k)) {
                /**
                 * 如果有，说明 pre - k 到 pre 直接的那些元素值之和就是 k
                 * 找到了一组，累加到 count 上
                 */
                kCount += map.get(aggPre - k);
            }

            /**
             * aggPre 出现的频次存储到 map 中
             * aggPre = 1, value = 1
             * aggPre = 3, value = 1
             * aggPre = 6, value = 1
             */
            map.put(aggPre, map.getOrDefault(aggPre, 0) + 1);
        }

        return kCount;
    }
}
