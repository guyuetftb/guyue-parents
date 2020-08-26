package com.gy.algorithm.basic.array;

import java.util.HashMap;

/**
 * @ClassName ArrayQueueJumperTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-27 16:58
 */
public class ArrayQueueJumperTest {

	// https://www.nowcoder.com/questionTerminal/31c1ae9d1c804b66b6ae17181e76f4f0?answerType=1&f=discussion

	/**
	 * 计算有多少个人最终不在自己原来的位置上
	 *
	 * @param n int整型 队伍总长
	 * @param cutIn int整型一维数组 依次会插队到最前方的人的编号
	 * @return int整型
	 *
	 *
	 * 这道题的思路是：
	 * 1. 排在最后面的那去插队的人人以后的人，位置肯定都不会变。所以找到maxm，就是最大变化的人数。
	 * 2. 然后去寻找前面的人哪些位置变了：
	 * 		a、首先，在maxm之前的人，没有插队的位置肯定变了。
	 * 		b、其次，就是去找那些插队的人会不会最后还处在原位。
	 *
	 * 由于，有些人多次插队，最后一次插队才决定他最后的位置。
	 * 所以从cutIn数组的后面开始遍历，计算每个人插完队后还有几个人插了队，
	 * 若插队人数恰好就是该人的原位置，就count++。
	 * 用hashmap来记录已经计算过的人，当他重复出现时，
	 * 不再需要计算（注意，重复的人只算做一个，所以要剪掉cutin队列的长度）
	 *
	 * 发现hash比arraylist的占用空间小
	 */
	public int countDislocation(int n, int[] cutIn) {
		// write code here
		int maxm = 0;
		int cutnum = cutIn.length;
		int count = 0;
		int index = 1;

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = cutIn.length - 1; i >= 0; i--) {
			if (!map.containsKey(cutIn[i])) {
				map.put(cutIn[i], index++);
				if (cutnum - i == cutIn[i]) {
					count++;
				}
			} else {
				cutnum--;
			}
			if (cutIn[i] > maxm) //找到最大的数，后面的人位置都不变，前面的没插队的都会变。
			{
				maxm = cutIn[i];
			}
		}

		return maxm - count;
	}

}
