package com.gy.algorithm.basic.linkedlist;


/**
 * > 1. 使用快，慢指针来解决。
 * > 2. 退出条件: fast指针不为空，且 fast.next也不为空，退出循环。
 * > 3. 操作: slow向后称动一步；fast向后移动2步；
 */
public class MiddleLinkedList876 {
    public static void main(String[] args) {

    }

    public ListNode middleNode(ListNode head) {
        // 1.两个指针都指向头节点
        ListNode slow = head;
        ListNode fast = head;

        // 2-1. fast && fast.next 不为null, 向后移动2步
        //2-2slow 向后移动一步
        while (null != fast && null != fast.next) {
            fast = fast.next.next;

            slow = slow.next;

        }

        // 3. 返回间节点.
        return slow;
    }
}
