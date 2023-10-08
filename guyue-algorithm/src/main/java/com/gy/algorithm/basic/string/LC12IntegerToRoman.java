package com.gy.algorithm.basic.string;

/**
 * https://leetcode.cn/problems/integer-to-roman/?envType=study-plan-v2&envId=top-interview-150
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给你一个整数，将其转为罗马数字。
 */
public class LC12IntegerToRoman {

    public static void main(String[] args) {
        LC12IntegerToRoman lc12IntegerToRoman = new LC12IntegerToRoman();
        String s = lc12IntegerToRoman.intToRoman(124);
        System.out.println(s);
    }


    /**
     * 现实中每一个「阿拉伯数字」固定对应一个「罗马数字」，不存在说一个「阿拉伯数字」能够对应出一个较长或者较短的「罗马数字」。
     * <p>
     * 因此这是一个按照固定规则进行转换的「模拟」过程。
     * <p>
     * 根据题意，我们可以列举出有限个罗马字符和其对应的数值。
     * <p>
     * 然后从左到右构建罗马数字，优先构建数值高的罗马字符
     * （如果有足够的分值，尽量尝试构建 "M"，直到分值不够，再尽量尝试构建 "CM" ... ）
     */
    public String intToRoman(int num) {
        // 定义数字枚举
        int[] val = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        // 定义字母枚举
        String[] str = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};


        // 这题是从左到右，不断的模拟罗马数字的构建过程.
        // 如果发现 num 值大于 指字的数值, 就把相应的字符 加到输出中, 同时减去该值
        StringBuilder sb = new StringBuilder();
        // 当 index 小于枚举的长度 并且 num数字大于0时, 循环
        for (int index = 0; index < val.length && num > 0; index++) {

            // 分别取出对应的值
            int cv = val[index];
            String cs = str[index];

            while (num >= cv) {
                // 如果 num >= 取出的值, 累加对应的字符
                sb.append(cs);
                // 同时减去 对应的值
                num -= cv;
            }
        }
        return sb.toString();
    }
}
