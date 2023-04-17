package com.gy.algorithm.basic.sort;

/**
 * @ClassName ShellSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-05 17:14
 */
public class ShellSortTest {

	public static void main(String[] args) {
		System.out.println("ShellSortTest-------------------------");
		int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
		shellSort(unsorted);
		display(unsorted);
	}

	static void shellSort(int[] unsorted) {
		// 取增量gap = arr.length / 2
		int gap = unsorted.length / 2;

		while (gap >= 1) {

			// 无须序列
			System.out.println("gap = " + gap);
			// 将 array 分为多少个gap, 有多少个 gap, 就代表 array 要切分成几个 sub-array
			// 外循环控制着多少个 sub-array
			for (int outGapIndex = gap; outGapIndex < unsorted.length; outGapIndex++) {

				int temp = unsorted[outGapIndex];

				// outGapIndex - gap, 就代表增量为 gap时, 与之对应的元素的索引.
				int inArrIndex = outGapIndex - gap;
				System.out.println(
					"\t outGapIndex=" + outGapIndex + ", unsorted[outGapIndex]=" + temp +
						", inArrIndex=" + inArrIndex + ", unsorted[inArrIndex]=" + unsorted[inArrIndex]);

				// 有序序列, 对每个序列做 插入排序
				for (; inArrIndex >= 0 && temp < unsorted[inArrIndex]; inArrIndex = inArrIndex - gap) {
					System.out.println("\t\t inArrIndex + gap=" + (inArrIndex + gap) + ", inArrIndex=" + inArrIndex);
					unsorted[inArrIndex + gap] = unsorted[inArrIndex];
				}
				unsorted[inArrIndex + gap] = temp;
			}
			gap = gap / 2;
		}
	}

	public static void display(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
