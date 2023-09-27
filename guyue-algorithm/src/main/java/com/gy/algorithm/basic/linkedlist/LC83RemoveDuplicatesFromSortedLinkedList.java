package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC83RemoveDuplicatesFromSortedLinkedList {
    public static void main(String[] args) {
        LC83RemoveDuplicatesFromSortedLinkedList a = new LC83RemoveDuplicatesFromSortedLinkedList();
        a.deleteDuplicates(null);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode curNode = head;
        while (curNode != null && curNode.next != null) {
            /**
             * 当前节点的val 等于 下一个节点的 val
             */
            if (curNode.val == curNode.next.val) {
                curNode.next = curNode.next.next;
            }/**
             * 当前节点的val  不等于 下一个节点的 val
             */
            else {
                curNode = curNode.next;
            }
        }
        return head;
    }
}
