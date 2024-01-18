package com.gy.algorithm.basic.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。所有得分都 互不相同 。
 * <p>
 * 运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。运动员的名次决定了他们的获奖情况：
 * <p>
 * 名次第 1 的运动员获金牌 "Gold Medal" 。
 * 名次第 2 的运动员获银牌 "Silver Medal" 。
 * 名次第 3 的运动员获铜牌 "Bronze Medal" 。
 * 从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
 * 使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：score = [5,4,3,2,1]
 * 输出：["Gold Medal","Silver Medal","Bronze Medal","4","5"]
 * 解释：名次为 [1st, 2nd, 3rd, 4th, 5th] 。
 */
public class LC506RelativeRanks {

    public static void main(String[] args) {
        LC506RelativeRanks lc506RelativeRanks = new LC506RelativeRanks();
        int[] score = new int[]{5, 4, 3, 2, 1};
        lc506RelativeRanks.findRelativeRanks(score);
    }

    public String[] findRelativeRanks(int[] score) {
        int n = score.length;
        //奖牌描述
        String[] desc = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        int scoreArrLength = score.length;

        //答案
        String[] result = new String[scoreArrLength];

        //按分数排序,从小到大排序
        int[] clone = score.clone();
        Arrays.sort(clone);

        //倒序迭代,把最大的分值放到最前面
        Map<Integer, Integer> map = new HashMap<>();
        for (int index = scoreArrLength - 1; index >= 0; index--) {
            // index = 4    5 - 1 - 4 = 0
            // index = 3    5 - 1 - 3 = 1
            // index = 2    5 - 1 - 2 = 2
            // index = 1    5 - 1 - 1 = 3
            // index = 0    5 - 1 - 0 = 4
            map.put(clone[index], scoreArrLength - 1 - index);
        }

        //反向迭代,把位次对应的奖牌展示出来
        for (int i = 0; i < scoreArrLength; i++) {
            int rank = map.get(score[i]);
            result[i] = rank < 3 ? desc[rank] : String.valueOf(rank + 1);
        }
        return result;
    }

}
