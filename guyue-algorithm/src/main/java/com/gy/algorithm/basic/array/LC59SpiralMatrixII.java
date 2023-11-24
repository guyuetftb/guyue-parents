package com.gy.algorithm.basic.array;

import com.gy.algorithm.basic.ArrayUtils;

public class LC59SpiralMatrixII {

    public static void main(String[] args) {
        int num = 3;
        LC59SpiralMatrixII lc59SpiralMatrixII = new LC59SpiralMatrixII();
        int[][] matrix = lc59SpiralMatrixII.generateMatrix(num);
        ArrayUtils.show2DimArray(matrix);
    }

    public int[][] generateMatrix(int num) {
        int[][] dimArrayEmpty = new int[num][num];
        int totalLength = num * num;
        int leftPoint = 0;
        int rightPoint = dimArrayEmpty.length - 1;
        int topPoint = 0;
        int bottomPoint = dimArrayEmpty[0].length - 1;
        int index = 0;
        while (index < totalLength) {
            // Part-A
            // 1. 顶部行: 从左 -> 右
            for (int i = leftPoint; i <= rightPoint; i++) {
                dimArrayEmpty[topPoint][i] = ++index;
            }
            if (++topPoint > bottomPoint) {
                // topPoint 指针下移
                break;
            }

            // Part-B
            // 1. 右侧列: 上 -> 下。
            // 2. topPoint 在上一步已经 向下移动一步.
            for (int i = topPoint; i <= bottomPoint; i++) {
                dimArrayEmpty[i][rightPoint] = ++index;
            }
            if (leftPoint > --rightPoint) {
                // 1. rightPoint 指针右移: 如果已经大于 leftPoint 退出.
                break;
            }

            // Part-C
            // 1. 左侧列: 右 -> 左
            for (int i = rightPoint; i >= leftPoint; i--) {
                dimArrayEmpty[bottomPoint][i] = ++index;
            }
            if (topPoint > --bottomPoint) {
                // 1. bottomPoint 指针上移: 如果已经大于 topPoint 退出
                break;
            }

            // Part-D
            for (int i = bottomPoint; i >= topPoint; i--) {
                dimArrayEmpty[i][leftPoint] = ++index;
            }
            if (++leftPoint > rightPoint) {
                break;
            }
        }
        return dimArrayEmpty;
    }
}
