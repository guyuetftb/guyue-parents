package com.gy.algorithm.basic.linkedlist;

public class MergeTwoSortedLinkedList21 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // 1. 初始化变量, dummy, pre
        //      -> dummy 虚拟节点, pre指向新链表最后的节点
        ListNode dummy = new ListNode();
        dummy.val = -1;
        ListNode pre = dummy;

        while (list1 != null && list2 != null) {
            // 2. 判断2个有序链表当前节点值大小.
            // 3. 让 pre.next 指向 节点值大的节点

            if (list1.val <= list2.val) {
                pre.next = list1;

                // 5. list1, list2向后移动
                list1 = list1.next;
            } else {
                pre.next = list2;

                // 5. list1, list2向后移动
                list2 = list2.next;
            }
            pre = pre.next;
        }

        // 6. 将剩余链表添加到新链表尾部
        if (null != list1) {
            pre.next = list1;
        }
        if (null != list2) {
            pre.next = list2;
        }
        return dummy.next;
    }
}
