package com.gy.algorithm.basic.array;

/**
 * @ClassName ArrayOddEvenTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-07 16:26
 */
public class ArrayOddEvenTest {

	public static void main(String[] args) {
		int[] arr = {2, 3, 5, 22, 45, 23, 80, 97, 67, 20, 11, 25, 71, 90};
		int begin = 0;
		int end = arr.length - 1;

		while (begin < end) {
			// TODO
			//		1. 从左往右找 偶数
			//  	begin % 2 != 0, 是奇数,
			//
			while (arr[begin] % 2 != 0 && begin < end) {
				begin++;
			}

			// TODO
			// 		2. 从右往左找 奇数
			//		begin % 2 == 0, 是偶数
			while (arr[end] % 2 == 0 && begin < end) {
				end--;
			}

			int tmp = arr[begin];
			arr[begin] = arr[end];
			arr[end] = tmp;
		}

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}