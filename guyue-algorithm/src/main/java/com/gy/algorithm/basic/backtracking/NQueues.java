package com.gy.algorithm.basic.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueues {

    /**
     * 1. 初始化 结果 变量, 皇后攻击2维数组, 皇后位置2维数组.
     * 2. 确定每个 皇后的位置后，需要设置一个 攻击函数，用来更新 二维数组的值.
     */
    // 结果, 存储符合要求的解。
    List<List<String>> result = new ArrayList<List<String>>();

//    public static void main(String[] args) {
//        NQueues nQueues = new NQueues();
//        nQueues.solveNQueens(4);
//        for (List<String> strings : result) {
//            System.out.println(String.valueOf(strings));
//        }
//    }

    public List<List<String>> solveNQueens(int n) {
        //attack 表示皇后的攻击范围
        int[][] attack = new int[n][n];
        // queues 表示皇后所在位置
        char[][] queues = new char[n][n];

        // 初始化皇后的攻击范围，题目要求 不在攻击范围的，使用0表示. 在攻击范围的，使用1表示。
        for (int[] a : attack) {
            Arrays.fill(a, 0);
        }

        // 初始化 皇后的位置, 要求，位置上有皇后的使用Q表示,没有的使用.表示。
        for (char[] c : queues) {
            Arrays.fill(c, '.');
        }

        // 从棋盘 第0行,第0列处理N皇后的问题。
        backtracking(0, n, queues, attack);

        // 最后返回所有符合要求的数据
        return result;
    }

    // rowNum: 代表处理当前行
    // queenCount: 代表有多少个皇后,也代表棋盘大小
    // queenArray 记录皇后的位置
    // attackArray 皇后攻击范围
    private void backtracking(int k, int n, char[][] queenArray, int[][] attackArray) {
        // 代表处理的是最后一个皇后,找一个合适的解
        if (k == n) {
            List<String> someOneSolution = new ArrayList<>();
            // queenArray 转为 字符串
            for (char[] c : queenArray) {
                someOneSolution.add(String.copyValueOf(c));
            }
            // 保存一个可行解
            result.add(someOneSolution);
            // 由于遍历完了所有行,直接返回
            return;
        }

        for (int i = 0; i < n; i++) {
            // 如果发现 attackArray[row][queenNum]=0,说明不在皇后的攻击范围内
            if (attackArray[k][i] == 0) {
                // 如果在 ( k , i ) 位置放置了皇后，那么就需要考虑在 k + 1 行应该怎么放置其它的皇后了
                // 由于有可能在( k , i )  位置放置了皇后之后，在后续的其它行会无法再放置其它的皇后
                // 那么就需要回到 ( k , i )  的状态，考虑能不能在 ( k , i + 1 )的位置放置
                // 为了能够回到 ( k , i )  的状态，所以需要先记录此时的 attack
                // 使用一个临时的二维数组，深度拷贝 attack
                // 如果不使用深度拷贝，而是直接使用 int[][] temp = c
                // 会导致 attack 发生改变是 temp 也会发生改变
                // 这样也就无法保存之前的状态了

                // 设置临时变量保存 数组状态
                int[][] temp = new int[n][n];
                for (int l = 0; l < n; l++) {
                    for (int m = 0; m < n; m++) {
                        temp[l][m] = attackArray[l][m];
                    }
                }

                // 用queenArray来记录皇后的位置
                queenArray[k][i] = 'Q';

                // 由于新放置了一个皇后，所以攻击范围又更多了
                // 所以需要更新 attack 数组
                // 新放置皇后的坐标为 ( k , i ) ，同样的需要更新它的八个方向
                checkQueueAttack(k, i, attackArray);

                // 如果在第 k行, 第i列放了皇后,那么需要考虑在 k+1 行怎么放置皇后;
                // 递归调用 backtracking 函数
                backtracking(k + 1, n, queenArray, attackArray);

                // 递归结束后，拿走皇后，恢复 attackArray 的状态，考虑能不能在 ( k ,i + 1 )的位置放置
                attackArray = temp;

                // 恢复queen的状态
                queenArray[k][i] = '.';
            }
        }
    }

    // 坐标 ( x , y) 为皇后所处的位置
    // 更新 attack
    public void checkQueueAttack(int x, int y, int[][] attackArray) {
        // 对于每一个坐标 (x,y) 来说，都有上、下、左、右、左上、左下、右上、右下 八个方向
        //【左上】的坐标为 (x - 1, y - 1)
        //【上】的坐标为 (x - 1, y )
        //【右上】的坐标为 (x - 1, y + 1)
        //【左】的坐标为 (x, y + 1)
        //【右】的坐标为 (x , y - 1)
        //【左下】的坐标为 (x + 1, y - 1)
        //【下】的坐标为 (x + 1, y)
        //【右下】的坐标为 (x + 1, y + 1)
        // 通过两个一维数组可以表示这八个方向
        int dx[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        // dy 表示 y 的方向
        int dy[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        // 皇后所处的坐标肯定是皇后能攻击的位置，设置为 1
        attackArray[x][y] = 1;

        // j表示8个方向. 以(x,y)为中心的8个方向
        for (int j = 0; j < 8; j++) {

            // i表示由内向外更新. i表示第几圈，或者第几层.
            for (int i = 1; i < attackArray.length; i++) {
                // 通用的公式: dx, dy的具体值, 由 i * dx[j] 来决定
                // 如果 x=1, y=1: 左上: [x + (1 * -1), y + (1 * -1)] = [x-1,y-1]
                int tempX = x + i * dx[j];
                int tempY = y + i * dy[j];
                // 横坐标，纵坐标 大于等于0，并且 小于数组长度, 把该位置值设置为1
                if (tempX >= 0 && tempX < attackArray.length && tempY >= 0 && tempY < attackArray.length) {
                    attackArray[tempX][tempY] = 1;
                }
            }
        }
    }
}
