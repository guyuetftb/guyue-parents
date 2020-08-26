package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName RecursionPermuteTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-29 19:57
 */
public class RecursionPermuteTest {

	public static List<String> res = new ArrayList<String>();

	public static List<Boolean> used = null;

	// TODO tmpContainer 中保存了 一个有 numIndex(0个, 1个, 2个....) 个元素的排列
	//		向这个排列的末尾添加第 numIndex + 1 个元素, 获得一个有 numIndex + 1个元素的排列.
	public static void generatePermutation(List<Integer> nums, int numIndex, List<Integer> tmpContainer) {

		// TODO	如果 numIndex 等于 nums 的大小, 也就是说 numIndex 中保存了nums 中所有的元素.
		// 		当前迭代退出.
		if (numIndex == nums.size()) {
			StringBuilder s = new StringBuilder();
			for (Integer i : tmpContainer) {
				s.append(i).append(",");
			}
			res.add(s.toString());
			return;
		}

		for (int i = 0; i < nums.size(); i++) {
			// TODO used 用于记录元素是否被使用过了.
			//
			if (!used.get(i)) {
				// TODO  已经把 i 位置上的元素添加到 tmpContainer 中了, 下次再循环就不能再添加了.
				tmpContainer.add(nums.get(i));
				used.set(i, true);
				generatePermutation(nums, numIndex + 1, tmpContainer);

				// TODO 删除元素, 重置属性值
				tmpContainer.remove(nums.get(i));
				used.set(i, false);
			}
		}
	}

	public static List<String> permute(List<Integer> nums) {

		used = new ArrayList<>(nums.size());
		for (int i = 0; i < nums.size(); i++) {
			used.add(i, false);
		}
		generatePermutation(nums, 0, new ArrayList<Integer>());

		return res;
	}


	public static void main(String[] args) {
		List<Integer> nums = new ArrayList<>();
		nums.add(1);
		nums.add(3);
		nums.add(5);
		permute(nums).forEach(new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		});
	}
}
