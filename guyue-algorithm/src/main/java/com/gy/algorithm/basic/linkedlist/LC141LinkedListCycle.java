package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC141LinkedListCycle {

    public static void main(String[] args) {
        ListNode listNode = ListNode.buildListNode(new int[]{3, 2, 0, -4});
        LC141LinkedListCycle lc141LinkedListCycle = new LC141LinkedListCycle();
        boolean b = lc141LinkedListCycle.hasCycle(listNode);
        System.out.println(b);
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
