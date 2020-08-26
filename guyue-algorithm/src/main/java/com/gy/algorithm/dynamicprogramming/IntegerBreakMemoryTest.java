package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IntegerBreakMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 21:41
 */
public class IntegerBreakMemoryTest {

	private static List<Long> memory;

	public static long max3(long a, long b, long c) {
		return Math.max(a, Math.max(b, c));
	}

	/**
	 * 将 N 进行分隔(至少分隔成2部分), 可以获得最大乘积. 这是采用"自顶向下"的解题思路.
	 */
	public static long breakInteger(int n) {
		// 结束条件
		if (n == 1) {
			return 1;
		}

		// 记忆化搜索, 取值
		if (memory.get(n) != Long.MIN_VALUE) {
			return memory.get(n);
		}

		// 采用暴力求解的方式, 计算每个部分的值
		long res = -1;
		for (int i = 1; i <= n - 1; i++) {
			/**
			 *
			 * // 1 , n-1 = [i * (n - i)]
			 * // 2 , n-2 = [i * (n - i)]
			 * // 3 , n-3 = [i * (n - i)]
			 * // ...
			 * // n-1, 1  = [i * (n - i)]
			 * // 不断的迭代下去
			 *
			 * 由于标题说, 至少要把N 分隔可2部分。
			 * 当我们要分隔一个N时, i * (n -i), 也是一种分配, 我们也需要考虑进去.
			 * 换句话说, 对于 N 的第一次分隔,  当遇到这种分配时, 我们不再分隔 i * (n - i), 求解这个值有多大.
			 */
			res = max3(res, i * (n - i), i * breakInteger(n - i));
		}

		// 记忆化搜索, 赋值
		memory.set(n, res);
		return res;
	}

	public static long integerBreak(int n) {
		// 记忆化搜索
		memory = new ArrayList<>(n + 1);
		for (int index = 0; index < (n + 1); index++) {
			memory.add(index, Long.MIN_VALUE);
		}

		return breakInteger(n);
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
