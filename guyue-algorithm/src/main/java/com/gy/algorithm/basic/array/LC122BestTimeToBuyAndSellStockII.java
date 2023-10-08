package com.gy.algorithm.basic.array;

/**
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。
 * 你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
 * 返回 你能获得的 最大 利润 。
 */
public class LC122BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        int[] nums = {7, 1, 5, 3, 4, 6, 4};
        LC122BestTimeToBuyAndSellStockII lc122BestTimeToBuyAndSellStockII = new LC122BestTimeToBuyAndSellStockII();
        int i = lc122BestTimeToBuyAndSellStockII.maxProfit(nums);
        System.out.println(i);
    }

    /**
     * 这个题不出现夸2支股票的情况.
     * 第一天买入3, 当第二天遇到 涨价大于3时, 就立即卖出
     */
    public int maxProfit(int[] prices) {
        int resultProfit = 0;
        int numLength = prices.length;
        for (int index = 1; index < numLength; ++index) {
            // index = 1: 1 - 7 = -6, Math.max的值等于0
            // index = 2: 5 - 1 = 4, Math.max的值等于4
            // index = 3: 3 - 5 = -1, Math.max的值等于-1
            // index = ...:
            resultProfit += Math.max(0, prices[index] - prices[index - 1]);
        }
        return resultProfit;
    }
}
