package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MinimumTriangle120
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-11 11:45
 */
public class MinimumTriangle120 {

	public static void main(String[] args) {

		/**
		 * [
		 * [2],
		 * [3,4],
		 * [6,5,7],
		 * [4,1,8,3]
		 * ]
		 */
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		initData(triangle);

		List<List<Integer>> result = minimumTriangle(triangle);
		int minVal = Integer.MAX_VALUE;
		for (int i = 0; i < result.get(result.size() - 1).size(); i++) {
			minVal = Math.min(minVal, result.get(result.size() - 1).get(i));
		}

		System.out.println(" 初始三解形是: ");
		for (int i = 0; i < triangle.size(); i++) {
			List<Integer> row = triangle.get(i);
			for (int j = 0; j < row.size(); j++) {
				System.out.printf(" %d ", row.get(j));
			}
			System.out.println();
		}

		System.out.println(" 结果三解形是: ");
		for (int i = 0; i < result.size(); i++) {
			List<Integer> row = result.get(i);
			for (int j = 0; j < row.size(); j++) {
				System.out.printf(" %d ", row.get(j));
			}
			System.out.println();
		}

		System.out.printf(" 最小值是 %d.", minVal);
	}

	public static List<List<Integer>> minimumTriangle(List<List<Integer>> triangle) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		result.add(0, new ArrayList<>(1));
		result.add(1, new ArrayList<>(2));
		result.add(2, new ArrayList<>(3));
		result.add(3, new ArrayList<>(4));

		// [0][0]
		result.get(0).add(triangle.get(0).get(0));

		// [1][0]
		result.get(1).add(result.get(0).get(0) + triangle.get(1).get(0));

		// [1][1]
		result.get(1).add(result.get(0).get(0) + triangle.get(1).get(1));

		for (int i = 2; i < triangle.size(); i++) {
			List<Integer> rowCur = triangle.get(i);
			List<Integer> rowResultCur = result.get(i);
			List<Integer> rowResultPre = result.get(i - 1);
			for (int j = 0; j < rowCur.size(); j++) {
				if (j == 0) {
					// TODO 第1个元素只能等于: 上一行第1个元素 + 本行第1个元素
					rowResultCur.add(0, rowResultPre.get(0) + rowCur.get(0));
				} else if (j == (rowCur.size() - 1)) {
					// TODO 第N个元素只能等于: 上一行第N-1个元素(上一行元素比本层少1个) + 本行第N个元素
					rowResultCur.add(j, rowResultPre.get(j - 1) + +rowCur.get(j));
				} else {
					Integer ele1 = rowResultPre.get(j) + rowCur.get(j);
					Integer ele2 = rowResultPre.get(j - 1) + rowCur.get(j);
					rowResultCur.add(j, Math.min(ele1, ele2));
				}
			}
		}

		return result;
	}

	public static void initData(List<List<Integer>> triangle) {
		triangle.add(new ArrayList<>(1));
		triangle.add(new ArrayList<>(2));
		triangle.add(new ArrayList<>(3));
		triangle.add(new ArrayList<>(4));
		// row 1
		triangle.get(0).add(2);

		// row 2
		triangle.get(1).add(3);
		triangle.get(1).add(4);

		// row 3
		triangle.get(2).add(5);
		triangle.get(2).add(6);
		triangle.get(2).add(7);

		// row 4
		triangle.get(3).add(4);
		triangle.get(3).add(1);
		triangle.get(3).add(8);
		triangle.get(3).add(3);
	}

}
