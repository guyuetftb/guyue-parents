package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC2AddTwoNumbers {

    public static void main(String[] args) {
        ListNode l1 = ListNode.buildListNode(new int[]{9, 9, 9, 9, 9, 9, 9});
        ListNode l2 = ListNode.buildListNode(new int[]{9, 9, 9, 9});
        LC2AddTwoNumbers lc2AddTwoNumbers = new LC2AddTwoNumbers();
        ListNode listNode = lc2AddTwoNumbers.addTwoNumbers(l1, l2);
        listNode.show();
    }

    public ListNode addTwoNumbers(ListNode list1, ListNode list2) {
        if (null == list1 && null != list2) {
            return list2;
        }

        if (null != list1 && null == list2) {
            return list1;
        }

        ListNode dummy1 = new ListNode(-1, list1);
        ListNode dummy2 = new ListNode(-1, list2);
        ListNode dummyNew = new ListNode(-1);

        ListNode head1 = dummy1.next;
        ListNode head2 = dummy2.next;
        ListNode cur = dummyNew;
        int preVal = 0;
        while (null != head1 || null != head2) {
            int val1 = null == head1 ? 0 : head1.val;
            int val2 = null == head2 ? 0 : head2.val;

            // 1节点值 + 2节点值 + 上次进位
            int tmpVal = preVal + val2 + val1;
            // 上次值已经累加, 清除值
            preVal = 0;
            if (tmpVal >= 10) {
                preVal = 1;
            }

            ListNode newNode = new ListNode(tmpVal % 10);
            cur.next = newNode;
            cur = cur.next;

            if (null != head1) {
                head1 = head1.next;
            }
            if (null != head2) {
                head2 = head2.next;
            }
        }

        if (preVal > 0) {
            cur.next = new ListNode(1);
        }

        return dummyNew.next;
    }
}
