package com.gy.algorithm.basic.search.binary;

/**
 * 猜数字游戏的规则如下：
 * <p>
 * 每轮游戏，我都会从 1 到 n 随机选择一个数字。 请你猜选出的是哪个数字。
 * 如果你猜错了，我会告诉你，你猜测的数字比我选出的数字是大了还是小了。
 * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有 3 种可能的情况（-1，1 或 0）：
 * <p>
 * -1：我选出的数字比你猜的数字小 pick < num
 * 1：我选出的数字比你猜的数字大 pick > num
 * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
 * 返回我选出的数字。
 * <p>
 * 示例 1：
 * 输入：n = 10, pick = 6
 * 输出：6
 * <p>
 * 示例 2：
 * 输入：n = 1, pick = 1
 * 输出：1
 */
public class LC374GuessNumberHigherOrLower {

    public static void main(String[] args) {

    }

    public int guessNumber_ME(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            int mid = (left + right) / 2;
            if (guess(mid) > 0) {
                //我猜的数字小
                left = mid;
            } else if (guess(mid) < 0) {
                //我猜的数字大
                right = mid;
            } else {
                return mid;
            }
        }
        return right;
    }

    // 链接：https://leetcode.cn/problems/guess-number-higher-or-lower/solutions/827062/gong-shui-san-xie-shi-yong-jiao-hu-han-s-tocm/
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            //(左+右)/2
            long tmp = (long) left + right + 1 >> 1;
            int mid = (int) tmp;
            if (guess(mid) >= 0) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    public int guess(int n) {
        return 0;
    }
}
