package com.gy.algorithm.basic.string;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.cn/problems/longest-valid-parentheses/
 * 32. 最长有效括号
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 */
public class LC32MaxLengthParentheses {

    public static void main(String[] args) {
        String str = "(())())";
        LC32MaxLengthParentheses lc32MaxLengthParentheses = new LC32MaxLengthParentheses();
        int maxLength = lc32MaxLengthParentheses.longestValidParentheses(str);
        System.out.println(maxLength);
    }

    public int longestValidParentheses(String s) {
        // 初始值必须是:0, 不可以是-1, 会减出其他问题
        int maxLength = 0;

        Deque<Integer> stack = new LinkedList<Integer>();
        /**
         * str = "(())())"
         * 先向栈中压入1个初始元素: 防止第1个元素是: ), 这时需要弹栈
         */
        stack.push(-1);
        for (int index = 0; index < s.length(); index++) {
            /**
             * 将 "(" 压入栈中
             */
            if (s.charAt(index) == '(') {
                stack.push(index);
            } else {
                /**
                 * 遇到 ")" 弹栈
                 */
                stack.pop();
                if (stack.isEmpty()) {
                    /**
                     * 1. 栈已经空了把当前index压栈, 这样做为了避免: 下一个元素是 ")", 需要弹栈
                     * 2. 第二个作用是记录下, 上次 配对时 index的值
                     * 3. 执行到此的情况有:
                     *  index=0, 记录下 匹配的开始, 当下次 括号不匹配时, 需要去掉index值, 算出 maxLength
                     */
                    stack.push(index);
                } else {
                    maxLength = Math.max(maxLength, index - stack.peek());
                }
            }
        }
        return maxLength;
    }
}
