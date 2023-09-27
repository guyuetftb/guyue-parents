package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

/**
 * https://leetcode.cn/problems/SLwz0R/
 */
public class LCR021RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{1, 2, 3, 4, 5});
        LCR021RemoveNthFromEnd lcr021RemoveNthFromEnd = new LCR021RemoveNthFromEnd();
        lcr021RemoveNthFromEnd.removeNthFromEnd(head, 2);
        head.show();
    }

    /**
     * 方案:-------------------------------------------------------
     * 1. 借助dummyHead 方便操作
     * 2. 创建2个临时 dummyHead节点
     * 3. 先让1个 dummyHead 节点向后计算 n次
     * 4. dummyHead.next 为 终止条件, 2个临时节点都向后计算, 直到 dummyHead.next == null
     * 5. 以时, tempDummyHead.next 的元素, 就是要删除的前一个节点元素
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0, head);
        ListNode tmpDummyHead1 = dummyHead;

        /**
         * 3. 先让1个 dummyHead 节点向后计算 n次
         */
        ListNode tmpDummyHead2 = dummyHead;
        for (int i = 0; i < n; i++) {
            tmpDummyHead2 = tmpDummyHead2.next;
        }

        /**
         *  4. tmpDummyHead2.next == null 为 终止条件, 2个临时节点都向后计算
         *  5. 以时, tempDummyHead.next 的元素, 就是要删除的前一个节点元素
         */
        while (tmpDummyHead2.next != null) {
            tmpDummyHead1 = tmpDummyHead1.next;
            tmpDummyHead2 = tmpDummyHead2.next;
        }
        tmpDummyHead1.next = tmpDummyHead1.next.next;

        return dummyHead.next;
    }
}
