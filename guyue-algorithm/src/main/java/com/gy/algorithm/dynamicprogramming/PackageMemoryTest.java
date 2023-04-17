package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-21 18:41
 */
public class PackageMemoryTest {


	private static List<List<Long>> memory;

	/**
	 * 用 【0......index)的物品, 填充容积为 currentCapacity 的背包, 使其价值最大.
	 */
	public static long bestValue(List<Long> weightList, List<Long> valueList, int currentIndex, Long currentCapacity) {

		/**
		 *
		 * 	id		0	1	2
		 * 	weight	1	2	3
		 * 	value 	6	10	12
		 *
		 *
		 * time-1
		 * 		bestValue === 2(3 - 1),  currentCapacity = 5
		 * 			1. dont-put
		 * 				bestValue === 1(2 - 1), 5
		 * 				1. dont-put
		 * 					bestValue === 0(1 - 1), currentCapacity = 5, maxValue = 6
		 * 						1. dont-put
		 * 							bestValue === -1(0 -1), 5
		 * 							res = 0
		 * 						dontPutValue = 0
		 *
		 * 						2. put
		 * 							currentIndex=0
		 *							currentValue=6
		 * 							previousIndexBestValue: 0
		 * 								bestValue === -1(0-1), currentCapacity = 4(5-1), 之所以要减掉自己的 weight, 是因为要看一下, 如果把自己加进去后, capacity 不能超过总 Capacity
		 * 									res = 0;
		 *
		 * 						putValue = 6(6 + 0);
		 *
		 * 					--> 6{0,5} = Max(6, 0)
		 *
		 * 				2. put
		 * 					currentIndex = 1
		 * 					currentValue = 10
		 * 					previousIndexBestValue =
		 * 						bestValue === 0(1-1), currentCapacity = 3(5 - 2)
		 * 							1. dont-put
		 * 								bestValue === -1(0-1), currentCapacity = 3
		 * 								res = 0
		 * 							dontPutValue = 0
		 *
		 * 							2. put
		 * 								currentIndex=0
		 * 								currentValue=6
		 * 								previousIndexBestValue =
		 * 									bestValue === -1(0 -1), 3
		 * 									res = 0;
		 * 							putValue=6(6+0)
		 * 						-->	6{0,3}=Max(6,0)
		 *
		 * 				--> 10[Math.max(10, 6)]
		 *
		 * 			2. put
		 * 				currentIndex = 2
		 * 				currentValue = 12
		 * 				previousIndexBestValue =
		 * 					bestValue === 1(2-1), currentCapacity = 2(5-3)
		 * 						1. dont-put
		 * 							bestValue === 0(1-1), currentCapacity = 2
		 * 								1. dont-put
		 * 									currentIndex = -1
		 * 									res = 0;
		 * 								dontPutValue=0
		 *
		 * 								2. put
		 * 									currentIndex = 0
		 * 									currentValue = 6
		 * 									previousIndexBestValue =
		 * 										bestValue === -1(0-1), currentCapacity = 2
		 * 										res = 0;
		 * 								putValue = 6 + 0
		 * 							--> 6{0,2}=Max(0,6)
		 * 						2. put
		 * 							currentIndex=1
		 * 							currentValue=10
		 * 							previousIndexBestValue=
		 * 								bestValue === 0(1-1), currentCapacity = 0(2-2)
		 * 							-->		return 0;
		 * 							putValue = 10(10 + 0)
		 * 					--> 10[Math.max(10,6)]
		 * 				putValue=22(12+10)
		 *
		 * 			-->22[Math.max(10,22)]
		 */
		/**
		 * 在我们求解问题的过程中, 我们遇到了一些重叠子问题, 即, index, currentCapacity 这样的数据对.
		 * 这些数据对, 在求解过程中, 会计算算多次, 所以我们要缓存这些值, 用记忆化搜索方式.
		 */
		// 没有物品了, 退出.
		if (currentIndex < 0) {
			return 0;
		}

		// 背包的剩余容量已经小于0了，不能再装物品了.
		if (currentCapacity <= 0) {
			return 0;
		}

		if (memory.get(currentIndex).get(currentCapacity.intValue()) != Long.MIN_VALUE) {
			return memory.get(currentIndex).get(currentCapacity.intValue());
		}

		long res = 0L;

		/**
		 * 第1种情况, 我们先不考虑 currentIndex, 而是先考虑 currentIndex -1, 来填充背包的话, 他能提供的价值是多少.
		 */
		long dontPutValue = bestValue(weightList, valueList, currentIndex - 1, currentCapacity);
		res = dontPutValue;

		/**
		 * 第2种情况, 我们把物品放入背包中
		 */
		// 检查当前剩余容量, 是不是大于 当前物品的容量, 即, 如果要入下该物品, 背包中还有没有空间.
		if (currentCapacity >= weightList.get(currentIndex)) {
			long currentValue = valueList.get(currentIndex);

			// 我们要查看, 从 [0...至 currentIndex -1) 这些物品, 填充 currentCapacity - weightList.get(currentIndex)) 这么多容积, 所能得到的最大值是多少.
			long previousIndexBestValue = bestValue(weightList, valueList, currentIndex - 1, currentCapacity - weightList.get(currentIndex));

			long putValue = currentValue + previousIndexBestValue;

			res = Math.max(dontPutValue, putValue);
		}

		// 更新相应的值.
		memory.get(currentIndex).set(currentCapacity.intValue(), res);

		return res;
	}

	public static long package01(List<Long> weightList, List<Long> valueList, Long finalCapacity) {
		int size = weightList.size();

		/**
		 * memory 是一个2维表格
		 * 第1维: 是元素的个数.
		 * 第2维: 表示从0到finalCapacity这么多的容量.
		 */
		memory = new ArrayList<>(size);

		for (int index = 0; index < size; index++) {
			List<Long> capacityList = new ArrayList<>(finalCapacity.intValue() + 1);
			memory.add(index, capacityList);

			for (int capacityIndex = 0; capacityIndex < (finalCapacity.intValue() + 1); capacityIndex++) {
				capacityList.add(capacityIndex, Long.MIN_VALUE);
			}
		}

		// 我们考虑的是从0, 到 n-1 所有的这些物品, 将他们将入容积为 finalCapacity , 这么一个背包中.
		return bestValue(weightList, valueList, size - 1, finalCapacity);
	}

	public static void main(String[] args) {

		List<Long> weight = new ArrayList<>();
		weight.add(1L);
		weight.add(2L);
		weight.add(3L);

		List<Long> value = new ArrayList<>();
		value.add(6L);
		value.add(10L);
		value.add(12L);

		System.out.println(package01(weight, value, 5L));
	}
}
