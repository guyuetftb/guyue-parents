package com.gy.algorithm.basic.queue;

import java.util.Stack;

public class LargestRectangleInHistogram84 {

    public static void main(String[] args) {
        int[] height = new int[]{2, 1, 5, 6, 2, 3};
        LargestRectangleInHistogram84 instance84 = new LargestRectangleInHistogram84();
        int i = instance84.largestRectangleArea(height);
        System.out.println("---------------> result = " + i);
    }

    public int largestRectangleArea(int[] heights) {
        int result = 0;

        // 初始化一个新数组, 头，尾元素为0.
        int[] newHeight = new int[heights.length + 2];
        newHeight[0] = 0;
        newHeight[heights.length - 1] = 0;

        // 其他元素不变
        for (int index = 1; index < heights.length + 1; index++) {
            newHeight[index] = heights[index - 1];
        }

        // 单调栈
        Stack<Integer> heightIndexStack = new Stack<Integer>();

        /**
         * 整体思路:
         * 对于每个高度, 如果 知道 左，右边界, 就能计算出面积
         * 那遍历所有高度, 就能计算出所有面积.
         * {0, 2, 1, 5, 6, 2, 3, 0};
         */
        for (int index = 0; index < newHeight.length; index++) {

            // 如果栈不为空, 并且 当前元素值 小于栈顶元素值, 则表示 以栈顶元素为高的矩形 面积可以确定
            while (!heightIndexStack.isEmpty() && newHeight[index] < newHeight[heightIndexStack.peek()]) {

                int curPopIndex = heightIndexStack.pop();

                int curHeight = newHeight[curPopIndex];

                int leftIndex = heightIndexStack.peek();

                int rightIndex = index;

                // 当前宽度
                int curWidth = rightIndex - leftIndex - 1;

                result = Math.max(result, (curHeight * curWidth));
            }

            heightIndexStack.push(index);
        }

        return result;
    }
}
