package com.gy.algorithm.basic.stack;

import java.util.Stack;

public class LC150EvaluateReversePolishNotation {

    public static void main(String[] args) {

        String[] tokens = new String[]{"4","13","5","/","+"};
        LC150EvaluateReversePolishNotation e = new LC150EvaluateReversePolishNotation();
        System.out.println("----------------->" + e.evalRPN(tokens));
    }

    public int evalRPN(String[] tokens) {

        int leftNum = 0;
        int rightNum = 0;
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            // 加法
            if ("+".equals(token)) {
                rightNum = stack.pop();
                leftNum = stack.pop();
                int result = leftNum + rightNum;
                stack.push(result);
                continue;
            }

            // 減法
            if ("-".equals(token)) {
                rightNum = stack.pop();
                leftNum = stack.pop();
                int result = leftNum - rightNum;
                stack.push(result);
                continue;
            }

            // 乘法
            if ("*".equals(token)) {
                rightNum = stack.pop();
                leftNum = stack.pop();
                int result = leftNum * rightNum;
                stack.push(result);
                continue;
            }

            // 除法
            if ("/".equals(token)) {
                rightNum = stack.pop();
                leftNum = stack.pop();
                int result = leftNum / rightNum;
                stack.push(result);
                continue;
            }

            stack.push(Integer.valueOf(token));
        }
        return stack.pop();
    }
}
