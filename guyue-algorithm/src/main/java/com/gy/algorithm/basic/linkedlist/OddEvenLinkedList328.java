package com.gy.algorithm.basic.linkedlist;

public class OddEvenLinkedList328 {

    public static void main(String[] args) {

    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode oddCurPoint = head;

        ListNode evenCurPoint = head.next;

        ListNode evenHead = head.next;

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6
        while (evenCurPoint != null && evenCurPoint.next != null) {
            // 当前奇数节点, 指向 偶数节点的下一个节点(也是奇数节点)
            oddCurPoint.next = evenCurPoint.next;

            // 奇数节点 后移
            oddCurPoint = oddCurPoint.next;

            // 当前偶数节点, 指向 奇数 节点的下一个节点
            evenCurPoint.next = oddCurPoint.next;

            // 偶数节点 后移
            evenCurPoint = evenCurPoint.next;
        }

        // oddCurPoint, evenCurPoint 都已经到最后, 把奇, 偶链表串连
        oddCurPoint.next = evenHead;

        return head;
    }
}
