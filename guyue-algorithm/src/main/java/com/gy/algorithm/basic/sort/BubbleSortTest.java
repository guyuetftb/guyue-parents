package com.gy.algorithm.basic.sort;

/**
 * @ClassName BubbleSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-05 16:03
 */
public class BubbleSortTest {

	public static void main(String[] args) {
		System.out.println("BubbleSortTest-------------------------");
		int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
		// 排序
		bubbleSort(unsorted);
		// 显示
		display(unsorted);
	}

	static void bubbleSort(int[] unsorted) {
		// 执行 array.length 次循环
		for (int outIndex = 0; outIndex < unsorted.length; outIndex++) {
			// TODO 注意
			//  	1. 循环终止条件, 可能会数组越界, 所以需要 '减1操作'
			// 		2. 同时, 经过一次冒泡, 最大的元素肯定会被 '上升'到最后, 所以可以不断递减 外'循环次数'.
			for (int inIndex = 0; inIndex < unsorted.length - 1 - outIndex; inIndex++) {
				// 把 后面[inIndex+1]的元素, 换到前面[inIndex]
				if (unsorted[inIndex + 1] < unsorted[inIndex]) {
					int tmp = unsorted[inIndex + 1];
					unsorted[inIndex + 1] = unsorted[inIndex];
					unsorted[inIndex] = tmp;
				}
			}
		}
	}

	public static void display(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
