package com.gy.algorithm.dynamicprogramming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName NonOverlappingIntervalsTes
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-28 20:39
 */
public class GreedDynamicNonOverlappingIntervalsTest {



	public static int eraseOverlapIntervals(List<Interval> intervals) {
		if (intervals.size() == 0) {
			return 0;
		}

		// TODO memory.get(i) 表示使用 intervals[0.....i]的区间能构成的最长不重叠区间序列
		// 		初始值为1.

		List<Integer> memory = new ArrayList<>(intervals.size());
		for (int i = 0; i < intervals.size(); i++) {
			memory.add(i, 1);
		}

		// TODO 外层循环 依次 遍历整个 interval List
		for (int intervalIndex = 1; intervalIndex < intervals.size(); intervalIndex++) {
			Interval interval = intervals.get(intervalIndex);

			// TODO 内层循环, 每次都从0开始, 遍历到 intervalIndex 所指的位置,
			//		计算 intervalIndex 位置, 可以组成的 不重叠 的区间个数是多少.
			for (int seqIndex = 0; seqIndex < intervalIndex; seqIndex++) {

				Interval intervalTmp = intervals.get(seqIndex);
				// TODO 如果 有遇到 end 坐标小于等于  intervalIndex 元素的 start 坐标, 就说明他们不重叠.
				if (interval.start >= intervalTmp.end) {

					// TODO 就更新 intervalIndex 元素的 所能组成的 不重叠区间的 最小个数.
					memory.set(intervalIndex, Math.max(memory.get(intervalIndex), memory.get(seqIndex) + 1));
				}
			}
		}

		// TODO 每个元素都代表一个 不重叠的 区间.
		int res = 1;
		for (int i = 0; i < memory.size(); i++) {
			res = Math.max(res, memory.get(i));
		}

		// TODO 返回要删除的区间个数, 是整体的区间数, 减去 求得的不重叠区间数.
		return intervals.size() - res;
	}

	public static void main(String[] args) {
		List<Interval> intervals = new ArrayList<>();
		intervals.add(new Interval(1, 2));
		intervals.add(new Interval(2, 3));
		intervals.add(new Interval(3, 4));
		intervals.add(new Interval(1, 3));

		Collections.sort(intervals, new AscComparator());

		for (Interval interval : intervals) {
			System.out.println(interval);
		}
	}

	public static class AscComparator implements Comparator<Interval> {

		@Override
		public int compare(Interval o1, Interval o2) {
			if (o1.start != o2.start) {
				return o1.start - o2.start;
			}
			return o1.end - o2.end;
		}
	}

	public static class Interval implements Serializable {

		public int start;
		public int end;

		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			Interval interval = (Interval) o;
			return start == interval.start &&
				end == interval.end;
		}

		@Override
		public int hashCode() {
			return Objects.hash(start, end);
		}

		@Override
		public String toString() {
			return "Interval{" +
				"start=" + start +
				", end=" + end +
				'}';
		}
	}
}
