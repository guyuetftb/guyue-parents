package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName DynamicProgrammingTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 15:40
 */
public class DynamicProgrammingMemoryTest {

	private static int runNum = 0;

	private static List<Integer> memory;

	public static int fib(Integer a) {
		runNum++;
		if (a == 0) {
			return a;
		}

		if (a == 1) {
			return 1;
		}

		if (memory.get(a) == Integer.MIN_VALUE) {
			memory.set(a, fib(a - 1) + fib(a - 2));
		}

		return memory.get(a);
	}

	public static void main(String[] args) {

		int[] arr = new int[]{10, 20, 40, 41, 42};
		for (int index = 0; index < arr.length; index++) {
			runNum = 0;
			int startNum = arr[index];
			// 初始化记忆代码
			memory = new ArrayList<>(startNum + 1);
			for (int i = 0; i < (startNum + 1); i++) {
				// 对记忆代码赋值
				memory.add(i, Integer.MIN_VALUE);
			}

			long startTime = System.nanoTime();
			int res = fib(startNum);
			long endTime = System.nanoTime();
			double nanoTime = endTime - startTime;

			System.out.println(
				" fib(" + startNum + ") = " + res + ", run_num = " + runNum + ", nanoTime=" + nanoTime + ", time=" + (nanoTime / 1000000000D));
		}
	}
}
