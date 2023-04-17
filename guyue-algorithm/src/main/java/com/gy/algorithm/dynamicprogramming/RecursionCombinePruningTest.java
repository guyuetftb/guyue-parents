package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * @ClassName RecursionCombineTest46
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-29 20:55
 */
public class RecursionCombinePruningTest {

	public static Vector v;

	public static List<String> res = new ArrayList<String>();

	// TODO 求解 C(n,k)，当前已经找到的组合存储在 tmpContainer 中，需要从 start 中开始搜索新的元素
	public static void generateCombination(int n, int k, int start, List<Integer> tmpContainer) {

		// TODO 已经找到了有 k 个元素的组合, 程序退出。
		if (k == tmpContainer.size()) {
			StringBuilder s = new StringBuilder();
			for (Integer i : tmpContainer) {
				s.append(i).append(",");
			}
			res.add(s.toString());
			return;
		}

		// TODO 程序循环时, tmpContainer 中 其实只还有 k - tmpContainer.size() 这么多空位.
		// 		换句话说, 我们应该从 start ... n，中寻找 k - tmpContainer.size() 这么多空位.
		//		而在 for 循环中, 一旦我们选择了 i, 就是从 [i...n]中找到 k - tmpContainer.size() 个元素.
		//		也就是说, [i...n]中 至少应该有 k - tmpContainer.size() 个元素
		//		i 最多为 n - (k-tmpContainer.size()) + 1.
		for (int i = start; i <= (n - (k - tmpContainer.size()) + 1); i++) {
			// TODO
			tmpContainer.add(i);
			// TODO 下次循环 start 从 i + 1 的位置开始循环.
			//		之所以不考虑 i 之前的数字, 是因为之前已经考虑过了, 不需再考虑了, 组合对顺序没有要求.
			generateCombination(n, k, i + 1, tmpContainer);

			// TODO
			// 		当算法回溯时,即接着尝试下一个字符, 所以需要从 tmpContainer 删除刚刚的数字.
			//		注意: 这里不能直接使用 int 类型, 因为 ArrayList 重载了 remove 方法, int 类型会被当做是下标处理, 抛异常.
			tmpContainer.remove(new Integer(i));
		}
	}


	public static List<String> combine(int n, int k) {
		List<Integer> tmpContainer = new ArrayList<>(k);
		//for (int i = 0; i < k; i++) {
		//	tmpContainer.add(Integer.MIN_VALUE);
		// }
		generateCombination(n, k, 1, tmpContainer);
		return res;
	}

	public static void main(String[] args) {
		combine(5, 2).forEach(new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		});
	}

}
