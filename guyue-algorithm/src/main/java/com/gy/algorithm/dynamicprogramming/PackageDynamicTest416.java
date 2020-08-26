package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageMemoryTest416
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-27 14:25
 */
public class PackageDynamicTest416 {

	/**
	 * 使用一维数组保存中间变量值, 在递推的过程中.
	 */
	private static List<Boolean> memory;


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
		int halfSum = sum / 2;

		// 背包中没有空间了, 背包已经被装满
		if (sum == 0) {
			return true;
		}

		// 背包容量已经不够用了，但是物品还够.
		if (sum < 0) {
			return false;
		}

		// TODO 使用最优化的空间复杂度, 保存这些值.
		memory = new ArrayList<>(halfSum + 1);
		for (int index = 0; index < halfSum + 1; index++) {
			memory.add(index, false);
		}

		// TODO 迭代看一下, nums[0] 是否能够填满这个 Package
		//  	如果可以，就直接返回 nums[0], 否则Package 均是无法填满的.
		//		当 capacityIndex = 1时, 即背包的容量为1, nums[0] 是可以填满这个背包的.
		for (int capacityIndex = 0; capacityIndex <= halfSum; capacityIndex++) {
			// 如果 第0个元素是5, 那么 capacityIndex 等于5时, memory.set(5) = true.
			// 也就是说背包的容量至少 大于等于5.
			memory.set(capacityIndex, nums.get(0) == capacityIndex);
		}

		// 使用双重循环
		// TODO 第一层循环是迭代物品, 从底向上, 不断的向 Package 中添加物品.
		// TODO 每一次循环多考虑一个数, 这是因为
		//  	1. 上面已经对 num[0] 进行了判断, 这里直接从 index=1开始.
		//  	2. 在放一个元素时, 需要考虑前一个元素的情况, 当 index = 0时, index - 1 等于-1, 所以 index 需要多考虑一步.
		for (int numIndex = 1; numIndex < numSize; numIndex++) {

			// TODO 循环结束的条件是, 容量必须大于等于 元素的值, 否则装不下
			for (int capacityTmp = halfSum; capacityTmp >= nums.get(numIndex); capacityTmp--) {
				// TODO 1. memory.get(capacityTmp) :
				//  	不考虑 numIndex 这个元素的情况下, 查看之前 memory[capacityTmp]是否能填满 Package.
				// TODO 2. memory.get(capacityTmp - nums.get(numIndex)) :
				//  	考虑 numIndex 这个元素的情况下, 排除numIndex的容量的话, 是否能填满这个背包.
				memory.set(capacityTmp, memory.get(capacityTmp) || memory.get(capacityTmp - nums.get(numIndex)));
				/**
				 * numIndex = 0
				 * capacityTmp
				 * 11			F
				 * 10			F
				 * 9			F
				 * 8			F
				 * 7			F
				 * 6			F
				 * 5			F
				 * 4			F
				 * 3			F
				 * 2			F
				 * 1			True
				 * 0			F
				 */

				/**
				 * numIndex = 1, value = 5, 11 >= 5
				 * capacityTmp
				 * 11			F	11-5=6
				 * 10			F	10-5=5
				 * 9			F	9-5=4
				 * 8			F	8-5=3
				 * 7			F	7-5=2
				 * 6			True 6-5=1	memory[1] = True, 把自身的容量去掉后, 看看用剩下的元素是否能填满 Package, 如果可以, 那容量为6的Package 就可以被这些元素填满.
				 * 5			F
				 * 4			F
				 * 3			F
				 * 2			F
				 * 1			True
				 * 0			F
				 */

				/**
				 * numIndex = 2, value = 11, 11 >= 11
				 * capacityTmp
				 * 11			F	11-11=0
				 * 10			F
				 * 9			F
				 * 8			F
				 * 7			F
				 * 6			True
				 * 5			F
				 * 4			F
				 * 3			F
				 * 2			F
				 * 1			True
				 * 0			F
				 */

				/**
				 * numIndex = 3, value = 5, 11 >= 5
				 * capacityTmp
				 * 11			True	11-5=6 memory(6) = True 把自身的容量去掉后, 看看用剩下的元素是否能填满 Package, 如果可以, 那容量为10的Package 就可以被这些元素填满.
				 * 10			F
				 * 9			F
				 * 8			F
				 * 7			F
				 * 6			True
				 * 5			F
				 * 4			F
				 * 3			F
				 * 2			F
				 * 1			True
				 * 0			F
				 */
			}
		}

		return memory.get(halfSum);
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
