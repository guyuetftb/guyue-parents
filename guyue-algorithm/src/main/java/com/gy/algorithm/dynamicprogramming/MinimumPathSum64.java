package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MinimumTriangle120
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-11 11:45
 */
public class MinimumPathSum64 {

	public static void main(String[] args) {

		/**
		 * [
		 *   [1,3,1],
		 *   [1,5,1],
		 *   [4,2,1]
		 * ]
		 */
		List<List<Integer>> initData = new ArrayList<List<Integer>>();
		initData(initData);

		List<List<Integer>> result = minimumPathSum(initData);

		System.out.println(" 初始形状是: ");
		for (int i = 0; i < initData.size(); i++) {
			List<Integer> row = initData.get(i);
			for (int j = 0; j < row.size(); j++) {
				System.out.printf(" %d ", row.get(j));
			}
			System.out.println();
		}

		System.out.println(" 结果形状是: ");
		for (int i = 0; i < result.size(); i++) {
			List<Integer> row = result.get(i);
			for (int j = 0; j < row.size(); j++) {
				System.out.printf(" %d ", row.get(j));
			}
			System.out.println();
		}

		List<Integer> lastRow = result.get(result.size() - 1);
		System.out.printf(" 最小值是 %d.", lastRow.get(lastRow.size() - 1));
	}

	public static List<List<Integer>> minimumPathSum(List<List<Integer>> matrix) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		result.add(0, new ArrayList<>(1));
		result.add(1, new ArrayList<>(2));
		result.add(2, new ArrayList<>(3));

		// TODO 初始化第[0][0]位置的最小路径值 等于 自身.
		result.get(0).add(0, matrix.get(0).get(0));

		// TODO 第一行数据的最小值, 只能由 '左侧元素' + '自身' 得到.
		List<Integer> resultRow1 = result.get(0);
		List<Integer> matrixRow1 = matrix.get(0);
		for (int i = 1; i < matrixRow1.size(); i++) {
			resultRow1.add(i, resultRow1.get(i - 1) + matrixRow1.get(i));
		}

		for (int i = 1; i < matrix.size(); i++) {
			List<Integer> curtMatrixRow = matrix.get(i);
			List<Integer> curtResultRow = result.get(i);
			List<Integer> preResultRow  = result.get(i - 1);

			for (int j = 0; j < curtMatrixRow.size(); j++) {
				if (j == 0) {
					// TODO 第1列的元素, 只能由上一行第1列元素得到.
					curtResultRow.add(0, curtMatrixRow.get(0));
				} else {
					// TODO 上一行, 本列元素.
					Integer ele1 = preResultRow.get(j) + curtMatrixRow.get(j);
					// TODO 本行, 上一列元素.
					Integer ele2 = curtResultRow.get(j - 1) + curtMatrixRow.get(j);
					curtResultRow.add(j, Math.min(ele1, ele2));
				}
			}
		}

		return result;
	}

	public static void initData(List<List<Integer>> matrix) {
		matrix.add(new ArrayList<>(1));
		matrix.add(new ArrayList<>(2));
		matrix.add(new ArrayList<>(3));

		// row 1
		matrix.get(0).add(1);
		matrix.get(0).add(3);
		matrix.get(0).add(1);

		// row 2
		matrix.get(1).add(1);
		matrix.get(1).add(5);
		matrix.get(1).add(1);

		// row 3
		matrix.get(2).add(4);
		matrix.get(2).add(2);
		matrix.get(2).add(1);
	}

}
