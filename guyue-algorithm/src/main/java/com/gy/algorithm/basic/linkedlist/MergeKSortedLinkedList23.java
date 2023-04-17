package com.gy.algorithm.basic.linkedlist;

public class MergeKSortedLinkedList23 {

    public static void main(String[] args) {

    }

    public ListNode mergeKLists(ListNode[] lists) {
        // 数组长度为0
        if (lists.length == 0) {
            return null;
        }

        // 数组长度为1
        if (lists.length == 1) {
            return lists[0];
        }

        // 数组长度为2
        if (lists.length == 2) {
            return mergeTwoLists(lists[0], lists[1]);
        }

        // 中间数组节点
        int mid = lists.length / 2;

        // 说明链表个数大于2个, 将整个链表拆分为2个数组
        // 分别合并
        ListNode[] sub1 = new ListNode[mid];
        for (int i = 0; i < mid; i++) {
            sub1[i] = lists[i];
        }

        ListNode[] sub2 = new ListNode[lists.length - mid];
        for (int i = mid; i < lists.length; i++) {
            sub2[i - mid] = lists[i];
        }

        // 合并左半个链表数组
        ListNode subListNode1 = mergeKLists(sub1);
        // 合并右半个链表数组
        ListNode subListNode2 = mergeKLists(sub2);
        // 合并2个结果
        return mergeTwoLists(subListNode1,subListNode2);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 一开始设置一个虚拟节点，它的值为 -1，它的值可以设置为任何的数，因为我们根本不需要使用它的值
        ListNode dummy = new ListNode(-1);

        // 设置一个指针，指向虚拟节点
        ListNode pre = dummy;

        // 通过一个循环，不断的比较 l1 和 l2 中当前节点值的大小，直到 l1 或者 l2 遍历完毕为止
        while (l1 != null && l2 != null) {
            // 如果 l1 当前节点的值小于等于了 l2 当前节点的值
            if (l1.val <= l2.val) {
                // 让 pre 指向节点的 next 指针指向这个更小值的节点
                // 即指向 l1
                pre.next = l1;
                // 让 l1 向后移动
                l1 = l1.next;
            } else {
                // 让 pre 指向节点的 next 指针指向这个更小值的节点
                // 即指向 l2
                pre.next = l2;
                // 让 l2 向后移动
                l2 = l2.next;
            }
            // 让 pre 向后移动
            pre = pre.next;
        }

        // 跳出循环后，l1 或者 l2 中可能有剩余的节点没有被观察过
        // 直接把剩下的节点加入到 pre 的 next 指针位置

        // 如果 l1 中还有节点
        if (l1 != null) {
            // 把 l1 中剩下的节点全部加入到 pre 的 next 指针位置
            pre.next = l1;
        }

        // 如果 l2 中还有节点
        if (l2 != null) {
            // 把 l2 中剩下的节点全部加入到 pre 的 next 指针位置
            pre.next = l2;
        }

        // 最后返回虚拟节点的 next 指针
        return dummy.next;
    }
}
