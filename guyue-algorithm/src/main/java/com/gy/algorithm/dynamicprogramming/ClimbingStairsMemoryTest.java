package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClimbingStairsMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 18:01
 */
public class ClimbingStairsMemoryTest {

	public static int comblingStairs(int stairN) {

		// 0阶, 没有解
		if (stairN == 0) {
			return 0;
		}

		// 1阶, 只有1个解, 爬1次1阶
		if (stairN == 1) {
			return 1;
		}

		// 2阶, 有2种迈台阶的方法
		if (stairN == 2) {
			return 2;
		}

		// 递归调用 stairN - 1 和 stairN - 2，
		return comblingStairs(stairN - 1) + comblingStairs(stairN - 2);
	}

	private static List<Long> memory;

	public static long comblingStairsAdvance(int stairN) {

		// 0阶, 没有解
		if (stairN == 0) {
			return 0;
		}

		// 1阶, 只有1个解, 爬1次1阶
		if (stairN == 1) {
			return 1;
		}

		// 2阶, 有2种迈台阶的方法
		if (stairN == 2) {
			return 2;
		}

		if(memory.get(stairN) == Integer.MIN_VALUE){
			memory.set(stairN, comblingStairsAdvance(stairN - 1) + comblingStairsAdvance(stairN - 2));
		}

		// 递归调用 stairN - 1 和 stairN - 2，
		return memory.get(stairN);
	}

	public static void main(String[] args) {

		int[] stairArr = new int[]{10, 20, 40, 41, 42};
		for (int index = 0; index < stairArr.length; index++) {
			int stairNum = stairArr[index];
			memory = new ArrayList<>(stairNum + 1);
			for (int i = 0; i < (stairNum + 1); i++) {
				// 对记忆代码赋值
				memory.add(i, Long.MIN_VALUE);
			}

			long startTime = System.nanoTime();
			int res = comblingStairs(stairNum);
			long endTime = System.nanoTime();
			double nanoTime = endTime - startTime;

			System.out.println(
				" fib(" + stairNum + ") = " + res + ", stair_num = " + stairNum + ", nanoTime=" + nanoTime + ", time=" + (nanoTime / 1000000000D));
		}
	}

}
