package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RobTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-21 08:00
 */
public class RobDynamicTest198 {

	// memory[i] 表示要考虑抢劫 nums[i....n]所能获得的最大收益.
	private static List<Long> memory;
	private static List<Long> houses;

	/**
	 * 整个算法的时间复杂度是 O(n^2) 级别
	 */
	// 去考虑抢劫 nums[index...nums.size()] 这个范围里的房子
	private static long tryRob(List<Long> nums, int index) {

		int numsSize = nums.size();
		// 如果 房子数是0, 那我们什么也抢不到, 退出.
		if (numsSize == 0) {
			return 0;
		}

		/**
		 * memory.get(i) 考虑抢劫, nums[i, n-1]这个范围的最大收益.
		 */
		// 初始化 memory, 赋初始值
		List<Long> memory = new ArrayList<>(numsSize);
		for (int i = 0; i < nums.size(); i++) {
			memory.add(i, Long.MIN_VALUE);
		}

		// 从局部优化的角度上, 从后向前 去考虑:
		// 只有一个房子时, 不会触发报警, 肯定会去偷他.
		memory.set(numsSize - 1, nums.get(numsSize - 1));

		/**
		 * memory[n - 1] = num [n - 1]:
		 * 	memory[5] = num[5] = 4
		 * 	意思是说: 假如只有一个房子的话, 这个房子我们必须偷, 所以最大价值 直接等于 房子价值.
		 *
		 * 		i  = 	[ 0  1   2  3   4   5]
		 *
		 * 	nums   = 	[ 1, 2,  3, 1,  2,  4]
		 *
		 * members = 	[-1,-1, -1,-1, -1, -1]
		 *
		 * for-i = 4
		 * 	for-j = 4, 4 < 6 --------> [x, x, x, x, 2, 4] >> 满足条件情况下, 局部最大是4
		 * 		time-1, j = 4
		 * 			memory[4] = -1
		 *			num[4] = 2
		 *			numJ2 = 4 + 2 不小于 6, 取0:  因为无法取相邻的局部最大值, 所以只能取隔一个数字的局部最大
		 *			max(-1, 2 + 0)
		 *			memory[4] = 2
		 *
		 *		time-1, j = 5
		 *			memory[4] = 2
		 *			num[5] = 4
		 *			numJ2 = 5 + 2 不小于 6, 取0
		 *			max(2, 4 + 0)
		 *			memory[4] = 4
		 *
		 * for-i = 3
		 * 	for-j = 3, 3 < 6 --------> [x, x, x, 1, 2, 4] >> 满足条件情况下, 局部最大是5
		 * 		time-1, j = 3
		 * 			memory[3] = -1
		 * 			num[3] = 1
		 * 			numJ2 = 3 + 2 小于6, 取memory[5] = 4:  因为无法取相邻的局部最大值, 所以只能取隔一个数字的局部最大
		 * 			max(-1, 1 + 4)
		 * 			memory[3] = 5
		 *
		 * 		time-2, j = 4
		 * 			memory[3] = 5
		 * 			num[4] = 2
		 * 			numJ2 = 4 + 2 不小于6, 取0
		 * 			max(5, 2 + 0)
		 * 			memory[3] = 5
		 *
		 * 		time-3, j = 5
		 * 			memory[3] = 5
		 * 			num[5] = 4
		 * 			numJ2 = 5 + 2 不小于6, 取0
		 * 			max(5, 4 + 0)
		 * 			memory[3] = 5
		 *
		 * for-i = 2
		 * 	for-j = 2, 2 < 6 --------> [x, x, 3, 1, 2, 4] >> 满足条件情况下, 局部最大是7
		 * 		time-1, j = 2
		 * 			memory[2] = -1
		 * 			num[2] = 3
		 * 			numJ2 = 2 + 2 小于 6, 取4:  因为无法取相邻的局部最大值, 所以只能取隔一个数字的局部最大
		 * 			max(-1, 3 + 4)
		 * 			memory[2] = 7
		 *
		 * 		time-2, j = 3
		 * 			memory[2] = 7
		 * 			num[3] = 1
		 * 			numJ2 = 3 + 2 小于 6, 取4
		 * 			max(7, 1 + 4)
		 * 			memory[2] = 7
		 *
		 * 		time-3, j = 4
		 * 			memory[2] = 7
		 * 			num[4] = 2
		 * 			numJ2 = 4 + 2 不小于6, 取0
		 * 			max(7, 2 + 0)
		 * 			memory[2] = 7
		 *
		 * 	    time-4, j = 5
		 * 	    	memory[2] = 7
		 * 	    	num[5] = 4
		 * 	    	numJ2 = 5 + 2 不小于6, 取0
		 * 	    	max(7, 4 + 0)
		 * 	    	memory[2] = 7
		 *
		 */
		//
		// 从规模较小, 一步一步的向规模较大偷取, 即状态转移.
		for (int i = numsSize - 2; i >= 0; i--) {

			//
			for (int j = i; j < numsSize; j++) {
				long memoryI = memory.get(i);
				long numJ = nums.get(j);
				long numJ2 = (j + 2 < numsSize ? memory.get(j + 2) : 0);

				memory.set(i, Math.max(memoryI, numJ + numJ2));
			}
		}

		return memory.get(0);
	}


	private static long tryRobV2(List<Long> house, int index) {

		int numsSize = house.size();
		// 如果 房子数是0, 那我们什么也抢不到, 退出.
		if (numsSize == 0) {
			return 0;
		}

		/**
		 * memory.get(i) 考虑抢劫, nums[i, n-1]这个范围的最大收益.
		 */
		// 初始化 memory, 赋初始值
		List<Long> maxWorth = new ArrayList<>(numsSize);
		for (int i = 0; i < house.size(); i++) {
			maxWorth.add(i, Long.MIN_VALUE);
		}

		/**
		 * 从前向后考虑
		 */
		// 只有1个房子时, 不会触发报警, 肯定会去偷他.
		if (house.size() == 1) {
			maxWorth.set(0, house.get(0));
		}

		// 只有2个房子时
		if (house.size() == 2) {
			maxWorth.set(1, Math.max(house.get(0), house.get(1)));
		}

		// 从规模较小, 一步一步的向规模较大偷取, 即状态转移.
		for (int i = 2; i < house.size(); i++) {
			maxWorth.set(i, Math.max(house.get(i) + maxWorth.get(i - 2), maxWorth.get(i - 1)));
		}

		return maxWorth.get(maxWorth.size() - 1);
	}

	public static void main(String[] args) {

	}
}
