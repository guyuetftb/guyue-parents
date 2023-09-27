package com.gy.algorithm.basic.linkedlist;


import com.gy.algorithm.basic.common.ListNode;

/**
 * > 1. 使用快，慢指针来解决。
 * > 2. 退出条件: fast指针不为空，且 fast.next也不为空，退出循环。
 * > 3. 操作: slow向后称动一步；fast向后移动2步；
 * <p>
 * https://leetcode.cn/problems/middle-of-the-linked-list/
 */
public class LC876MiddleLinkedList {
    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{1, 2, 3, 4, 5, 6});
        LC876MiddleLinkedList lc876MiddleLinkedList = new LC876MiddleLinkedList();
        ListNode listNode = lc876MiddleLinkedList.middleNode(head);
        System.out.println(listNode.val);
    }

    /**
     * 方案:--------------------------------------------------------------------------
     * 1. 使用快,慢指针.
     * 2. fastPoint 走2步, slowPoint走1步
     * 3. fastPoint 走到链表表, slowPoint走到 中间, 返回 slowPoint
     */
    public ListNode middleNode(ListNode head) {
        // 1.两个指针都指向头节点
        ListNode slowPoint = head;
        ListNode fastPoint = head;

        /**
         * 2 -1. fast && fast.next 不为null, 向后移动2步
         * 2-2. slow 向后移动一步
         */
        while (null != fastPoint && null != fastPoint.next) {
            fastPoint = fastPoint.next.next;
            slowPoint = slowPoint.next;
        }

        // 3. 返回间节点.
        return slowPoint;
    }
}
