package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC82RemoveDuplicatesFromSortedLinkedListII {

    public static void main(String[] args) {
        ListNode head = ListNode.buildListNode(new int[]{1, 1, 2, 3, 3});
        LC82RemoveDuplicatesFromSortedLinkedListII lc82RemoveDuplicatesFromSortedLinkedListII = new LC82RemoveDuplicatesFromSortedLinkedListII();
        ListNode listNode = lc82RemoveDuplicatesFromSortedLinkedListII.deleteDuplicates(head);
        listNode.show();
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (null == head) {
            return null;
        }

        ListNode curNode = head;
        while (curNode != null && curNode.next != null) {
            /**
             * 当前节点的val 等于 下一个节点的 val
             */
            if (curNode.val == curNode.next.val) {
                curNode.next = curNode.next.next;
            }
            /**
             * 当前节点的val  不等于 下一个节点的 val
             */
            else {
                curNode = curNode.next;
            }
        }
        return head;
    }

    /**
     * 方案:----------------------------------------------------------------------------
     * 1. 递归调用, 删除重复节点
     */
    public ListNode deleteDuplicatesBak(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        /**
         * 1. 当前节点的val != 下一节点的val
         * 2. 递归判断
         */
        if (head.val != head.next.val) {

            ListNode newNode = deleteDuplicates(head.next);
            head.next = newNode;
            return head;
        } else {

            /**
             * 1. 判断后续重复节点
             * 2. 如果 nextNode的val == nextNode.val, nextNode 接着向后移动, 直到不相等
             * 3. 不相等, 调用 deleteDuplicates 递归
             */
            ListNode nextNode = head.next;
            while (nextNode != null && head.val == nextNode.val) {
                nextNode = nextNode.next;
            }
            return deleteDuplicates(nextNode);
        }
    }
}
