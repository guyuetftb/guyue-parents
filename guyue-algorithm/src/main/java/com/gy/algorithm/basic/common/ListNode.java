package com.gy.algorithm.basic.common;

import java.util.Objects;

public class ListNode {
    public int val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return val == listNode.val && Objects.equals(next, listNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }

    public static ListNode buildListNode(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return null;
        }
        ListNode head = new ListNode(nums[0]);
        ListNode curNode = head;
        for (int index = 1; index < nums.length; index++) {
            ListNode newNode = new ListNode(nums[index]);
            curNode.next = newNode;
            curNode = newNode;
        }
        return head;
    }

    public void show() {
        ListNode listNode = this;
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

}
