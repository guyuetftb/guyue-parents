package com.gy.algorithm.basic.linkedlist;

public class RemoveLinkedListElements203 {

    public static void main(String[] args) {

    }

    public ListNode removeElements(ListNode head, int val) {
        if (null == head ) {
            return null;
        }

        // 虚拟头节点
        ListNode dummy = new ListNode();
        dummy.val = -1;
        dummy.next = head;

        ListNode pre = dummy;
        ListNode cur = head;

        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                // pre指针后移
                pre = cur;
            }

            // cur指针后移
            cur = cur.next;
        }

        return dummy.next;
    }
}
