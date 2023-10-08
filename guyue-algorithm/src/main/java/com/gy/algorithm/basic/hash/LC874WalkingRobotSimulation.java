package com.gy.algorithm.basic.hash;

import java.util.HashSet;

public class LC874WalkingRobotSimulation {

    public static void main(String[] args) {

    }

    public int robotSim(int[] commands, int[][] obstacles) {

        // 定义结果
        int result = 0;

        // 定义x, y坐标
        int x = 0;
        int y = 0;

        // 行进方向
        // 初始方向：正北
        // 0 表示 向北
        // 1 表示 向东
        // 2 表示 向南
        // 3 表示 向西
        int direction = 0;

        // 定义四个方向
        int[][] direXY = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        // 定义障碍物坐标点
        HashSet<String> obstaclesSet = new HashSet<String>();
        // 把障碍物的坐标转化为 字符串 形式加入到 HashSet中
        for (int[] obs : obstacles) {
            obstaclesSet.add(obs[0] + "," + obs[1]);
        }

        /**
         * -2 ：向左转 90 度
         * -1 ：向右转 90 度
         * 1 <= x <= 9 ：向前移动 x 个单位长度
         */
        for (int command : commands) {
            // 向 左转动90度
            if (command == -2) {
                // 1、当前方向为正北方向，即 direction == 0 ，向左转 90 度
                // 来到 向西 方向，即 direction 需要更新为 3
                // 2、当前方向【不是】正北方向，即 direction ！= 0 ，向左转 90 度
                // 西 -> 南，即 3 -> 2
                // 南 -> 东，即 2 -> 1
                // 东 -> 北，即 1 -> 0
                // 即 direction 需要更新为 direction - 1
                direction = direction == 0 ? 3 : direction - 1;
            }
            // 向 右转动90度
            else if (command == -1) {
                // 1、当前方向为正西方向，即 direction == 3 ，向右转 90 度
                // 来到 向北 方向，即 direction 需要更新为 0
                // 2、当前方向【不是】正西方向，即 direction ！= 3 ，向右转 90 度
                // 北 -> 东，即 0 -> 1
                // 东 -> 南，即 1 -> 2
                // 南 -> 西，即 2 -> 3
                // 即 direction 需要更新为 direction + 1
                direction = direction == 3 ? 0 : direction + 1;
            }
            // 向前走 N 步
            else {

                // 命令等于正数, 往前走N步.
                // 每走1步, command 减1
                // 同时: 前一方向上不存在 障碍物
                while (command-- > 0 && !obstaclesSet.contains((x + direXY[direction][0]) + "," + (y + direXY[direction][1]))) {
                    x += direXY[direction][0];
                    y += direXY[direction][1];
                }
                result = Math.max(result, x * x + y * y);
            }
        }

        return result;
    }


}
