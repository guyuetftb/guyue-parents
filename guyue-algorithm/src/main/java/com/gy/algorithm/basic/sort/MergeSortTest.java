package com.gy.algorithm.basic.sort;

import java.util.Arrays;

/**
 * @ClassName MergeSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-08 12:35
 */
public class MergeSortTest {

	public static void main(String[] args) {
		System.out.println("Merge Sort -------------------------");
		int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
		int[] sorted = new int[unsorted.length];
		mergeSort(unsorted, 0, unsorted.length, sorted, 1);
		display(unsorted);

		System.out.println("Merge Sort V2 -------------------------");
		int[] unsortedV2 = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
		display(mergeSortV2(unsortedV2));
	}

	public static int[] mergeSortV2(int[] array) {
		if (array.length < 2) {
			return array;
		}

		int mid = array.length / 2;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, array.length);
		return mergeV2(mergeSortV2(left), mergeSortV2(right));
	}

	public static int[] mergeV2(int[] left, int[] right) {
		int[] result = new int[left.length + right.length];
		int leftIndex = 0;
		int rightIndex = 0;
		for (int resultIndex = 0; resultIndex < result.length; resultIndex++) {
			if (leftIndex >= left.length) {
				// left 部分已经没有元素了, 把 right 部分元素拷贝到 result
				result[resultIndex] = right[rightIndex];
				rightIndex++;
			} else if (rightIndex >= right.length) {
				// right 部分已经没有元素了, 把 left 部分元素拷贝到 result
				result[resultIndex] = left[leftIndex];
				leftIndex++;
			} else if (left[leftIndex] > right[rightIndex]) {
				// left 元素大于 right 元素, 拷贝到 result.
				result[resultIndex] = right[rightIndex];
				// right 指针后移.
				rightIndex++;
			} else {
				// right 元素大于 left 元素, 拷贝到 result.
				result[resultIndex] = left[leftIndex];
				// left 指针后移.
				leftIndex++;
			}
		}
		return result;
	}


	static void mergeSort(int[] unsorted, int first, int last, int[] sorted, int step) {
		StringBuilder stepStr = new StringBuilder();
		for (int i = 0; i < step; i++) {
			stepStr.append("-");
		}
		System.out.printf("%s first=[%d], last=[%d]\n", stepStr.toString(), first, last);

		/**
		 * 1. 把长度为 n 的 array 分隔为 2 部分
		 * 2. 采用归并排序, 排序 0-(n/2)
		 * 3. 采用归并排序, (n/2)-n
		 * 3. 采用归半排序, 排序left, right 部分
		 */
		if (first + 1 < last) {
			int mid = (first + last) / 2;
			System.out.printf("%s f=[%d], m=[%d], l=[%d] \n", stepStr.toString(), first, mid, last);

			// 不断的对 左半部分 排序.
			mergeSort(unsorted, first, mid, sorted, step + 1);

			// 不断的对 右半部分 排序.
			mergeSort(unsorted, mid, last, sorted, step + 1);

			// left, right 都排好序, 或 不能再细分, 对  left, right 两部分排序.
			merge(unsorted, first, mid, last, sorted);
		}
	}

	static void merge(int[] unsorted, int first, int mid, int last, int[] sorted) {

		int low = first;
		int high = mid;
		int tmp = 0;
		while (low < mid && high < last) {
			// 把大的元素, 放到新空间中.
			if (unsorted[low] < unsorted[high]) {
				sorted[tmp++] = unsorted[low++];
			} else {
				sorted[tmp++] = unsorted[high++];
			}
		}

		while (low < mid) {
			sorted[tmp++] = unsorted[low++];
		}

		while (high < last) {
			sorted[tmp++] = unsorted[high++];
		}

		for (int t = 0; t < tmp; t++) {
			unsorted[first + t] = sorted[t];
		}
	}

	public static void display(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
