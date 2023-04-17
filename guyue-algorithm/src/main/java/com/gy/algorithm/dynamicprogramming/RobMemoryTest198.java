package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RobTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-21 08:00
 */
public class RobMemoryTest198 {

	// memory[i] 表示要考虑抢劫 nums[i....n]所能获得的最大收益.
	private static List<Long> memory;
	private static List<Long> houses;

	// 去考虑抢劫 nums[index...nums.size()] 这个范围里的房子
	private static long tryRob(List<Long> nums, int index) {

		// 考虑的范围已经是一个空集了.
		if (index >= nums.size()) {
			return 0;
		}

		if (memory.get(index) != Long.MIN_VALUE) {
			return memory.get(index);
		}

		long res = 0;
		for (int i = index; i < nums.size(); i++) {
			// 迭代计算
			// 0, 0 + 2, 对于每个循环都这样不断的迭代下去, 最终求出来的值就是所能偷得的最大值.
			//		-> 2, 2 + 2, 返回最大的值, 给 0, 0 + 2
			//		 	-> 4, 4 + 2, 返回最大的值, 给 2, 2 + 2
			//				-> 6, 6 + 2, 返回最大的值, 给 4, 4 + 2
			//	 	-> 3, 3 + 2
			//		 	-> 5, 5 + 2
			//			 	-> 7, 7 + 2
			// 1, 1 + 2
			// 2, 2 + 2
			// 3, 3 + 2
			// ..
			// n - 1
			res = Math.max(res, nums.get(i) + tryRob(nums, i + 2));
		}

		memory.set(index, res);
		return res;
	}

	public static long tryRob(List<Long> nums) {
		// 初始化 memory
		memory = new ArrayList<>(nums.size());
		// 赋初始值
		for (int index = 0; index < nums.size(); index++) {
			memory.add(index, Long.MIN_VALUE);
		}
		return 0;
	}

	public static void main(String[] args) {

	}
}
