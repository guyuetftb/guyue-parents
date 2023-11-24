package com.gy.algorithm.basic.string;

import java.util.Stack;

public class LC678ValidParenthesisString {

    public static void main(String[] args) {
        String str = "(*)";
        LC678ValidParenthesisString lc678ValidParenthesisString = new LC678ValidParenthesisString();
        boolean validString = lc678ValidParenthesisString.checkValidString(str);
        System.out.println(validString);
    }

    /**
     * 括号匹配问题的经典解法:
     * <p>
     * 1. 栈存放的是索引
     * 一栈存左括号，一栈存星号
     * <p>
     * 2. 遍历过程中，同时判断是否有足够的右括号使他们出栈
     * 优先抵消左括号"（" 贪心思想
     * <p>
     * 3. 两栈同时出栈并判断，
     * 要求: 所有左括号，都有 其右边索引的星号 能使其抵消，左括号不能还有富余
     */
    public boolean checkValidString(String s) {
        Stack<Integer> leftStack = new Stack<>();
        Stack<Integer> starStack = new Stack<>();

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (c == '(') {
                leftStack.push(index);
            } else if (c == '*') {
                starStack.push(index);
            } else {
                if (!leftStack.isEmpty()) {
                    leftStack.pop();
                } else if (!starStack.isEmpty()) {
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }

        /**
         * 要求: 所有左括号，都有 其右边索引的星号 能使其抵消，左括号不能还有富余
         */
        while (!leftStack.isEmpty() && !starStack.isEmpty()) {
            if (leftStack.pop() > starStack.pop()) {
                return false;
            }
        }
        return leftStack.isEmpty();
    }
}
