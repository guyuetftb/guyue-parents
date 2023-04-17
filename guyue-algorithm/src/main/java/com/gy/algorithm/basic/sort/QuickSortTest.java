package com.gy.algorithm.basic.sort;

/**
 * @ClassName QuickSortTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-08 15:36
 */
public class QuickSortTest {


	public static void main(String[] args) {
		System.out.println("QuickSort -------------------------");
		int[] unsorted = {3, 5, 1, 13, 17, 11, 9, 88, 105, 7};
		// 注意: high 索引 位置不能大于 数组长度.
		quickSort(unsorted, 0, unsorted.length - 1);
		display(unsorted);
	}

	public static void quickSort(int[] unsorted, int low, int high) {
		if (low < high) {
			// low = 0, 会确定一个初始位置 pivotLocation, 这个位置left 部分的元素都比 pivot 小, right 部分元素都比 pivot 大。
			// 注意: 首次快排, 及后序每次分区快排, 都会使用该代码查找 pivot位置; 因素坐标不能设置成0.
			int pivotLocation = partition(unsorted, low, high);

			// 从 low 至 pivotLocation -1 进行快排.
			quickSort(unsorted, low, pivotLocation - 1);

			// 从 pivotLocation 至 high 进行快排.
			quickSort(unsorted, pivotLocation + 1, high);
		}
	}

	public static int partition(int[] unsorted, int low, int high) {
		// low = 0
		// 把 每个分区 第0个元素当做 pivot.
		int pivot = unsorted[low];
		while (low < high) {
			// 从高 high 到低 low 搜索
			// low = 0
			// 从高到低寻找比 pivot 大的元素
			while (low < high && unsorted[high] > pivot) {
				high--;
			}
			//	low = 0
			// 找到后放到 基准pivot 元素在 unsorted 中的位置.
			// 因为 pivot 已经缓存, 所以不会 覆盖.
			unsorted[low] = unsorted[high];

			// 从低 low 到高 high 搜索
			// low = 0
			// 从低到高搜索比 pivot 小的元素
			while (low < high && unsorted[low] <= pivot) {
				low++;
			}
			// low = 0
			// 找到后放到 unsorted[high] 中的位置. 因为 unsorted[high] 元素已经 赋值给 unsorted[low], 所以不会覆盖.
			unsorted[high] = unsorted[low];

			// low = 0
			// 至此已经实现了一次交换, 基于 pivot, 再进行交换
			// 一直到数组中所有比 pivot 小元素放到左边, 比 pivot 元素放到右边
		}

		// 最终确定 pivot 的最位置.
		unsorted[low] = pivot;
		return low;
	}

	public static void display(int[] a) {
		for (int i : a) {
			if (i > 0) {
				System.out.println(i);
			}
		}
	}

}
