package com.gy.algorithm.basic.testing;

public class LC283ZeroMovie20230305Testing {

    public int[] zeroMovie(int[] arr) {

        // 零移动,通过双指针解决
        // fast指针,判断当前fast 与fast-1 是否相同
        // 相同: 向后移动
        // 不同: 把值赋给slow
        int slow = 0;
        int fast = 0;
        for (; fast < arr.length; fast++) {
            if (arr[fast] == 0) {
                continue;
            }

            arr[slow] = arr[fast];
            slow++;
        }

        for (; slow < arr.length; slow++) {
            arr[slow] = 0;
        }
        return arr;
    }
}
