package com.gy.algorithm.basic.linkedlist;

public class RotateLinkedList61 {
    public static void main(String[] args) {

    }

    /**
     * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k <= 0) {
            return head;
        }


        // 先获取链表的长度
        int length = 0;
        ListNode tempHead = head;
        while (tempHead != null) {
            tempHead = tempHead.next;
            length++;
        }

        // 由于题目中的 k 会超过链表的长度，因此进行一个取余的操作
        // 比如 k = 1000，len = 999
        // 实际上就是将链表每个节点向右移动 1000 % 999 = 1 个位置就行了
        // 因为链表中的每个节点移动 len 次会回到原位
        k = k % length;

        // 4、接下来设置两个指针指向链表的头节点
        // 这两个指针的目的是去寻找出: 旋转之前的尾节点位置、旋转成功之后的尾节点位置

        ListNode formerCur = head;  // 旋转之前尾节点
        ListNode latterCur = head;  // 旋转之后尾节点

        // 旋转之前尾结点的位置
        for (int i = 0; i < k; i++) {
            formerCur = formerCur.next;
        }


        while (formerCur.next != null) {
            formerCur = formerCur.next;
            latterCur = latterCur.next;
        }

        formerCur.next = head;

        ListNode newHead = latterCur.next;

        latterCur.next = null;

        return newHead;
    }
}
