package com.gy.algorithm.basic.array;

/**
 * @ClassName ArrayPlusOneTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 15:31
 */
public class ArrayPlusOneTest {


	public static void main(String[] args) {
		int[] arr = new int[]{1, 9, 9};
		int[] arrNew = plusOne(arr);
		for (int i = 0; i < arrNew.length; i++) {
			System.out.print(arrNew[i]);
		}
	}

	public static int[] plusOne(int[] arr) {
		// TODO 从后向前, 从低位到高位不断的迭代每一个数字.
		//		假如个位数字小于9, 加1后, 不会引起进位, 直接退出.
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] < 9) {
				// TODO
				//		假如个位数字小于9, 加1后, 不会引起进位, 直接退出.
				arr[index]++;
				return arr;
			} else {
				//	TODO
				//		假如个位数字等于9, 加1后, 会引起进位, 那个数上的数字 会变成0.
				//		假如十块数字依然是9, 得到个位进位后, 十位也要向百位进1, 十位上的数字也会变成0.
				arr[index] = 0;
			}
		}
		return arr;
	}
}
