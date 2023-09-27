package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;


/**
 * https://leetcode.cn/problems/merge-two-sorted-lists/
 */
public class LC21MergeTwoSortedLinkedList {

    public static void main(String[] args) {
        ListNode head1 = ListNode.buildListNode(new int[]{1, 2, 4});
        ListNode head2 = ListNode.buildListNode(new int[]{1, 3, 4});
        LC21MergeTwoSortedLinkedList lc21MergeTwoSortedLinkedList = new LC21MergeTwoSortedLinkedList();
        ListNode combineList = lc21MergeTwoSortedLinkedList.mergeTwoLists(head1, head2);
        ListNode head = combineList;
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        /**
         * 初始化变量, dummy, pre
         *                   -> dummy 虚拟节点, pre指向新链表最后的节点
         */
        ListNode dummyNode = new ListNode();
        dummyNode.val = -1;
        ListNode pre = dummyNode;

        /**
         * 循环迭代2个链表, 把链表的值, 添加到新链表
         */
        while (list1 != null && list2 != null) {
            /**
             * list1的值 <= list2的值
             */
            if (list1.val <= list2.val) {
                pre.next = list1;

                // 5. list1, list2向后移动
                list1 = list1.next;
            }
            /**
             * list1 的值 > list2的值
             */
            else {
                pre.next = list2;

                // 5. list1, list2向后移动
                list2 = list2.next;
            }

            /**
             * 当前节点向后移动
             */
            pre = pre.next;
        }

        /**
         * 将剩余链表添加到新链表尾部
         */
        if (null != list1) {
            pre.next = list1;
        }

        if (null != list2) {
            pre.next = list2;
        }
        return dummyNode.next;
    }
}
