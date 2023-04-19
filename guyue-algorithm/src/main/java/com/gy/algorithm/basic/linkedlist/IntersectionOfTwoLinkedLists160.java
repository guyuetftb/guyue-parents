package com.gy.algorithm.basic.linkedlist;

public class IntersectionOfTwoLinkedLists160 {

    public static void main(String[] args) {

    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pointA = headA;
        ListNode pointB = headB;
        while (pointA != pointB) {
            if (pointA == null) {
                // 链表A到达尾节点
                pointA = headB;
            } else {
                // 没有到达尾节点, 接着向后移动
                pointA = pointA.next;
            }

            if (pointB == null) {
                // 链表B到达尾节点
                pointB = headA;
            } else {
                // 没有到达尾节点, 接着向后移动
                pointB = pointB.next;
            }
        }

        //  返回共同起始节点
        return pointA;
    }
}
