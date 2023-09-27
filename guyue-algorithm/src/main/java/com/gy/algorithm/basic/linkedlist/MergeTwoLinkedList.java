package com.gy.algorithm.basic.linkedlist;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.LinkedList;

/**
 * @ClassName RemoveTailNElementTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 14:13
 */
public class MergeTwoLinkedList {

    public static void main(String[] args) {

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(7);
        list1.add(9);
        list1.add(22);
        list1.add(23);

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(8);
        list2.add(10);
        list2.add(12);
        list2.add(15);

        LinkedList<Integer> resultList = merge(list1, list2);
        for (Integer i : resultList) {
            System.out.println(i);
        }
    }

    public static LinkedList<Integer> merge(LinkedList<Integer> list1, LinkedList<Integer> list2) {

        if (list1.isEmpty()) {
            return list2;
        }

        if (list2.isEmpty()) {
            return list1;
        }

        int list1Index = 0;
        int list2Index = 0;
        int resultIndex = 0;

        LinkedList<Integer> resultList = new LinkedList();
        for (; resultIndex < list1.size() + list2.size(); resultIndex++) {
            if (list1Index >= list1.size()) {
                /**
                 * list1 中的元素已全部添加到 result 中, 只要处理 list2 中的元素.
                 */
                resultList.add(resultIndex, list2.get(list2Index));
                list2Index++;
            } else if (list2Index >= list2.size()) {
                /**
                 * list2 中的元素已全部添加到 result 中, 只要处理 list1 中的元素
                 */
                resultList.add(resultIndex, list1.get(list1Index));
                list1Index++;
            } else if (list1.get(list1Index) > list2.get(list2Index)) {
                /**
                 * list2中的元素 小于 list1中的元素, 把 list2中的元素添加到队列中
                 */
                resultList.add(resultIndex, list2.get(list2Index));
                list2Index++;
            } else {
                /**
                 * list1中的元素 小于 list2中的元素, 把 list1中的元素添加到队列中.
                 */
                resultList.add(resultIndex, list1.get(list1Index));
                list1Index++;
            }
        }
        return resultList;
    }
}
