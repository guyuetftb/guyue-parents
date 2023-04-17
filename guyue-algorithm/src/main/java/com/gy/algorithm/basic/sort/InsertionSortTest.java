package com.gy.algorithm.basic.sort;

/**
 * @ClassName InsertionSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-05 16:49
 */
public class InsertionSortTest {

	public static void main(String[] args) {
		System.out.println("InsertionSortTest-------------------------");
		insertionSort();
	}


	public static void insertionSort() {
		int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};

		// 1. 外层循环从1开始
		// 2. 只有
		for (int out = 1; out < unsorted.length; out++) {

			// 暂存 after 元素
			int current = unsorted[out];

			int in = out;
			// 在'已经排序拓队列'中 由后 向前 扫描.
			// 如果 before 元素 大于 after 元素, 就把 before 元素逐步后移, 直到不大于 current 元素时
			// current 就找到了'当下'合适的位置.
			while (in > 0 && unsorted[in - 1] > current) {
				unsorted[in] = unsorted[in - 1];
				in--;
			}
			unsorted[in] = current;
		}

		display(unsorted);
	}

	public static void display(int[] a) {
		for (int i : a) {
			if (i > 0) {
				System.out.println(i);
			}
		}
	}
}
