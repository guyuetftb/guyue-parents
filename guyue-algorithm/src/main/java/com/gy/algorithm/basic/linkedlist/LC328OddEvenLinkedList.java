package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

/**
 * https://leetcode.cn/problems/odd-even-linked-list/
 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
 */
public class LC328OddEvenLinkedList {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{2, 1, 3, 5, 6, 4, 7});
        LC328OddEvenLinkedList lc328OddEvenLinkedList = new LC328OddEvenLinkedList();
        ListNode listNode = lc328OddEvenLinkedList.oddEvenList(head);
        System.out.println(listNode.val);
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 备份 偶数节点的 第1个节点
        ListNode evenHeadBak = head.next;

        // 奇数指针
        ListNode oddCurPoint = head;

        // 偶数指针
        ListNode evenCurPoint = head.next;


        // 2->1->3->5->6->4->7->null
        while (evenCurPoint != null && evenCurPoint.next != null) {
            /**
             * 1. 当前奇数节点, 指向 偶数节点的下一个节点(也是奇数节点)
             * 2. 奇数节点指针后移
             */
            oddCurPoint.next = evenCurPoint.next;
            oddCurPoint = oddCurPoint.next;

            /**
             * 1. 当前偶数节点, 指向 奇数 节点的下一个节点
             * 2. 偶数节点 后移
             */
            evenCurPoint.next = oddCurPoint.next;
            evenCurPoint = evenCurPoint.next;
        }

        // oddCurPoint, evenCurPoint 都已经到最后, 把奇, 偶链表串连
        oddCurPoint.next = evenHeadBak;
        return head;
    }
}
