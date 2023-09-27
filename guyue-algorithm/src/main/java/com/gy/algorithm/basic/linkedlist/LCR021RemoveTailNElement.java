package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.LinkedNode;

/**
 * https://leetcode.cn/problems/SLwz0R/
 */
public class LCR021RemoveTailNElement {


    private static class LinkedList<E> {

        private LinkedNode preHead;
        private int size;


        public LinkedList() {
            preHead = new LinkedNode();
            size = 0;
        }

        public void removeFromTail(int numTail, E e) {

            // 倒数索引必须为正数, 且不能大于 LinkedList 的大小
            if (numTail < 0 || numTail > size) {
                throw new IllegalArgumentException(" error index .");
            }

            // TODO 把删除 倒数的 转变成 删除正数的.
            //		假设 size = 5, 索引是 0,1,2,3,4
            //		删除倒数第3个, 5 - 3 = 2 即, 删除正数第2个.
            //		从0, 迭代到2的前一个元素, 即索引为1.
            int stopIndex = size - numTail;
            LinkedNode pre = preHead;
            for (int i = 0; i < stopIndex; i++) {
                pre = pre.next;
            }

            // TODO pre 是元素1
            //		pre.next 是要删除的元素2.
            LinkedNode removeNode = pre.next;

            // TODO removeNode.next 是元素3
            //		让 元素1的 next, 指向 元素3, 就是把 元素2删除了.
            pre.next = removeNode.next;

            // TODO  释放资源
            removeNode = null;
            size--;
        }
    }
}
