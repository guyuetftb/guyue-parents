package com.gy.algorithm.basic.array;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/merge-intervals/description/?envType=study-plan-v2&envId=top-100-liked
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 * <p>
 * 1. 首先把数组排序，先按照起点升序排列，如果起点位置相同，按照结尾升序排序。
 * 2. 设置start 和 end 为当前区间的开始和结尾。
 * 3. 遍历intervals，如果下一个的起点小于当前区间的结尾，那么就说明这两个区间有重叠，要进行合并，所以要更新结尾，end = Math.max(end, intervals[i + 1][1])
 * 4. 如果下一个的起点，大于当前区间的结尾，说明这两个区间没有重叠。之前start，end是一个单独的区间，所以保存起来。保存之后，重新设置strat和end，继续向后进行合并。
 */
public class LC56MergeIntervals {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        LC56MergeIntervals lc56MergeIntervals = new LC56MergeIntervals();
        int[][] merge = lc56MergeIntervals.merge(intervals);
        Arrays.stream(merge).forEach((int[] a) -> {
            System.out.println(a[0] + "," + a[1]);
        });
    }

    public int[][] merge(int[][] intervals) {
        // 保存结果
        List<int[]> resList = new ArrayList<>();

        // 对数组中的所有元素排序
        Arrays.sort(intervals, (arr1, arr2) -> arr1[0] == arr2[0] ? arr1[1] - arr2[1] : arr1[0] - arr2[0]);

        int start = intervals[0][0];
        int end = intervals[0][1];
        // 从第1个数组开始判断, 直到大数组中所有 小数组 都遍历一次
        for (int i = 1; i < intervals.length; i++) {
            // 1. 首次运行, end是 第一个小数组的 end
            // 2. 后面运行, end是  没有重叠的 小数组的 end
            // [1,3], [2,6] 如果 下一个元素start <= 上一个元素end, 就说明有重叠
            if (intervals[i][0] <= end) {
                //两个区间有重叠
                end = Math.max(end, intervals[i][1]);
            } else {
                //两个区间没有重叠，保存[start,end]，然后更新
                resList.add(new int[]{start, end});

                // 重置 start, end
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }

        // 把最后一个 元素 添加到结果里.
        resList.add(new int[]{start, end});

        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = resList.get(i);
        }
        return res;
    }


}
