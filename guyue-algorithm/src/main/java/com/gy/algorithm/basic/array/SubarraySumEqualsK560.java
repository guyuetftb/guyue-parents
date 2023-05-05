package com.gy.algorithm.basic.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 2023-05-04
 * --Tag: 吴师兄 算法-前缀和
 */
public class SubarraySumEqualsK560 {

    public static void main(String[] args) {

    }

    public int subarraySum(int[] nums, int k) {
        // k count出现的次数
        int kCount = 0;

        // 累加 pre
        int aggPre = 0;

        // 记录 累加的每种情况的次数
        Map<Integer, Integer> map = new HashMap<>();

        // 防止出现 [1,2,3,4], k = 6
        // 累加到 1+2+3 = 6, 6-6 = 0;
        // 如果不把 key = 0; 放入 map，就会出现漏记录的问题
        // TODO --------------
        // 一开始，需要设置前缀和为 0 时，出现的次数为 1 次
        // 这一行的作用就是为了应对 nums[0] +nums[1] + ... + nums[i] == k 这种情况
        // 如数组 [1, 2, 3, 6]
        // 这个数组的累加和数组为 [1, 3, 【6】, 12]
        // 如果 k = 6, 假如 mp 中没有预先存储(0, 1)
        // 那么来到累加和为 6 的位置时，这时 mp 中存储的就只有两个数据 (1, 1), (3, 1)
        // 想去判断 mp.containsKey(pre - k) , 这时 pre - k = 6 - 6 = 0
        // 但 map 中没有 (0, 1) ，
        // 因为这个时候忽略了从下标 0 累加到下标 i 等于 k 的情况
        // 仅仅是统计了从下标大于 0 到某个位置等于 k 的所有答案
        map.put(0, 1);

        // 开始从头到尾遍历 nums 数组，在遍历过程中，会执行两个操作
        // 1、存储索引为 i 的这个元素时，前缀和的值是多少，并且把这个值出现的频次存储到 mp 中
        // 2、判断之前有没有存储 pre - k 这种前缀和，如果有，说明 pre - k 到 pre 直接的那些元素值之和就是 k
        for (int index = 0; index < nums.length; index++) {
            // 累加数组中的元素
            aggPre += nums[index];

            // map中有符合条件的数据
            // 假如: aggPre = 12, k = 6, aggPre - 6 = 6
            if (map.containsKey(aggPre - k)) {

                // 如果有，说明 pre - k 到 pre 直接的那些元素值之和就是 k
                // 找到了一组，累加到 count 上
                kCount += map.get(aggPre - k);
            }

            // 这个值出现的频次存储到 mp 中
            // getOrDefault：当 Map 集合中有这个 key 时，就使用这个 key 对应的 value 值
            // 如果没有就使用默认值 defaultValue
            map.put(aggPre, map.getOrDefault(aggPre, 0) + 1);
        }

        return kCount;
    }
}
