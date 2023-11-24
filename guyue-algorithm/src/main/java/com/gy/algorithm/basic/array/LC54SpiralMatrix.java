package com.gy.algorithm.basic.array;

import com.gy.algorithm.basic.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class LC54SpiralMatrix {

    public static void main(String[] args) {
        int[][] matrix = ArrayUtils.create2DimArrayWithDefaultValue();
        LC54SpiralMatrix lc54SpiralMatrix = new LC54SpiralMatrix();
        List<Integer> integers = lc54SpiralMatrix.spiralOrder(matrix);
        integers.stream().forEach(System.out::println);
    }

    /**
     * 自己的思路: 螺旋矩阵，先是列加，行加，列减，行减，完成一圈后，行坐标减一，列坐标减一。
     *
     * <p>
     * <p>
     * LC思路:
     * 算法流程：
     * 1. 空值处理： 当 matrix 为空时，直接返回空列表 [] 即可。
     * 2. 初始化： 矩阵 左、右、上、下 四个边界 l , r , t , b ，用于打印的结果列表 res 。
     * 3. 循环打印： “从左向右、从上向下、从右向左、从下向上” 四个方向循环打印。
     * --> 3-1: 根据边界打印，即将元素按顺序添加至列表 res 尾部。
     * --> 3-2: 边界向内收缩 1 （代表已被打印）。
     * --> 3-3: 判断边界是否相遇（是否打印完毕），若打印完毕则跳出。
     * 4. 返回值： 返回 res 即可。
     * 链接：https://leetcode.cn/problems/spiral-matrix/
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (null == matrix || matrix.length == 0) {
            return new ArrayList<>();
        }

        // 创建结果集
        List<Integer> results = new ArrayList<>();

        // 初始化4个指针,
        int leftPoint = 0;  // 左指针坐标（用于从 左->右 )
        int topPoint = 0;   // 上指针坐标, 标示从上到下迭代 多少行 (从 上 -> 下)
        int rightPoint = matrix[0].length - 1;  // 右指针坐标（从 右 -> 左）
        int bottomPoint = matrix.length - 1;    // 下指针坐标, 标示从下往上迭代多少行 (从 下 -> 上)
        while (true) {
            // Part-A:
            // 1. 第一次遍历, leftPoint=0,  边界 <= rightPoint
            for (int i = leftPoint; i <= rightPoint; i++) {
                results.add(matrix[topPoint][i]); // left to right
            }
            if (++topPoint > bottomPoint) {
                // 向下走一行. 顶指针往下一行, 如果大于 bottomPoint, 就说明已经迭代到矩阵中心了
                break;
            }
            // Part-B:
            // 1. 迭代最右侧列, 起始指针由 topPoint控制
            for (int i = topPoint; i <= bottomPoint; i++) {
                results.add(matrix[i][rightPoint]); // top to bottom
            }
            if (leftPoint > --rightPoint) {
                // 向左走一行. 已经迭代到行底了, 右指针减1, 判断是否到了最中心的位置, 如果到了最中心的位置, 停止循环
                break;
            }
            // Part-C:
            // 1. 迭代最下方行, 起始指针由rightPoint控制, leftPoint为界限
            for (int i = rightPoint; i >= leftPoint; i--) {
                results.add(matrix[bottomPoint][i]); // right to left
            }
            if (topPoint > --bottomPoint) {
                // 向上走一行,
                break;
            }
            for (int i = bottomPoint; i >= topPoint; i--) {
                results.add(matrix[i][leftPoint]); // bottom to top
            }
            if (++leftPoint > rightPoint) {
                break;
            }
        }
        return results;
    }
}
