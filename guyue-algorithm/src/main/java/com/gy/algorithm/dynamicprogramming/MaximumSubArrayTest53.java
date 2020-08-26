package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MaximumSubArrayTest53
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-10 17:00
 */
public class MaximumSubArrayTest53 {

	public static void main(String[] args) {
		int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
		int maxValue = maxSubArray(arr);
		System.out.println("最大子序列合是: " + maxValue);
	}

	public static int maxSubArray(int[] arr) {

		// TODO 初始化状态集合, 设置默认值.
		List<Integer> result = new ArrayList<Integer>(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result.add(i, Integer.MIN_VALUE);
		}

		result.set(0, arr[0]);

		int maxValue = Integer.MIN_VALUE;
		for (int i = 1; i < arr.length; i++) {
			int subArrMaxVal = Math.max(arr[i], result.get(i - 1) + arr[i]);
			result.set(i, subArrMaxVal);

			maxValue = Math.max(maxValue, subArrMaxVal);
		}

		for (int i = 0; i < result.size(); i++) {
			System.out.printf("数组索引为: %d 的子序列和为: %d \n", i, result.get(i));
		}

		return maxValue;
	}

}
