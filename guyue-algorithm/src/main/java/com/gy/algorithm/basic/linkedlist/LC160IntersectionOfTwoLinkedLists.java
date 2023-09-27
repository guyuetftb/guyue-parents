package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

import static com.gy.algorithm.basic.common.ListNode.buildListNode;


/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/
 */
public class LC160IntersectionOfTwoLinkedLists {


    public static void main(String[] args) {
        ListNode listA = buildListNode(new int[]{4, 1, 8, 4, 5});
        ListNode listB = buildListNode(new int[]{5, 6, 1, 8, 4, 5});
        LC160IntersectionOfTwoLinkedLists lc160IntersectionOfTwoLinkedLists = new LC160IntersectionOfTwoLinkedLists();
        ListNode intersectionNode = lc160IntersectionOfTwoLinkedLists.getIntersectionNode(listA, listB);
        if (null == intersectionNode) {
            System.out.println("null");
        } else {
            System.out.println(intersectionNode.val);
        }
    }

    /**
     * 思路:-------------------
     * pointA: 走过的路径为: A链+B链
     * pointB: 走过的路径为: B链+A链
     * pointA和pointB走过的长度都相同，都是A链和B链的长度之和，相当于将两条链从尾端对齐，
     * 如果相交，则会提前在相交点相遇，如果没有相交点，则会在最后相遇。
     * <p>
     * <p>
     * pA:4, 1, 8, 4, 5,null, 5,    6,1,8,4,5,null
     * pB:5, 6, 1, 8,4, 5,    null, 4,1,8,4,5,null
     */
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
