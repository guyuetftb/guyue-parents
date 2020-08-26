package com.gy.algorithm.dynamicprogramming;

/**
 * @ClassName DynamicProgrammingTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-20 15:40
 */
public class DynamicProgrammingTest {

	private static int runNum = 0;

	public static int fib(Integer a) {
		runNum++;
		if (a == 0) {
			return a;
		}

		if (a == 1) {
			return 1;
		}

		return fib(a - 1) + fib(a - 2);


	}

	public static void main(String[] args) {

		int[] arr = new int[]{10, 20, 40, 41, 42};
		for (int index = 0; index < arr.length; index++) {
			runNum = 0;
			int startNum = arr[index];
			long startTime = System.nanoTime();
			int res = fib(startNum);
			long endTime = System.nanoTime();
			double nanoTime = endTime - startTime;

			System.out.println(
				" fib(" + startNum + ") = " + res + ", run_num = " + runNum + ", nanoTime=" + nanoTime + ", time=" + (nanoTime / 1000000000D));
		}

		//  fib(10) = 55, time = 216002
		//  fib(20) = 6765, time = 1399115
	}
}
