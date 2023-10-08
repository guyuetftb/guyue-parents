package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC25ReverseNodesInKGroup {

    public static void main(String[] args) {

    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (null == head || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.val = -1;
        dummy.next = head;

        ListNode preNode = dummy;
        ListNode endNode = dummy;

        while (endNode.next != null) {

            for (int i = 0; i < k; i++) {
                // end节点不断向后移动
                endNode = endNode.next;
                // 如果移动过程中, 长度为够 k ,跳出
                if (null == endNode) {
                    break;
                }
            }

            // 如果移动过程中, 长度为够 k ,跳出
            if (null == endNode) {
                break;
            }

            // 下一批需要翻转的节点
            ListNode nextNode = endNode.next;

            // 为了翻转链表, 断开
            endNode.next = null;

            // 翻转链表 head
            ListNode startNode = preNode.next;
            // 为了翻转链表, 断开
            preNode.next = null;

            // 此时返回的是 tail 节点
            preNode.next = reverse(startNode);

            // 此时, startNode 节点是 tail节点
            startNode.next = nextNode;

            preNode = startNode;
            endNode = startNode;
        }
        return dummy.next;
    }

    // 1 -> 2 -> 3 -> 4
    private ListNode reverse(ListNode head) {
        if (null == head || head.next == null) {
            return head;
        }

        ListNode cur = reverse(head.next);

        head.next.next = head;

        head.next = null;

        return cur;
    }

}
