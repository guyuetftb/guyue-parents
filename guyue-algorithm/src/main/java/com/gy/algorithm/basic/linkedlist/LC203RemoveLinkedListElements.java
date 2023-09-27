package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC203RemoveLinkedListElements {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{1, 2, 6, 3, 4, 5, 6});
        LC203RemoveLinkedListElements lc203RemoveLinkedListElements = new LC203RemoveLinkedListElements();
        lc203RemoveLinkedListElements.removeElements(head, 4);
        head.show();
    }

    /**
     * 方案:--------------------------------------------------------------
     * 1. 他那1个虚拟头节点dummyNode, 方便操作
     * 2.
     */
    public ListNode removeElements(ListNode head, int val) {
        if (null == head) {
            return null;
        }

        // 虚拟头节点
        ListNode dummyNode = new ListNode();
        dummyNode.val = -1;
        dummyNode.next = head;

        ListNode preNode = dummyNode;
        ListNode curNode = head;

        while (curNode != null) {
            /**
             * 如果删除的值等于 当前节点值, preNode指向 curNode的后一个节点
             */
            if (curNode.val == val) {
                preNode.next = curNode.next;
            } else {
                /**
                 * 1. 注意: 这一步不能放到 else 外面. 只有val 不相等才后移, 相等 curNode已经被调过, 不用再后移
                 * 2. preNode指针后移
                 */
                preNode = curNode;
            }
            /** 2. curNode 指针后移*/
            curNode = curNode.next;
        }

        return dummyNode.next;
    }
}
