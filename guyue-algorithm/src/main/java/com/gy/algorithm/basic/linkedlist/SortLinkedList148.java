package com.gy.algorithm.basic.linkedlist;

public class SortLinkedList148 {
    public static void main(String[] args) {

        SortLinkedList148 a = new SortLinkedList148();
        a.sortList(null);
    }

    /**
     * 题目要求时间空间复杂度分别为 O(nlogn) 和 O(1)
     * 使用快排，归并排序解决当前问题
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {

        // 1. 获取 链表长度
        ListNode node = head;
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }

        // 设置一个虚拟头节点， 因为可能会操作到原先链表的头节点.
        ListNode dummy = new ListNode();
        dummy.next = head;
        dummy.val = -1;

        // 2. 执行归并排序
        // 利用for循环，执行归并排序
        // 长度为1的链表，合并成为长度为2的链表
        // 长度为2的链表，合并成为长度为4的链表
        // 长度为4的链表，合并成为长度为8的链表
        // 长度为8的链表，合并成为长度为16的链表
        // 而由于合并过程中操作的是链表，所以需要有断链和重新连接的过程。
        // 也有长度不相同的2个链表，合并.
        // 每次归并都把链表变为原先的2倍，直到链表超过 原先列表的长度

        // subLength 就是 每次排序的链表长度
        // 步长是 每次都增 乘2个.
        // 整个归并分3个步骤
        // 1. 不停划分，直到无法划分
        // 2. 两两合并
        // 3. 生次合并之后的结果 都需要连接起来
        for (int subLength = 1; subLength < length; subLength *= 2) {
            // 把每次排序好的节点连接到dummy
            // pre 代表已经排序的尾节点位置
            // 每次执行For循环，就代表 subLength要增涨2倍, 进行下一输合并.
            // 1,2,4,8的形式递增
            ListNode prevNode = dummy;

            // dummy 后面节点才是原链表的节点，需要把他们进行划分
            // curr 节点表示所有正准备排序的那 节点的【尾节点】
            ListNode currNode = dummy.next;

            while (currNode != null) {

                // 每次都是2个子链表开始合并
                // 先找出 【左子链表】,长度为 subLength
                ListNode head1 = currNode;
                // 当subLength = 1时，这个循环并不会执行. 同时判断currNode的下一个节点,不能为空
                for (int i = 1; i < subLength && currNode.next != null; i++) {
                    currNode = currNode.next;
                }

                // 2. 再寻找出【右子链表】，长度最多为subLength，甚至有可能为0
                // -- 以此 curr已经是第一个链表的最后一个节点了.
                ListNode head2 = currNode.next;

                // 将 左，右子链表断开
                currNode.next = null;

                // curr 到『右子链表』头部
                currNode = head2;

                // 通过for循环找出右子链表，需要排序的节点，
                // 右子链表的节点个数可能达不到subLength，甚至只有1个，或0个节点
                for (int i = 1; i < subLength && currNode != null && currNode.next != null; i++) {
                    currNode = currNode.next;
                }

                // 获取到【右子链表】之后，需要把它和后续链表断开
                // next 表示 本轮subLength下，接下来需要排序的那些节点的【首节点】
                ListNode nextNode = null;

                // 如果 curr != null，那么说明【右子链表】的节点个数达到了 subLength 个，并且后续还有节点
                if (currNode != null) {
                    // 记录一下后面节点, 即 右链表 后面的节点
                    nextNode = currNode.next;

                    // 再将【右子链表】和后续链表断开
                    currNode.next = null;
                }

                // 将【左子链表】与【右子链表】合并
                ListNode merged = mergeTwoLists(head1, head2);

                // 合并之后，结果 需要链表到前一个链表
                prevNode.next = merged;

                // prev 来到链表的尾部，是下一个即将合成链表之后的，前一个链表的尾节点
                while (prevNode.next != null) {
                    prevNode = prevNode.next;
                }

                // curr 来到 next，处理后面的节点
                currNode = nextNode;
            }
        }
        return dummy.next;
    }

    // 合并两个有序链表的代码
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 一开始设置一个虚拟节点，它的值为 -1，它的值可以设置为任何的数，因为我们根本不需要使用它的值
        ListNode dummy = new ListNode(-1);

        // 设置一个指针，指向虚拟节点
        ListNode pre = dummy;

        // 通过一个循环，不断的比较 l1 和 l2 中当前节点值的大小，直到 l1 或者 l2 遍历完毕为止
        while (l1 != null && l2 != null) {
            // 如果 l1 当前节点的值小于等于了 l2 当前节点的值
            if (l1.val <= l2.val) {
                // 让 pre 指向节点的 next 指针指向这个更小值的节点
                // 即指向 l1
                pre.next = l1;
                // 让 l1 向后移动
                l1 = l1.next;
            } else {
                // 让 pre 指向节点的 next 指针指向这个更小值的节点
                // 即指向 l2
                pre.next = l2;
                // 让 l2 向后移动
                l2 = l2.next;
            }
            // 让 pre 向后移动
            pre = pre.next;
        }

        // 跳出循环后，l1 或者 l2 中可能有剩余的节点没有被观察过
        // 直接把剩下的节点加入到 pre 的 next 指针位置

        // 如果 l1 中还有节点
        if (l1 != null) {
            // 把 l1 中剩下的节点全部加入到 pre 的 next 指针位置
            pre.next = l1;
        }

        // 如果 l2 中还有节点
        if (l2 != null) {
            // 把 l2 中剩下的节点全部加入到 pre 的 next 指针位置
            pre.next = l2;
        }

        // 最后返回虚拟节点的 next 指针
        return dummy.next;
    }
}
