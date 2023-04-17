package com.gy.algorithm.basic.linkedlist;

public class RemoveDuplicatesFromSortedLinkedList83 {
    public static void main(String[] args) {
        RemoveDuplicatesFromSortedLinkedList83 a = new RemoveDuplicatesFromSortedLinkedList83();
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
