package com.gy.algorithm.basic.array;


/**
 * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
 * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。
 * 每个细胞都具有一个初始状态：
 * 1: 活细胞 （live）
 * 0: 死细胞 （dead）
 * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
 *
 * <p>
 * <p>
 * 1. 如果 活细胞 周围八个位置的 "活细胞数" < 2，则该位置活细胞死亡；
 * 2. 如果 活细胞 周围八个位置有 "活细胞" = 3 或 "活细胞" = 2，则该位置活细胞仍然存活；
 * 3. 如果 活细胞 周围八个位置有 "活细胞" > 3，则该位置活细胞死亡；
 * 4. 如果 死细胞 周围 "活细胞" = 3，则该位置死细胞复活；
 * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
 * 给你 m x n 网格面板 board 的当前状态，返回下一个状态。
 */
public class LC289GameOfLife {

    public static void main(String[] args) {
        int[][] array = new int[][]{{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        LC289GameOfLife lc289GameOfLife = new LC289GameOfLife();
        lc289GameOfLife.gameOfLife(array);
    }

    public void gameOfLife(int[][] board) {

        gameOfLifeGY(board);
    }

    public void gameOfLifeGY(int[][] board) {
        /**
         * 如果当前位置坐标为: 0,0，其周围X,Y相对坐标为:
         * (-1,-1),(-1,0),(-1,1)
         * (0,-1),(0,0,),(0,1)
         * (1,-1),(1,0),(1,1)
         */
        //如果当前位置是0,0
        // 加上x,y的相对位置,在矩阵中对应的位置是:
        // 右, 左, 右下, 左下, 下, 右上, 左上, 上
        int[] x = {0, 0, 1, 1, 1, -1, -1, -1};
        int[] y = {1, -1, 1, -1, 0, 1, -1, 0};

        // 技巧1: 矩阵的状态会发生变化, 需要2次遍历矩阵
        for (int rowIdx = 0; rowIdx < board.length; rowIdx++) {
            for (int colIdx = 0; colIdx < board[0].length; colIdx++) {

                int liveNum = 0;
                int curX = 0;
                int curY = 0;
                // 技巧2: 矩阵中某位置元素值变化会影响周边元素的值
                for (int i = 0; i < 8; i++) {
                    curX = rowIdx + x[i];
                    curY = colIdx + y[i];

                    if (curX < 0 || curX >= board.length || curY < 0 || curY >= board[0].length) {
                        // 进到这里是因为: 在计算某个矩阵元素周边元素时, 下标越界了
                        continue;
                    }

                    //计算周存活细胞个数
                    if (board[curX][curY] == 1 || board[curX][curY] == 2) {
                        liveNum++;
                    }
                }

                // 已经知道了周围细胞情况,根据真实情况,把当前细胞位置标记成中间值
                if(board[rowIdx][colIdx] == 0){
                    if(liveNum == 3){
                        //用-1记录从 死变活的状态
                        board[rowIdx][colIdx] = -1;
                    }
                } else {
                    if(liveNum < 2 || liveNum > 3){
                        //用2记录从 活变死的状态
                        board[rowIdx][colIdx] = 2;
                    }
                }
            }
        }

        // 对应技巧1，第二次遍历，把中间值-1和2刷新为1和0
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == -1) {
                    board[i][j] = 1;
                }
            }
        }
    }

    /**
     * 总结：类似的矩阵问题有很多，但不管什么问题，有两个技巧是一定用的上的，一定要记住！！！
     * <p>
     * 1、矩阵中某个位置的状态如果发生改变，那么这种题的解法一般是: 两次遍历整个矩阵。
     * 1-1: 第一遍遍历时，用一个不可能出现在原矩阵中的中间值来保存状态的变化（这样再次遍历时，不影响其他的位置的判断，比如我们可以用“$”这种没人用的字符）；
     * 1-2: 第二遍遍历时，把中间值刷新成为变化后应该变成的值。
     * <p>
     * 2、如果遍历到某个位置时，需要查看它周边的位置，此时如果每一个周围的位置都手写，然后再判断是否越界，就很麻烦。
     * 可以先用一个数组保存向周边位置变化的坐标偏移值，一次性通过一个循环，来遍历完周边的位置，并且方便进行越界判断。
     * <p>
     * 分析本题：
     * 本题就是上面两个技巧的典型用例，因为所有的格子同时刷新变化，所以，你需要两次遍历：
     * 1. 第一次遍历时，判断下一次刷新后的状态应该是啥，你要记下变化，但你又不能影响本次正在进行的遍历，所以你可以用一个中间值来记录。
     * 2. 第二次遍历时，刷新中间值。
     * <p>
     * 作者：freshrookie
     * 链接：https://leetcode.cn/problems/game-of-life/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public void gameOfLife1(int[][] board) {
        // 对应技巧2，用一个长度为8的数组来保存遍历每一个位置时，其周边位置的相对坐标偏移
        /**
         * 如果当前位置坐标为: 0,0，其周围X,Y相对坐标为:
         * (-1,-1),(-1,0),(-1,1)
         * (0,-1),(0,0,),(0,1)
         * (1,-1),(1,0),(1,1)
         */
        //如果当前位置是0,0
        // 加上x,y的相对位置,在矩阵中对应的位置是:
        // 右, 左, 右下, 左下, 下, 右上, 左上, 上
        int[] x = {0, 0, 1, 1, 1, -1, -1, -1};
        int[] y = {1, -1, 1, -1, 0, 1, -1, 0};

        // 对应技巧1，第一次遍历，把需要变化状态的位置保存为中间值
        for (int rowIdx = 0; rowIdx < board.length; rowIdx++) {
            for (int colIdx = 0; colIdx < board[0].length; colIdx++) {
                int curX, curY;
                int live = 0;
                // 对应技巧2，通过之前保存的相对坐标偏移的数组，方便的遍历所有的周边位置。
                for (int k = 0; k < 8; k++) {
                    curX = rowIdx + x[k];
                    curY = colIdx + y[k];
                    if (curX < 0 || curX >= board.length || curY < 0 || curY >= board[0].length) {
                        //程序进到这里,说明数组越界了, 说明出来了不存在的: 表格位置, 继续遍历周边其他表格
                        continue;
                    }
                    // 对应技巧1, 这里的0,1是题目里合理的值，然后:
                    // 如果0要变1，我们用中间值-1记录，
                    // 如果1要变0，我们用中间值2来记录。
                    if (board[curX][curY] == 1 || board[curX][curY] == 2) {
                        live++;
                    }
                }
                if (board[rowIdx][colIdx] == 0) {
                    if (live == 3) {
                        board[rowIdx][colIdx] = -1;
                    }
                } else {
                    if (live < 2 || live > 3) {
                        board[rowIdx][colIdx] = 2;
                    }
                }
            }
        }

        // 对应技巧1，第二次遍历，把中间值-1和2刷新为1和0
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == -1) {
                    board[i][j] = 1;
                }
            }
        }
    }
}
