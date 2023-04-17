package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * 本代码的时间复杂度是: O ( N ^ 2) 但这个问题，还有一个更好的实现，时间复杂度是: O(N * LogN )，但是这个解法不是动态规划的解法.
 */
public class LISDynamicTest {

	private static List<Integer> memory;

	public static int lengthOfLIS(List<Integer> nums) {

		if (null == nums || nums.size() <= 0) {
			return 0;
		}

		// TODO memory.get(index) 代表以 index 结尾的最长上升子序列的长度.
		memory = new ArrayList<>(nums.size());
		for (int index = 0; index < nums.size(); index++) {
			memory.add(index, 1);
		}

		// TODO 外层循环 依次 遍历整个List
		for (int i = 1; i < nums.size(); i++) {

			// TODO 内层循环, 每次都从0开始, 遍历到 numIndex 所指的位置,
			//		计算numIndex位置数字的最长子序列是多少.
			for (int j = 0; j < i; j++) {

				// TODO 如果 有遇到 比 numIndex 元素小的元素
				if (nums.get(j) < nums.get(i)) {

					// TODO 就更新 numIndex 元素的 上升子序列的长度.
					memory.set(i, Math.max(memory.get(i), 1 + memory.get(j)));
				}
			}
		}

		// TODO 每个元素都代表一个子序列.
		int res = -1;
		for (int i = 0; i < memory.size(); i++) {
			res = Math.max(res, memory.get(i));
		}

		return res;
	}


	public static void main(String[] args) {
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(10);
		arr.add(9);
		arr.add(2);
		arr.add(5);
		arr.add(3);
		arr.add(7);
		arr.add(101);
		arr.add(1);
		System.out.println(lengthOfLIS(arr));
	}

}
