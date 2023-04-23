package com.gy.algorithm.basic.stack;

import java.util.Stack;

public class MaximalRectangle85 {

    public static void main(String[] args) {

    }

    public int maximalRectangle(char[][] matrix) {
        // 对于每一行来说, 都是一维的
        int[] heights = new int[matrix[0].length];

        // 最大值
        int maxArea = 0;

        for (int row = 0; row < matrix.length; row++) {

            for (int col = 0; col < matrix[0].length; col++) {

                if (matrix[row][col] == '1') {
                    //循环判断每个元素, 如果元素为1, 把1积累下来
                    heights[col] += 1;
                } else {
                    // 如果元素为0, 则把元素重置为0
                    heights[col] = 0;
                }
            }

            // 求 第 row 行的最大面积
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
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
