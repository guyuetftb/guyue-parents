package com.gy.algorithm.basic.string;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/valid-parentheses/
 * 20. 有效的括号
 */
public class LC20ValidParenthesisString {

    public static void main(String[] args) {
        String str = "()[]{}";
        LC20ValidParenthesisString lc20ValidParenthesisString = new LC20ValidParenthesisString();
        boolean validString = lc20ValidParenthesisString.isValid(str);
        System.out.println(validString);

        boolean validString2 = lc20ValidParenthesisString.isValid2(str);
        System.out.println(validString2);
    }

    /**
     * 括号匹配问题的经典解法
     * 1. 栈存放的是索引
     * 一栈存左括号，一栈存星号
     * <p>
     * 2. 遍历过程中，同时判断是否有足够的右括号使他们出栈
     * 优先抵消左括号"（" 贪心思想
     * <p>
     * 3. 两栈同时出栈并判断，
     * 要求: 所有左括号，都有 其右边索引的星号 能使其抵消，左括号不能还有富余
     */
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }

        Stack<String> stack = new Stack<>();

        for (int index = 0; index < s.length(); index++) {
            char curChar = s.charAt(index);
            if (curChar == '(' || curChar == '{' || curChar == '[') {
                stack.push(String.valueOf(curChar));
            } else {
                // 栈为空, 退出
                if (stack.isEmpty()) {
                    return false;
                }

                /**
                 * 注意:  这里不要写反了
                 * 1. curChar 必须是 关闭的 括号
                 * 2. stack顶的元素是 打开的 括号
                 */
                if (")".equals(String.valueOf(curChar)) && stack.peek().equals("(")) {
                    stack.pop();
                } else if ("}".equals(String.valueOf(curChar)) && stack.peek().equals("{")) {
                    stack.pop();
                } else if ("]".equals(String.valueOf(curChar)) && stack.peek().equals("[")) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public boolean isValid2(String s) {
        Deque<Character> deque = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //碰到左括号，就把相应的右括号入栈
            if (ch == '(') {
                deque.push(')');
            } else if (ch == '{') {
                deque.push('}');
            } else if (ch == '[') {
                deque.push(']');
            } else if (deque.isEmpty() || deque.peek() != ch) {
                return false;
            } else {//如果是右括号, 判断是否和栈顶元素匹配
                deque.pop();
            }
        }
        return deque.isEmpty();
    }

}



