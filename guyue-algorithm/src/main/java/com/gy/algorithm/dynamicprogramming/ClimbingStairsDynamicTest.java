package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClimbingStairsMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 18:01
 */
public class ClimbingStairsDynamicTest {

	private static List<Long> comblingStairs;

	public static long comblingStairs(int stairN) {

		comblingStairs = new ArrayList<>(stairN + 1);
		for (int i = 0; i < (stairN + 1); i++) {
			// 对记忆代码赋值
			comblingStairs.add(i, Long.MIN_VALUE);
		}

		comblingStairs.set(0, 0L);
		comblingStairs.set(1, 1L);

		for (int index = 2; index <= stairN; index++) {
			comblingStairs.add(index, (comblingStairs.get(index - 1) + comblingStairs.get(index - 2)));
		}

		// 递归调用 stairN - 1 和 stairN - 2，
		return comblingStairs.get(stairN);
	}
}
