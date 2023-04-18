package com.gy.algorithm.basic.linkedlist;

public class LinkedListCycleII142 {

    public static void main(String[] args) {

    }

    public ListNode detectCycle(ListNode head) {
        // 快慢指针的方式
        ListNode fast = head;

        ListNode slow = head;

        while (fast != null && fast.next != null) {
            // 慢指针向前移动1步
            slow = slow.next;

            // 快指针向前移动2步
            fast = fast.next.next;

            // 根据初等数学环形公式, fast指针在一定时间内, 肯定会与slow指针相遇
            // slow 指针与 fast 指针相遇
            if (fast == slow) {
                ListNode tempNodeA = fast;

                ListNode tempNodeB = head;

                while (tempNodeA != tempNodeB) {
                    tempNodeA = tempNodeA.next;
                    tempNodeB = tempNodeB.next;
                }
                return tempNodeA;
            }
        }

        return null;
    }
}
