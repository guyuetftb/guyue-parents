package com.gy.datastructure.heap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.PriorityQueue;

/**
 * LeetCode 347 号问题
 *
 * @ClassName FindTopMFromN
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-29 16:07
 */
public class FindTopMFromNJavaPriorityQueueComparator {

	private class Freq {

		private int e;
		private int freq;


		Freq(int e, int freq) {
			this.e = e;
			this.freq = freq;
		}
	}

	private class FreqComparator implements Comparator<Freq> {

		@Override
		public int compare(Freq a, Freq b) {
			// 因为 Java 中的堆是一个最小堆,  即 元素按某个属性排序, 最小的放在堆顶.
			// a.freq < b.freq, 返回 -1;
			// a.freq > b.freq, 返回 1
			// a.freq = b.freq, 返回 0
			return a.freq - b.freq;
		}
	}

	public List<Integer> topKFrequent(int[] nums, int k) {

		// 把元素都放入一下 Map 中,并得到频次
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
		for (int i : nums) {
			if (treeMap.containsKey(i)) {
				treeMap.put(i, treeMap.get(i) + 1);
			} else {
				treeMap.put(i, 1);
			}
		}

		PriorityQueue<Freq> pq = new PriorityQueue<>(new FreqComparator());

		for (Entry<Integer, Integer> e : treeMap.entrySet()) {
			if (pq.size() < k) {

				// 队列中存放M个元素
				pq.add(new Freq(e.getKey(), e.getValue()));
			} else if (treeMap.get(e.getKey()) > pq.peek().freq) {

				// 频次越大, 优先级越底。使用 maxheap 的时候, 排序就越靠前.
				// 遍历到的元素的频次, 比队首的元素的频次更高一些
				pq.remove();
				pq.add(new Freq(e.getKey(), e.getValue()));
			}
		}

		LinkedList<Integer> result = new LinkedList<Integer>();
		while (!pq.isEmpty()) {
			result.add(pq.remove().e);
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
