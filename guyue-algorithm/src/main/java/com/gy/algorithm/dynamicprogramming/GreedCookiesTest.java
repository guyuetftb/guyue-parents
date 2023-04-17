package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName GreedTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-28 19:59
 */
public class GreedCookiesTest {

	public static int findContentChildren(List<Integer> children, List<Integer> cookies) {
		// TODO 对 小朋友 降序排序
		Collections.sort(children, new DescComparator());

		// TODO 对 饼干 降序排序
		Collections.sort(cookies, new DescComparator());

		int res = 0;
		int childIndex = 0;    // 代表当前 小朋友的 索引
		int cookieIndex = 0;    // 代表当前 饼干的 索引

		// TODO
		//		小朋友的索引必须小于 小朋友个数, 也就是说: 没有小朋友了, 还给谁饼干.
		// 		饼干的索引必须小于 饼干个数, 也就是说: 没有饼干了, 也没法分配了.
		while (childIndex < children.size() && cookieIndex < cookies.size()) {
			// TODO 当前的饼干可以满足 最贪心的小朋友, 把饼干分给他
			if (cookies.get(cookieIndex) >= children.get(childIndex)) {
				res++;
				childIndex++;
				cookieIndex++;
			} else {
				// TODO 当前饼干无法满足 这个贪心的小朋友, 不好意思，只能轮到下一个小朋友了.
				childIndex++;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		List<Integer> children = new ArrayList<>();
		children.add(1);
		children.add(2);
		children.add(3);

		List<Integer> cookies = new ArrayList<>();
		cookies.add(1);
		cookies.add(2);

		System.out.println(findContentChildren(children, cookies));
	}

	public static class DescComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
	}
}
