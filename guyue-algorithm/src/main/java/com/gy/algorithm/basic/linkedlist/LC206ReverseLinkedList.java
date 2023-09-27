package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC206ReverseLinkedList {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{});
        LC206ReverseLinkedList lc206ReverseLinkedList = new LC206ReverseLinkedList();
        lc206ReverseLinkedList.reverseList(head);
        head.show();
    }

    public ListNode reverseList(ListNode head) {
        ListNode curNode = head;
        ListNode preNode = null;

        /**
         * 1 --> 2 --> 3 --> 4 --> 5 --> null
         */
        while (curNode != null) {
            /**
             * 1. 暂存后继节点 cur.next
             *  第一次为: 2
             */
            ListNode tmp = curNode.next;

            /**
             * 2. 修改 next 引用指向
             *  第一次为: null
             */
            curNode.next = preNode;

            /**
             * 3. preNode 暂存 curNode
             *  第一次为: preNode = 1
             */
            preNode = curNode;

            /**
             * 4. curNode 访问下一节点
             *  第一次为: 2
             */
            curNode = tmp;
        }

        return preNode;
    }

    /**
     * 本题采用递归的思想
     * 1.  假如链表是: 比如原链表为 1 --> 2 --> 3 --> 4 --> 5 --> null
     * 2.   第1步: 反转以1为head节点链表
     * 3.   第2步: 反转以2为head节点的链表
     * 4.   第3步: 反转以3为head节点的链表
     * <p>
     * <p>
     * <p>
     * 主体思想
     * 1. 反转以生个节点为head的子链表
     * 2. head节点为null, 或者 head.next = null，退出循环
     * 3. 反转 尾部节点的next 值
     * 4. 返回当前节点
     */
    public ListNode reverseListBak(ListNode head) {
        if (head == null || head.next == null) {
            // 链表: 1 --> 2 --> 3 --> 4 --> 5 --> null
            // 第一次执行到此: 是node = 5，node.next = null，返回5.
            return head;
        }

        // 反转当前Node的next链表
        // 接收尾部节点
        // 第一次执行到此: head = 4, head.next = 5l，返回: currentNode = 5
        ListNode currentNode = reverseList(head.next);

        // 第1次:
        // -> head = 4
        //  -> head.next = 5
        // -> head.next.next = 5.next = null
        // -> 5.next = head = 4;
        // 第2次:
        // -> head = 3
        // -> head.next = 4
        // -> head.next.next = 4.next = null;
        // -> 4.next = 3
        head.next.next = head;

        // head = 4
        // head.next 原来 = 5, 现在指向 null
        head.next = null;

        // 返回: currentNode = 5
        return currentNode;
    }
}
