package com.gy.algorithm.basic.linkedlist;

public class RemoveDuplicatesFromSortedLinkedListII82Iteration {

    public static void main(String[] args) {

    }

    public ListNode deleteDuplicatesIteration(ListNode head) {
        return deleteDuplicatesIteration(head);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {

            if (cur.next.val == cur.next.next.val) {

                // 记录 value 重复的具体信息
                int value = cur.next.val;

                // 循环判断 next是否为null, 同时 next.val 的值 与 value的相同
                // 相同就跳过 next节点, 让 cur 指向 下下个节点: 即: next.next
                while (cur.next != null && cur.next.val == value) {
                    cur.next = cur.next.next;
                }
            } else {
                // next.val  != next.next.val 说明 下个节点的value，与下下个节点的value不同
                // 指针后移
                cur = cur.next;
            }
        }

        // 返回虚拟头节点的下个节点.
        return dummy.next;
    }
}
