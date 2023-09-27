package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class SwapNodesInPairsLinkedList24 {

    public static void main(String[] args) {

    }

    public ListNode swapPairs(ListNode head) {

        // 寻找递归终止条件
        // 1、head 指向的结点为 null
        // 2、head 指向的结点的下一个结点为 null
        // 在这两种情况下，一个节点或者空节点无论怎么交换操作，都是原来的 head
        if (head == null || head.next == null) {
            return head;
        }

        //第1次执行: subHead = 5
        // 第2次执行: subHead = 4
        ListNode subHead = swapPairs(head.next.next);

        // 第1次: head = 3, head.next = 4;
        // 第2次: head = 1, head.next = 2;
        ListNode headNext = head.next;

        //第1次:4->3
        //第2次:2->1
        headNext.next = head;

        //第1次:3->5
        //第2次:2->4
        head.next = subHead;

        // 第1次: 返回4->3->5
        // 第2次: 返回2->4->4
        return headNext;
    }
}
