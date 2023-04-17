package com.gy.algorithm.basic.linkedlist;

public class RemoveDuplicatesFromSortedLinkedListII82 {

    public static void main(String[] args) {

    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        if (head.val != head.next.val) {

            ListNode newNode = deleteDuplicates(head.next);

            head.next = newNode;

            return head;
        } else {
            ListNode newNode = head.next;
            while (newNode != null && head.val == newNode.val) {
                newNode = newNode.next;
            }
            return deleteDuplicates(newNode);
        }
    }
}
