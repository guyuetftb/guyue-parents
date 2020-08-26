package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageMemoryTest416
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-27 14:25
 */
public class PackageMemoryTest416 {

	/**
	 * memory[i][c] 表示 使用索引为[0.....i]的这些元素，是否可以完全填充一个容量为C 的背包 -1表示未计算, 0表示为可填充, 1表示可填充
	 */
	private static List<List<Integer>> memory;


	public static boolean tryPartition(List<Integer> nums, int index, int sum) {

		// 背包中没有空间了, 背包已经被装满
		if (sum == 0) {
			return true;
		}

		// 背包容量已经不够用了，但是物品还够.
		if (sum < 0) {
			return false;
		}

		// 物品已经没有了，但是背包容量还足够
		if (index < 0) {
			return false;
		}

		// 如果这个值不为 MIN_VALUE，就说明这个值是已经被计算过的.
		if (memory.get(index).get(sum) != Integer.MIN_VALUE) {
			return memory.get(index).get(sum) == 1;
		}

		// 不使用 当前 index 这个商品, 看是否能填满整个Package
		boolean noCurrentIndex = tryPartition(nums, index - 1, sum);

		// 假设使用当前商品正好能填满 Package, 查看剩余空间的填充情况.
		boolean hasCurrentIndex = tryPartition(nums, index - 1, sum - nums.get(index));

		// 有一个能填满, 那就算满足条件 记录值的状况
		memory.get(index).set(sum, (noCurrentIndex || hasCurrentIndex) ? 1 : 0);

		// 如果 记录的值是1, 就说明可以填满, 就退出.
		return memory.get(index).get(sum) == 1;
	}

	public static boolean canPartition(List<Integer> nums) {

		// 计算所有数据的总和
		int sum = 0;
		for (int i = 0; i < nums.size(); i++) {
			sum += nums.get(i);
		}

		// 说明用来填充的数据, 不能被完全平分, 退出
		if (sum % 2 != 0) {
			return false;
		}

		int numSize = nums.size();
		memory = new ArrayList<>(numSize);
		int halfSize = sum / 2;

		for (int index = 0; index < numSize; index++) {
			List<Integer> capacityList = new ArrayList<Integer>(halfSize + 1);
			memory.add(index, capacityList);

			for (int capacityIndex = 0; capacityIndex < (halfSize + 1); capacityIndex++) {
				capacityList.add(capacityIndex, Integer.MIN_VALUE);
			}
		}

		/**
		 * 这里需要注意, 一般是一个问题点.
		 * index 的索引是从尾部开始的, 从高到底的.
		 */
		return tryPartition(nums, numSize - 1, sum / 2);
	}

	public static void main(String[] args) {
		List<Integer> nums = new ArrayList<>();
		nums.add(1);
		nums.add(5);
		nums.add(11);
		nums.add(5);
		System.out.println(canPartition(nums));


	}

}
