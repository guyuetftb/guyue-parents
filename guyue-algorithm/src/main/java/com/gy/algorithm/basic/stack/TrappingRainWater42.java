package com.gy.algorithm.basic.stack;

import java.util.Stack;

public class TrappingRainWater42 {

    public static void main(String[] args) {

        TrappingRainWater42 trappingRainWater42 = new TrappingRainWater42();
        trappingRainWater42.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
    }


    public int trap(int[] height) {
        if (null == height || height.length <= 2) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();

        int result = 0;

        for (int index = 0; index < height.length; index++) {
            // 栈为空
            if (stack.isEmpty()) {
                stack.push(index);
            }
            // 栈顶元素  大于 当前元素
            else if (height[index] < height[stack.peek()]) {
                // 无法形成 ao cao, 加入栈中
                stack.push(index);
            }
            // 栈顶元素, 等于当前元素
            else if (height[index] == height[stack.peek()]) {
                // 无法形成 ao cao
                stack.push(index);
            } else {
                // 可能形成 ao cao
                // 由于栈中可能有多个元素，与当前元素形成 ao cao。当移除栈顶元素后, 剩下的这些元素可以与栈顶元素 接着形成 ao cao
                // 因此，需要不断的比较, 当前元素 和 栈顶元素，
                // 如果 当前元素，大于 栈顶元素， 我们就去计算 面积
                while (!stack.isEmpty() && height[index] > height[stack.peek()]) {

                    // 获取底部索引
                    Integer b = stack.peek();
                    // 栈顶元素移除
                    stack.pop();


                    if (!stack.isEmpty()) {

                        int right = height[index];

                        int left = height[stack.peek()];

                        int bottom = height[b];

                        int h = Math.min(left, right) - bottom;

                        int w = (index - stack.peek()) - 1;

                        System.out.println("凹槽右侧-->" + index);
                        System.out.println("凹槽左侧-->" + stack.peek());
                        System.out.println("凹槽高度-->" + h);
                        System.out.println("凹槽宽度-->" + w);
                        System.out.println("凹槽面积-->" + h * w);
                        System.out.println("---------------");

                        result += h * w;
                    }
                }

                stack.push(index);
            }
        }

        return result;
    }
}
