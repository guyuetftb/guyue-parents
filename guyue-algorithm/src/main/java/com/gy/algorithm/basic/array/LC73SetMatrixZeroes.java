package com.gy.algorithm.basic.array;

import java.util.Arrays;

public class LC73SetMatrixZeroes {

    public static void main(String[] args) {

        int[][] matrix = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        LC73SetMatrixZeroes lc73SetMatrixZeroes = new LC73SetMatrixZeroes();
        lc73SetMatrixZeroes.setZeroes(matrix);
        Arrays.stream(matrix).forEach(x -> {
            Arrays.stream(x).forEach(System.out::println);
        });
    }

    /**
     * {1, 1, 1}
     * {1, 0, 1}
     * {1, 1, 1}
     * <p>
     * zero[1,1]
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        // 先记录每行每列是不是有0
        boolean[] row = new boolean[matrix.length];
        boolean[] col = new boolean[matrix[0].length];
        for (int indexRow = 0; indexRow < matrix.length; indexRow++) {
            for (int indexCol = 0; indexCol < matrix[0].length; indexCol++) {
                if (matrix[indexRow][indexCol] == 0) {
                    row[indexRow] = true;
                    col[indexCol] = true;
                }
            }
        }

        for (int indexRow = 0; indexRow < matrix.length; indexRow++) {
            for (int indexCol = 0; indexCol < matrix[0].length; indexCol++) {
                if (row[indexRow] || col[indexCol]) {
                    matrix[indexRow][indexCol] = 0;
                }
            }
        }
    }
}
