package com.gy.algorithm.basic.sort;

/**
 * @ClassName SelectionSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-05 16:42
 */
public class SelectionSortTest {
	public static void main(String[] args) {
		System.out.println("SelectionSortTest-------------------------");
		selectionSort();
	}

	public static void selectionSort() {
		int[] unsorted = { 3, 5, 1, 13, 17, 11, 9, 88, 105, 7 };
		//
		for (int out = 0; out < unsorted.length - 1; out++) {
			System.out.println(" out -> " + out);
			// 取出每一个元素, 设置为临时最小(大)值.
			int minTmp = unsorted[out];
			for (int in = out + 1; in < unsorted.length; in++) {
				// 遇到比 minTmp 小的, 就交换位置, minTmp 始终保持最小值
				if (minTmp > unsorted[in]) {
					int t = minTmp;
					minTmp = unsorted[in];
					unsorted[in] = t;
				}
			}
			// 将最小值, 设置为 out 位置上的元素.
			unsorted[out] = minTmp;
		}
		display(unsorted);
	}

	public static void display(int[] a) {
		for (int i : a) {
			if (i > 0)
				System.out.println(i);
		}
	}
}
