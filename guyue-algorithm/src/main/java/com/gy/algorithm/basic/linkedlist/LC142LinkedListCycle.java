package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

/**
 * https://leetcode.cn/problems/linked-list-cycle-ii/description/
 */
public class LC142LinkedListCycle {

    /**
     * 题看明白: 但是，本地无法运行
     * @param args
     */
    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{3, 2, 0, -4});
        LC142LinkedListCycle lc142LinkedListCycle = new LC142LinkedListCycle();
        ListNode listNode = lc142LinkedListCycle.detectCycle(head);
        System.out.println(listNode);
    }

    public ListNode detectCycle(ListNode head) {
        // 快慢指针的方式
        ListNode fastPoint = head;
        ListNode slowPoint = head;

        /**
         * 1. 通过快慢指针的方式
         * 2. 快指针走2步, 慢指针走1步
         */
        while (fastPoint != null && fastPoint.next != null) {
            // 慢指针向前移动1步
            slowPoint = slowPoint.next;

            // 快指针向前移动2步
            fastPoint = fastPoint.next.next;

            // 根据初等数学环形公式, fastPoint 指针在一定时间内, 肯定会与 slowPoint 指针相遇, 如果有环, 就多跑1圈
            // slowPoint 指针与 fastPoint 指针相遇
            // 这是相等,是值相等, 这一步的目的: 是返回 相等节点的链表
            if (fastPoint == slowPoint) {

                ListNode tempNodeA = fastPoint;
                ListNode tempNodeB = head;

                while (tempNodeA != tempNodeB) {
                    tempNodeA = tempNodeA.next;
                    tempNodeB = tempNodeB.next;
                }
                return tempNodeA;
            }
        }

        return null;
    }
}
