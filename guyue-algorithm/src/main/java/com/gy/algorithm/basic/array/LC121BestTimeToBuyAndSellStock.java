package com.gy.algorithm.basic.array;


/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/?envType=study-plan-v2&envId=top-interview-150
 *
 */
public class LC121BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] nums = {7, 1, 5, 3, 6, 4};
        LC121BestTimeToBuyAndSellStock lc121BestTimeToBuyAndSellStock = new LC121BestTimeToBuyAndSellStock();
        int i = lc121BestTimeToBuyAndSellStock.maxProfit(nums);
        System.out.println(i);
    }

    /**
     * 如果我们真的在买卖股票，我们肯定会想：如果我是在历史最低点买的股票就好了！
     * 太好了，在题目中，我们只要用一个变量记录一个历史最低价格 minprice，我们就可以假设自己的股票是在那天买的。
     * 那么我们在第 i 天卖出股票能得到的利润就是 prices[i] - minprice。
     * <p>
     * 因此，我们只需要遍历价格数组一遍，记录历史最低点，然后在每一天考虑这么一个问题：
     * 如果我是在历史最低点买进的，那么我今天卖出能赚多少钱？
     * 当考虑完所有天数之时，我们就得到了最好的答案。
     */
    public int maxProfit(int prices[]) {
        // 最低价值设置为: 整形最大值.
        int minPrice = Integer.MAX_VALUE;
        // 最大收益设置为: 0.
        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                // 如果 数组中 有比 最低价格更低的价钱, 保存最低价格
                minPrice = prices[i];
            } else if ((prices[i] - minPrice) > maxProfit) {
                // 如果 当前价格 - 最低价格 > 最大收益
                // 就保存目前的最大收益
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
}
