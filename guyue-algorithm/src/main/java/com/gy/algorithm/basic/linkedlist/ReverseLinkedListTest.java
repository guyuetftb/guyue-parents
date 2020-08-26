package com.gy.algorithm.basic.linkedlist;

/**
 * @ClassName ReverseLinkedListTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 20:13
 */
public class ReverseLinkedListTest {

	public static void main(String[] args) {

		MyLinkedList<Integer> list1 = new MyLinkedList<>();
		list1.add(1);
		list1.add(3);
		list1.add(5);
		list1.add(7);
		list1.add(9);
		list1.add(2);
		list1.add(4);
		list1.add(6);
		list1.add(8);
		list1.add(10);

		for (int i = 0; i < list1.size; i++) {
			System.out.println(list1.get(i).getE());
		}

		System.out.println("Swap Two linked Node ----------------------");
		list1.reverse();
		for (int i = 0; i < list1.size; i++) {
			System.out.println(list1.get(i).getE());
		}

	}


	private static class MyLinkedList<E> {

		private LinkedNode preHead;
		private int size;

		public MyLinkedList() {
			preHead = new LinkedNode();
			size = 0;
		}

		public void reverse() {
			preHead.next = reverse(preHead.next);
		}

		public LinkedNode reverse(LinkedNode head) {
			// TODO 1 -> 2 -> 3 -> 4 -> 5
			// 		把整个链表反转, 但是 preHead不反转
			LinkedNode cur = head;
			LinkedNode pre = null;
			while (cur != null) {

				// 1. 把 cur.next 赋给 curNext, 暂存.
				LinkedNode curNext = cur.next;

				// cur 反指, 把 pre -> cur 变成  pre <- cur.next.
				cur.next = pre;

				// pre 赋为 cur, pre 向前递进一个元素.
				pre = cur;

				// cur 赋为 下一个节点 curNext 2; cur 向前递进一个元素.
				cur = curNext;
			}

			return pre;
		}

		public int getSize() {
			return size;
		}

		public void add(E e) {
			add(size, e);
		}

		public LinkedNode get(int index) {
			if (0 > index || index >= size) {
				throw new IllegalArgumentException("Get failed. Illegal index.");
			}

			LinkedNode cur = preHead.next;
			for (int i = 0; i < index; i++) {
				cur = cur.next;
			}

			return cur;
		}

		public void add(int index, E e) {
			if (index < 0 || index > size) {
				throw new IllegalArgumentException("Add failed. Illegal index.");
			}

			LinkedNode prev = preHead;
			for (int i = 0; i < index; i++) {
				prev = prev.next;
			}
			prev.next = new LinkedNode(e, prev.next);
			size++;
		}
	}
}
