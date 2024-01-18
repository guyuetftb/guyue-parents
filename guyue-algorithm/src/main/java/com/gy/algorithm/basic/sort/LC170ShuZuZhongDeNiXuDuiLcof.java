package com.gy.algorithm.basic.sort;

/**
 * 在股票交易中，如果前一天的股价高于后一天的股价，则可以认为存在一个「交易逆序对」。
 * 请设计一个程序，输入一段时间内的股票交易记录 record，
 * 返回其中存在的「交易逆序对」总数。
 * <p>
 * 示例 1:
 * <p>
 * 输入：record = [9, 7, 5, 4, 6]
 * 输出：8
 * 解释：交易中的逆序对为 (9, 7), (9, 5), (9, 4), (9, 6), (7, 5), (7, 4), (7, 6), (5, 4)。
 */
public class LC170ShuZuZhongDeNiXuDuiLcof {

    public static void main(String[] args) {

    }

    public int reversePairs(int[] record) {
        return 0;
    }

    /**
     * 暴力解法,双层循环
     *
     * @param nums
     * @return
     */
    public int reversePairs1(int[] nums) {
        int cnt = 0;
        int len = nums.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] > nums[j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
