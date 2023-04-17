package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DifferentWaysTest62
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 17:03
 */
public class DifferentWaysTest63 {

	public static void main(String[] args) {
		int col = 7;
		int row = 3;
		int barrier14 = 1;
		int barrier32 = 1;

		// TODO 初始化 二维表格的值,
		//  	注: 1为正常, 0为障碍物.
		// 		因为图中多了障碍物, 障碍物后面的路径都是不通的, 把所有障碍物的值都设置为0.
		//		假设 row = 2, col = 4 有障碍物.
		//		假设 row = 3, col = 2 有障碍物.
		//		第一层 List 代表行数, 此处为3
		//		第二层 List 代表列数, 此外为7
		//		映射成二维数组是: int[3][7]
		//		1	2	3	4	5	6	7
		//		2	x	x	*	x	x	x
		//		3	*	y	y	y	y	y
		List<List<Integer>> statusList = new ArrayList<>(row);
		for (int i = 0; i < row; i++) {
			List<Integer> list = new ArrayList<Integer>();
			// TODO 把第一行, 第一列元素值设置为1, 因为顺着第一行, 第一列 只有一条路径可以走.
			int value = 1;
			for (int j = 0; j < col; j++) {
				if ((i == 0 && j == 3) || (i == 2 && j == 1)) {
					// TODO 初始化时, 也需要考虑障碍物, 把障碍的值设置为0.
					//		当遇到障碍物时, 包括障碍物在内 及 后面所有元素的值都要设置为0.
					value = 0;
				}
				list.add(j, value);
			}
			statusList.add(i, list);
		}

		// TODO 计算路径.
		//		第一行, 第一列的值初始化时已经得到,
		//		下面计算都从第2行, 第2列开始.
		for (int i = 1; i < row; i++) {
			// TODO preList 代表上一行.
			List<Integer> preList = statusList.get(i - 1);
			// TODO currentList 代表本行.
			List<Integer> currentList = statusList.get(i);
			for (int j = 1; j < col; j++) {

				// TODO 以 DP[2][2] 为例:
				//		preList(j) = DP[1][2]
				//		currentList(j-1) = DP[2][1]
				//		DP[2][2] = DP[1][2] + DP[2][1] = preList(j) + currentList(j-1)
				int value = -1;
				// TODO 如果当前行上有 障碍物, 那后期所有值都为0.
				if (currentList.get(j) == 0) {
					value = 0;
				} else {
					value = preList.get(j) + currentList.get(j - 1);
				}
				currentList.set(j, value);
			}
		}

		// TODO  1行4列有障碍物, 3行2列有障碍物, 计算完后, 表格的路径图如下:
		//		1	1	1	*	0	0	0
		//		1	2	3	3	3	3	3
		//		1	*	0	0	0	0	3
		System.out.printf("row = %d, col = %d, 到达右下角一共有: %d 种路径.", row, col, statusList.get(row - 2).get(col - 1) + statusList.get(row - 1).get(col - 2));

	}

}
