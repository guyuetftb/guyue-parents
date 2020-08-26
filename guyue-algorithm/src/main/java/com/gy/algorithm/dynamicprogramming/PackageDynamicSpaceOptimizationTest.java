package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageMemoryTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-21 18:41
 */
public class PackageDynamicSpaceOptimizationTest {


	private static List<List<Long>> memory;


	public static long package01(List<Long> elementCapacityList, List<Long> valueList, Long finalCapacity) {
		int size = elementCapacityList.size();

		/**
		 * memory 是一个2维表格
		 * 第1维: 是元素的个数.
		 * 第2维: 表示从0到finalCapacity这么多的容量.
		 */
		// TODO 优化1
		// TODO 保存空间只初始化2行.
		memory = new ArrayList<>(2);
		for (int index = 0; index < size; index++) {
			List<Long> tmpCapacityList = new ArrayList<>(finalCapacity.intValue() + 1);
			memory.add(index, tmpCapacityList);

			for (int capacityIndex = 0; capacityIndex < (finalCapacity.intValue() + 1); capacityIndex++) {
				tmpCapacityList.add(capacityIndex, Long.MIN_VALUE);
			}
		}

		/**
		 * 传入的元素数量, 要与价值相等.
		 */
		if (elementCapacityList.size() != valueList.size()) {
			return 0;
		}

		/**
		 * 容积必须大于0
		 */
		if (finalCapacity <= 0) {
			return 0;
		}

		/**
		 *
		 * 	id		0	1	2
		 * 	weight	1	2	3
		 * 	value 	6	10	12
		 *
		 *
		 * 在只有一个元素的情况下, 初始化不同容积的最大值
		 * 		0	1	2	3	4	5
		 * 	0	0	6	6	6	6	6
		 */
		for (int capacity = 0; capacity <= finalCapacity.intValue(); capacity++) {
			long valueInitValue = 0L;
			if (capacity >= elementCapacityList.get(0).intValue()) {
				// 1 >= 1
				// 2 >= 1
				// 3 >= 1
				// 4 >= 1
				// 5 >= 1
				valueInitValue = valueList.get(0).intValue();
			} else {
				// 0 <= 1
				valueInitValue = 0;
			}
			memory.get(0).set(capacity, valueInitValue);
		}

		int elementNum = elementCapacityList.size();

		// row=行=代表元素
		for (int rowElement = 1; rowElement < elementNum; rowElement++) {

			// TODO 优化点2
			// TODO 根据奇偶性的不断变化, 选择不同的行保存数据.
			// TODO 下面只要涉及到行号的, 就使用优化后的行号.

			// col=列=代表容积
			for (int colCapacity = 0; colCapacity <= finalCapacity; colCapacity++) {

				long capacity = 0;
				/**
				 * 不把元素放入容积中.
				 * rowElement - 1, 代表少考虑1个元素, 把当前元素排除在外.
				 */
				long dontPut = memory.get((rowElement - 1) % 2).get(colCapacity);
				capacity = dontPut;

				/**
				 * 容量值必须 大于 要放入的元素容量, 才进行判断
				 */
				if (colCapacity >= elementCapacityList.get(rowElement % 2)) {
					long elementValue = valueList.get(rowElement % 2);
					/**
					 * 在不考虑当前元素, 且总容积减去元素所占容量 情况下, N-1元素所能取得的最大值.
					 */
					long preElementMaxValue = memory.get((rowElement - 1) % 2).get(colCapacity - elementCapacityList.get(rowElement % 2).intValue());

					/**
					 * 对放入元素, 不放元素 两种情况求最大值.
					 */
					capacity = Math.max(dontPut, (elementValue + preElementMaxValue));
				}

				memory.get(rowElement % 2).set(colCapacity, capacity);
			}
		}

		return memory.get((size - 1) % 2).get(finalCapacity.intValue());
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
