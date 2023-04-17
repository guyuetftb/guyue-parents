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
public class GreedNonOverlappingIntervalsTest {


	public static int eraseOverlapIntervals(List<Interval> intervals) {
		if (intervals.size() == 0) {
			return 0;
		}

		// TODO 因为我们已经按结尾最早的排序了, 所以结尾最早的元素肯定是要获取的, 所以res 初始值=1.
		int res = 1;

		// TODO 在选择区间的过程中, 之前选择使用的区间 index 是多少.
		int preIndex = 0;

		// TODO 外层循环 依次 遍历整个 interval List
		for (int intervalIndex = 1; intervalIndex < intervals.size(); intervalIndex++) {
			Interval interval = intervals.get(intervalIndex);
			Interval preInterval = intervals.get(preIndex);

			// TODO 如果当前 interval 的起始, 大于等于 pre 的结束, 说明肯定不重叠.
			//		按 end 排完序后, 如果 current.start >= pre.end, 就说明这两个 interval 肯定不相交.
			/**
			 * Interval{start=1, end=2}
			 * Interval{start=1, end=3}
			 * Interval{start=2, end=3}
			 * Interval{start=3, end=4}
			 *    __      [1,2]
			 *    ____    [1,3]
			 *      __    [2,3]
			 *        __  [3,4]
			 * 0_1_2_3_4_5__
			 *
			 *
			 */
			if (interval.start >= preInterval.end) {
				res++;
				preIndex = intervalIndex;
			}
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

		Collections.sort(intervals, new EndEasierComparator());

		for (Interval interval : intervals) {
			System.out.println(interval);
		}
	}

	public static class EndEasierComparator implements Comparator<Interval> {

		@Override
		public int compare(Interval o1, Interval o2) {
			// TODO 谁的 end 结束的更早, 谁排在前面.
			if (o1.end != o2.end) {
				return o1.end - o2.end;
			}

			// TODO 如果 end 相同, 谁的 start 更靠前, 把谁排在最前面.
			return o1.start - o2.start;
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
