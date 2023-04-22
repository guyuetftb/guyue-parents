package com.gy.algorithm.basic.stack;

import java.util.Stack;

public class MinStack155 {

    Stack<Integer> stack;
    Stack<Integer> minStack;

    public static void main(String[] args) {

        // 借助另外一个辅助 栈, 保存当前 栈中元素的最小值.
        // 当栈中元素，出栈时, 辅助栈的元素与 当前栈中的元素相同, 辅助栈元素也出栈
    }

    public MinStack155() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);

        if (!minStack.isEmpty()) {

            Integer peekVal = minStack.peek();
            if (val <= peekVal) {
                minStack.push(val);
            }

        } else {
            minStack.push(val);
        }
    }

    public void pop() {
        // TODO 这里傻逼了, 这里不能用2个 Integer 类型直接比较
        int popVal = stack.pop();
        int peekVal = minStack.peek();

        // 元素值相同, 把栈顶元素移除掉
        if (popVal == peekVal) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
