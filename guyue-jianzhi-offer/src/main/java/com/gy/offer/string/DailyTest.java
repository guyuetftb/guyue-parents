package com.gy.offer.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DailyTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-27 16:30
 */
public class DailyTest {

	public static void main(String[] args) {

		int resultIndex = 0;
		int list1Index = 0;
		int list2Index = 0;
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		List<Integer> resultList = new ArrayList<>();
		for (; resultIndex < list1.size() + list2.size() - 1; resultIndex++) {
			if(list1Index > list1.size()){

				resultList.add(list2.get(list2Index));
				list2Index++;

			} else if(list2Index > list2.size()){
				resultList.add(list1.get(list1Index));
				list1Index++;
			} else if(list1.get(list1Index) > list2.get(list2Index)){
				resultList.add(list1.get(list1Index));
				list1Index++;
			} else {
				resultList.add(list2.get(list2Index));
				list2Index++;
			}
		}
	}

}
