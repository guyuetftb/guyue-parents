package com.gy.algorithm.basic.stack;

import java.util.Stack;

public class LC20ValidParentheses {
    public static void main(String[] args) {

    }

    // 左括号 压栈
    // 右括号 弹栈, 比较是否匹配
    public boolean isValid(String s) {
        if (null == s || s.length() % 2 == 1) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if ('(' == currentChar) {
                stack.push(currentChar);
            } else if ('[' == currentChar) {
                stack.push(currentChar);
            } else if ('{' == currentChar) {
                stack.push(currentChar);
            } else {
                // 栈为空, 返回false
                if (stack.isEmpty()) {
                    return false;
                }
                Character topChar = stack.peek();
                if ((')' == currentChar && topChar == '(')
                        || (']' == currentChar && topChar == '[')
                        || ('}' == currentChar && topChar == '{')) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
