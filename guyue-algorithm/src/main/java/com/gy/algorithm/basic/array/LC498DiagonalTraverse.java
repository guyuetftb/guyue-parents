package com.gy.algorithm.basic.array;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 矩阵行列的性质：
 * <p>
 * 同一条对角线上的每个点(x, y)的横纵坐标之和 x + y相等，且都等于对角线的序号（仔细观察上图）LC498DiagonalTraverse.png。
 * 如何确定每条对角线的起始和终点端点 ？
 * 根据对角线性质，只要知道端点的 横，纵 坐标之一，就可以得到另一维坐标，因此我们可以只关心 横坐标。
 * <p>
 * 对角线的起始和终点端点横坐标：
 * 这样我们就确定了【偶数对角线】的起始和终点端点横坐标分别为:（i是对角线序号）
 * 1. 起始端点横坐标： x = min(i, n - 1)
 * 2. 终点端点横坐标： x = max(0, i - m + 1)
 * 3. 纵坐标为 i - x。
 * 而【奇数对角线】的遍历方向恰好和【偶数对角线】相反，因此【奇数对角线】的起始和终点端点横坐标分别为:（i是对角线序号）
 * 1. 起始点横坐标：x = max(0, i - m + 1)
 * 2. 终点点横坐标：x = min(i, n - 1)
 * 3. 纵坐标为 i - x。
 * <p>
 * 接下来的思路就很明确了，遍历每条对角线。如果是偶数对角线，就从下往上遍历；如果是奇数对角线，就从上往下遍历。
 * 具体过程如下：
 * 1、定义答案数组res，遍历每条对角线。
 * 2、对于每条序号为i的对角线，判断其奇偶性：
 * 如果是偶数对角线 ，确定其横坐标x，从下往上遍历，将mat[x][i - x]加入res中。
 * 如果是奇数对角线 ，确定其横坐标x，从上往下遍历，将mat[x][i - x]加入 res中。
 * 3、最后返回res。
 */
public class LC498DiagonalTraverse {

    public static void main(String[] args) {
        int[][] mat = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        LC498DiagonalTraverse lc498DiagonalTraverse = new LC498DiagonalTraverse();
        int[] diagonalOrder = lc498DiagonalTraverse.findDiagonalOrder(mat);
        Arrays.stream(diagonalOrder).forEach(System.out::println);
    }

    public int[] findDiagonalOrder(int[][] mat) {
        if (mat.length == 0 || mat[0].length == 0) {
            return new int[0];
        }
        // 拿到 矩阵的行与列
        int row = mat.length, col = mat[0].length;

        // 确定返回结果大小
        int[] res = new int[row * col];

        for (int index = 0, idx = 0; index < row + col - 1; index++) {
            if (index % 2 == 0) {
                //偶数对角线
                int evenStartIndex = Math.min(index, row - 1);
                int evenEndIndex = Math.max(0, index - col + 1);
                for (int x = evenStartIndex; x >= evenEndIndex; x--) {
                    //从下往上遍历
                    res[idx++] = mat[x][index - x];
                }
            } else {
                //奇数对角线
                int oddStartIndex = Math.max(0, index - col + 1);
                int oddEndIndex = Math.min(index, row - 1);
                for (int x = oddStartIndex; x <= oddEndIndex; x++) {
                    //从上往下遍历
                    res[idx++] = mat[x][index - x];
                }
            }
        }
        return res;
    }


    /**
     * 1. 算每个数的x+y坐标，发现一样x+y的坐标的值是在同一条线上的；
     * 2. 当x+y的和是偶数的时候，线是向上斜走的
     * 3. 当x+y的和是奇数的时候，线是向下斜走的
     * 4. 那么，当线是向上斜走的时候，x坐标大的数是排在前面
     * 5. 那么，当线是向下斜走的时候，x坐标小的数是排在前面
     * <p>
     * [1,2,3]
     * [4,5,6]
     * [7,8,9]
     * 作者：晴天☀️
     * 链接：https://leetcode.cn/problems/diagonal-traverse/
     *
     * @param matrix
     * @return
     */
    public int[] findDiagonalOrder2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return new int[]{};
        }

        int row = matrix.length;
        int col = matrix[0].length;
        // 保存结果集
        List<Integer>[] ans = new LinkedList[(row - 1) + (col - 1) + 1];

        // 每行，每列迭代
        /** 从1的左下角开始，斜着向上遍历
         * [1,2,3]
         * [4,5,6]
         * [7,8,9]
         */
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int sum = i + j;
                if (ans[sum] == null) {
                    ans[sum] = new LinkedList<>();
                }
                if (sum % 2 == 0) {
                    // 当x+y的和是偶数的时候，线是向上斜走的
                    // 那么，x坐标大的数是排在前面
                    ans[sum].add(0, matrix[i][j]);
                } else {
                    ans[sum].add(matrix[i][j]);
                }
            }
        }

        int[] res = new int[row * col];
        int index = 0;
        for (int k = 0; k < ans.length; k++) {
            if (ans[k] != null) {
                for (int num : ans[k]) {
                    res[index++] = num;
                }
            }
        }
        return res;
    }

}
