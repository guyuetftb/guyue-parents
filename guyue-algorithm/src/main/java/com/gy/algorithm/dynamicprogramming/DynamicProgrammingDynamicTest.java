package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DynamicProgrammingTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 15:40
 */
public class DynamicProgrammingDynamicTest {

	private static int runNum = 0;

	private static List<Long> memory;

	public static long fib(int a) {

		memory = new ArrayList<>(a + 1);

		memory.add(0, 0L);
		memory.add(1, 1L);
		for (int index = 2; index <= a; index++) {
			memory.add(index, memory.get(index - 1) + memory.get(index - 2));
		}

		return memory.get(a);
	}

	public static void main(String[] args) {

		int[] arr = new int[]{10, 20, 40, 41, 42, 1000};
		for (int index = 0; index < arr.length; index++) {
			int startNum = arr[index];
			long startTime = System.nanoTime();
			long res = fib(startNum);
			long endTime = System.nanoTime();
			double nanoTime = endTime - startTime;

			System.out.println(
				" fib(" + startNum + ") = " + res + ", run_num = " + runNum + ", nanoTime=" + nanoTime + ", time=" + (nanoTime / 1000000000D));
		}
	}
}
