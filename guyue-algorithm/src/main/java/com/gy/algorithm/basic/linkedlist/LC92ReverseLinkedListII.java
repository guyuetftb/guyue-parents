package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC92ReverseLinkedListII {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{1, 2, 3, 4, 5});
        int left = 2;
        int right = 4;
        LC92ReverseLinkedListII lc92ReverseLinkedListII = new LC92ReverseLinkedListII();
        ListNode listNode = lc92ReverseLinkedListII.reverseBetween(head, left, right);
        listNode.show();
    }

    /**
     * 1、我们定义两个指针，分别称之为 g(guard 守卫) 和 p(point)。
     * 我们首先根据方法的参数 m 确定 g 和 p 的位置。将 g 移动到第一个要反转的节点的前面，
     * 将 p 移动到第一个要反转的节点的位置上。我们以 m=2，n=4为例。
     * 2、将 p 后面的元素删除，然后添加到 g 的后面。也即头插法。
     * 3、根据 m 和 n 重复步骤（2）
     * 4、返回 dummyHead.next
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 定义一个dummyHead, 方便处理
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        // 初始化指针
        ListNode guardPoint = dummyHead;
        ListNode curPoint = dummyHead.next;

        // 将指 guardPoint, curPoint 移到相应的位置
        // guardPoint 位置比 curPoint 位置靠前1个
        for (int step = 0; step < left - 1; step++) {
            guardPoint = guardPoint.next;
            curPoint = curPoint.next;
        }

        // 头插法: 插入节点
        // 即: 逐个将 curPoint 指向的元素, 插入到 guardPoint的后面, 也就是 插入到 curPoint的前
        for (int index = 0; index < right - left; index++) {
            // 暂存 当前节点的下一个节点, 这是需要删除的节点.
            ListNode removed = curPoint.next;
            // 当前节点指向 下一个节点的next, 即，指针后移2次
            curPoint.next = curPoint.next.next;

            // 删除节点的next 指向 guardPoint的next
            removed.next = guardPoint.next;
            // 然后让 guardPoint.next 指向要删除的节点
            guardPoint.next = removed;
        }

        return dummyHead.next;
    }
}
