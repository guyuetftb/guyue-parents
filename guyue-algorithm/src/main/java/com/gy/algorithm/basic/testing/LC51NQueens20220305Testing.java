package com.gy.algorithm.basic.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 皇后的攻击范围是一个二维数组
// 确定每个皇后的攻击范围，需要确定下一个皇后的攻击范围
public class LC51NQueens20220305Testing {
    // 保存多种解的 容器
    List<List<String>> res = new ArrayList<List<String>>();

    public static void main(String[] args) {
        LC51NQueens20220305Testing nQueues = new LC51NQueens20220305Testing();
        nQueues.solveNQueens(4);
        for (List<String> strings : nQueues.res) {
            System.out.println(String.valueOf(strings));
        }
    }



    public List<List<String>> solveNQueens(int n) {
        // 攻击范围,初始化为0
        int[][] attack = new int[n][n];
        for (int[] ints : attack) {
            Arrays.fill(ints, 0);
        }


        // 皇后位置,初始化为.
        char[][] queens = new char[n][n];
        for (char[] chars : queens) {
            Arrays.fill(chars, '.');
        }

        // 确定N皇后的位置,需要知道皇后的attack, queen,
        ensureQueenPos(0, n, attack, queens);

        return res;
    }

    /**
     * @param rowNum   当前行
     * @param queenNum 皇后个数
     * @param attackArr   皇后攻击范围
     * @param queenArr   皇后位置
     */
    public void ensureQueenPos(int rowNum, int queenNum, int[][] attackArr, char[][] queenArr) {

        // rowNum等于皇后的个数,正处理最后一行数据
        if (rowNum == queenNum) {

            // 某种解
            List<String> someoneResult = new ArrayList<String>();
            for (char[] chars : queenArr) {
                someoneResult.add(String.copyValueOf(chars));
            }

            // 把某种解,加入 最终解
            res.add(someoneResult);
            return;
        }

        // 逐列确认是否在皇后的攻击范围
        for (int colIndex = 0; colIndex < queenNum; colIndex++) {

            // 当前行,当前列 在某个皇后的攻击范围
            if (attackArr[rowNum][colIndex] == 0) {
                // 当前行,当前列,不在攻击范围, 保存全体皇后位置
                int[][] temp = new int[queenNum][queenNum];
                for (int tempRow = 0; tempRow < queenNum; tempRow++) {
                    for (int l = 0; l < queenNum; l++) {
                        for (int m = 0; m < queenNum; m++) {
                            temp[l][m] = attackArr[l][m];
                        }
                    }
                }

                // 确定皇后位置
                queenArr[rowNum][colIndex] = 'Q';

                // 放置一个新皇后, 攻击范围多了,更新攻击范围
                checkQueenAttack(rowNum, colIndex, attackArr);

                // 确定下一个皇后的位置, 攻击范围
                ensureQueenPos(rowNum + 1, queenNum, attackArr, queenArr);

                // 递归结束,拿走皇后,恢复 attack的状态
                attackArr = temp;

                // 恢复皇后位置
                queenArr[rowNum][colIndex] = '.';
            }


        }
    }


    public void checkQueenAttack(int x, int y, int[][] attackArray) {
        // 每个皇后位置有8个方位
        // 左上: x-1,y-1
        // 上: x-1, y
        // 右上: x-1,y+1
        // 左: x,y-1
        // 皇后位: x,y
        // 右:x,y+1
        // 左下: x+1,y-1,
        // 下: x+1,y
        // 右下: x+1,y+1
        // 皇后周围的8个位置(不包含 皇后的位置), 由 (x + j * dx[j], y + j * dy[j]) 决定
        int dx[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        // dy 表示 y 的方向
        int dy[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        // 皇后位的值一定是1
        attackArray[x][y] = 1;

        for (int roundIndex = 0; roundIndex < 8; roundIndex++) {

            for (int arrIndex = 1; arrIndex < attackArray.length; arrIndex++) {
                // 确定 皇后周围的8个坐标
                int tempX = x + arrIndex * dx[roundIndex];
                int tempY = y + arrIndex * dy[roundIndex];

                if (tempX >= 0 && tempX < attackArray.length && tempY >= 0 && tempY < attackArray.length) {
                    attackArray[tempX][tempY] = 1;
                }
            }
        }
    }
}
