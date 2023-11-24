package com.gy.algorithm.basic.string;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/roman-to-integer/description/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。
 */
public class LC13RomanToInteger {

    public static void main(String[] args) {
        String str = "MCMXCIV";
        LC13RomanToInteger lc13RomanToInteger = new LC13RomanToInteger();
        int i = lc13RomanToInteger.romanToInt(str);
        System.out.println(i);
    }

    /**
     * 按照题目的描述，可以总结如下规则：
     * <p>
     * 1. 罗马数字由 I,V,X,L,C,D,M 构成；
     * 2. 当小值在大值的左边，则减小值，如 IV=5-1=4；
     * 3. 当小值在大值的右边，则加小值，如 VI=5+1=6；
     * 4. 由上可知，右值永远为正，因此最后一位必然为正。
     * 总结: 一言蔽之，把一个小值放在大值的左边，就是做减法，否则为加法。
     */
    public int romanToInt(String s) {

        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);


        int resultSum = 0;
        // 初始第1个值, 通过不断的比较 curNum 与 preNum, 决定进行 加操作, 减操作
        int preNum = map.get(s.charAt(0) + "");
        for (int index = 1; index < s.length(); index++) {

            int curNum = map.get(s.charAt(index) + "");
            if (preNum < curNum) {
                // 当小值在大值的左边，则减小值，如 IV=5-1=4；
                resultSum -= preNum;
            } else {
                // 当小值在大值的右边，则加小值，如 VI=5+1=6；
                resultSum += preNum;
            }
            // 当前值 赋值给 之前值
            preNum = curNum;
        }

        resultSum += preNum;
        return resultSum;
    }
}
