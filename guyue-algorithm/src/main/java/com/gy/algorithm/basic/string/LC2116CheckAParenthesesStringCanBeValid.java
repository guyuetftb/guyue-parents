package com.gy.algorithm.basic.string;


/**
 * https://leetcode.cn/problems/check-if-a-parentheses-string-can-be-valid/description/
 * <p>
 * 一个括号字符串是只由 '(' 和 ')' 组成的 非空 字符串。如果一个字符串满足下面 任意 一个条件，那么它就是有效的：
 * <p>
 * 字符串为 ().
 * 它可以表示为 AB（A 与 B 连接），其中A 和 B 都是有效括号字符串。
 * 它可以表示为 (A) ，其中 A 是一个有效括号字符串。
 * 给你一个括号字符串 s 和一个字符串 locked ，两者长度都为 n 。
 * locked 是一个二进制字符串，只包含 '0' 和 '1' 。对于 locked 中 每一个 下标 i ：
 *
 * <p>
 * 如果 locked[i] 是 '1' ，你 不能 改变 s[i] 。
 * 如果 locked[i] 是 '0' ，你 可以 将 s[i] 变为 '(' 或者 ')' 。
 * 如果你可以将 s 变为有效括号字符串，请你返回 true ，否则返回 false 。
 */
public class LC2116CheckAParenthesesStringCanBeValid {

    public static void main(String[] args) {
        String str = "))()))";
        String locked = "010100";
        LC2116CheckAParenthesesStringCanBeValid lc2116CheckAParenthesesStringCanBeValid = new LC2116CheckAParenthesesStringCanBeValid();
        lc2116CheckAParenthesesStringCanBeValid.canBeValid(str, locked);
    }

    /**
     * 1. 字符串的个数必须是偶数个，否则返回false;
     * 2. 从左往右遍历，左括号的数量 >= 右括号的数量(否则返回false), located[i] = 0 的均视为左括号
     * 3. 从右往左遍历，右括号的数量 >= 左括号的数量(否则返回false), located[i] = 0 的均视为右括号
     */
    public boolean canBeValid(String s, String locked) {
        int strLength = s.length();
        /**
         * 不是偶数, 退出
         */
        if (strLength % 2 == 1) {
            return false;
        }

        /**
         * 从左往右遍历，
         * 1. 左括号的数量 >= 右括号的数量, 结果为:true,
         * 2. 左括号的数量 < 右括号的数量, 否则返回false,
         * located[i] = 0 的均视为左括号
         */
        int leftIndex = 0;
        int rightIndex = 0;
        // 左括号的数量 >= 右括号的数量
        for (int index = 0; index < strLength; index++) {
            char ch1 = s.charAt(index);
            char ch2 = locked.charAt(index);
            if (ch2 == '0' || ch1 == '(') {
                leftIndex++;
            } else {
                rightIndex++;
            }
            if (rightIndex > leftIndex) {
                return false;
            }
        }

        /**
         * 1. 从右往左遍历
         * 2. 右括号的数量 >= 左括号的数量, 结果: true
         * 3. 右括号的数量 < 左括号的数量, 否则返回false
         * located[i] = 0 的均视为右括号
         */
        leftIndex = 0;
        rightIndex = 0;
        // 右括号的数量 >= 左括号的数量
        for (int index = strLength - 1; index >= 0; index--) {
            char ch1 = s.charAt(index);
            char ch2 = locked.charAt(index);
            if (ch2 == '0' || ch1 == ')') {
                rightIndex++;
            } else {
                leftIndex++;
            }
            if (rightIndex < leftIndex) {
                return false;
            }
        }

        return true;
    }
}
