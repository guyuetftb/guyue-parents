package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;

public class LC234PalindromeLinkedList {

    public static void main(String[] args) {


    }

    public boolean isPalindrome(ListNode head) {

        // 1. 找到中间节点
        //      使用快,慢指针
        // 2. 从中间节点到尾节点, 这个子链表, 反转链表

        // 只有1个节点
        if (head == null || head.next == null) {
            return true;
        }

        // 有2个节点
        if (head.next.next == null) {
            return head.val == head.next.val;
        }

        ListNode fast = head;
        ListNode slow = head;


        while (fast.next != null && fast.next.next != null) {
            // slow 向后移动一步
            // slow 就是链表的中间节点
            slow = slow.next;

            // fast 向后移动2步
            fast = fast.next.next;
        }

        // 反转链表
        ListNode rightHead = reverse(slow.next);

        // 左链表的头节点
        ListNode leftHead = head;
        while (rightHead != null) {
            if (rightHead.val != leftHead.val) {
                return false;
            }

            // 右链表向右移动
            rightHead = rightHead.next;

            // 左链表向右移动
            leftHead = leftHead.next;
        }

        return true;

    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverse(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        // 不断的通过递归调用，直到无法递归下去，递归的最小粒度是在最后一个节点
        // 因为到最后一个节点的时候，由于当前节点 head 的 next 节点是空，所以会直接返回 head
        ListNode cur = reverse(head.next);

        // 比如原链表为 1 --> 2 --> 3 --> 4 --> 5
        // 第一次执行下面代码的时候，head 为 4，那么 head.next = 5
        // 那么 head.next.next 就是 5.next ，意思就是去设置 5 的下一个节点
        // 等号右侧为 head，意思就是设置 5 的下一个节点是 4

        // 这里出现了两个 next
        // 第一个 next 是「获取」 head 的下一节点
        // 第二个 next 是「设置」 当前节点的下一节点为等号右侧的值
        head.next.next = head;

        // head 原来的下一节点指向自己，所以 head 自己本身就不能再指向原来的下一节点了
        // 否则会发生无限循环
        head.next = null;

        // 我们把每次反转后的结果传递给上一层
        return cur;
    }
}
