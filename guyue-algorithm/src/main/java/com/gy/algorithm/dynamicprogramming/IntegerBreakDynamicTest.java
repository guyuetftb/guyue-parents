package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IntegerBreakMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 21:41
 */
public class IntegerBreakDynamicTest {

	private static List<Long> memory;

	public static long max3(long a, long b, long c) {
		return Math.max(a, Math.max(b, c));
	}

	public static long integerBreak(int n) {
		// 记忆化搜索
		memory = new ArrayList<>(n + 1);
		for (int index = 0; index < (n + 1); index++) {
			memory.add(index, Long.MIN_VALUE);
		}

		// 初始化, 1等于1.
		memory.add(1, 1L);

		/**
		 * 假如 n = 4, 4就被分隔成:
		 * i = 1, n - i = 3
		 * i = 2, n - i = 2
		 * i = 3, n - i = 1
		 * i = 1, n - i = 3
		 *
		 * 为了求最后的解, 还需要对 i 部分, 继续求解
		 */
		for (int i = 2; i <= n; i++) {

			// 求解 memory(i)
			for (int j = 1; j <= i - 1; j++) {
				// 求 j * (i - j)
				// 一种情况是把 j * (i - j), 另一种情况是把数字j继续分隔.
				// memory.get(i - j) 一定是小于 I 的, 所以 memory(i - j)的值一定已经被计算出来了.
				memory.set(i, max3(memory.get(i), j * (i - j), j * memory.get(i - j)));
			}
		}

		return memory.get(n);
	}

	public static void main(String[] args) {
		int[] arr = new int[]{10, 20, 40, 41, 42};
		for (int index = 0; index < arr.length; index++) {

			int num = arr[index];
			long startTime = System.nanoTime();
			long res = integerBreak(num);
			long endTime = System.nanoTime();
			double nanoTime = endTime - startTime;

			System.out.println(" integerBreak(" + num + ") = " + res + ", nanoTime=" + nanoTime + ", time=" + (nanoTime / 1000000000D));
		}
	}

}
