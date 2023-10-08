package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.ListNode;


/**
 * https://leetcode.cn/problems/partition-list/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 * <p>
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 */
public class LC86PartitionList {

    public static void main(String[] args) {
        ListNode listNode = ListNode.buildListNode(new int[]{1, 4, 3, 2, 5, 2});
        LC86PartitionList lc86PartitionList = new LC86PartitionList();
        ListNode partition = lc86PartitionList.partition(listNode, 3);
        partition.show();
    }

    /**
     * 根据题意，考虑通过「新建两个链表」实现原链表分割，算法流程为：
     * <p>
     * 1. 新建两个链表 sml_dummy , big_dummy ，分别用于添加所有「节点值 <x< x<x 」、「节点值 ≥x\geq x≥x 」的节点。
     * 2. 遍历链表 head 并依次比较各节点值 head.val 和 xxx 的大小：
     * 2-1 若 head.val < x ，则将节点 head 添加至链表 sml_dummy 最后面；
     * 2-2 若 head.val >= x ，则将节点 head 添加至链表 big_dummy 最后面；
     * 3. 遍历完成后，拼接 sml_dummy 和 big_dummy 链表。
     * 4. 最终返回头节点 sml_dummy.next 即可。
     */
    public ListNode partition(ListNode head, int num) {
        ListNode dummySmall = new ListNode(0);
        ListNode dummyBig = new ListNode(0);
        ListNode smallCur = dummySmall;
        ListNode bigCur = dummyBig;

        while (head != null) {
            //0. head 节点值 小于 num, 添加到 smallCur后面
            if (head.val < num) {
                smallCur.next = head;
                smallCur = smallCur.next;   // 后移
            }
            //1. head 节点值 大于 num, 添加到 bigCur后面
            else {

                bigCur.next = head;
                bigCur = bigCur.next;   // 后移
            }
            head = head.next;
        }
        // small 的链表 连接 big的链表
        smallCur.next = dummyBig.next;
        bigCur.next = null;
        return dummySmall.next;
    }
}
