package com.gy.algorithm.basic.array;

/**
 * @ClassName ArrayMergeTwoArrIntoOneArrTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-27 17:14
 */
public class ArrayMergeTwoArrIntoOneArrTest {

	public static void main(String[] args) {

		int[] arr1 = new int[]{2, 3, 19, 20, 90, 0, 0, 0, 0, 0};
		int[] arr2 = new int[]{1, 2, 11, 15, 17};

		merge(arr1, 5, arr2, 5);

		for (int index = 0; index < arr1.length; index++) {
			System.out.println(arr1[index]);
		}
	}

	//归并排序的merge过程
	public static void merge(int[] nums1, int m, int[] nums2, int n) {
		int arr1Index = m - 1;
		int arr2Index = n - 1;
		int arrFinalIndex = m + n - 1;

		// TODO 整个处理过程是 倒序的, 先把最大的元素放到最后,依次类推
		while (arrFinalIndex >= 0) {
			//前两个判断要放在前面，防止空指针异常
			if (arr1Index < 0) {
				// TODO arr1 已经迭代完了, 只处理 arr2
				nums1[arrFinalIndex--] = nums2[arr2Index--];
			} else if (arr2Index < 0) {
				// TODO arr2 已经迭代完了, 只处理 arr1
				nums1[arrFinalIndex--] = nums1[arr1Index--];
			} else if (nums1[arr1Index] > nums2[arr2Index] && arr1Index >= 0) {
				// TODO arr1 元素 > arr2 元素, 处理 arr1元素
				nums1[arrFinalIndex--] = nums1[arr1Index--];
			} else if (nums1[arr1Index] <= nums2[arr2Index] && arr2Index >= 0) {
				// TODO arr2 元素 > arr1 元素, 处理 arr2元素
				nums1[arrFinalIndex--] = nums2[arr2Index--];
			}
		}
	}
}
