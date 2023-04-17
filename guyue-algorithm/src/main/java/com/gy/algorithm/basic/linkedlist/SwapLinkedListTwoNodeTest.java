package com.gy.algorithm.basic.linkedlist;

import java.util.LinkedList;

/**
 * @ClassName SwapLinkedListTwoNodeTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 18:55
 */
public class SwapLinkedListTwoNodeTest {

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

		list1.swapTwoNodeFowWholeLinkedList();
		System.out.println("Swap Two linked Node ----------------------");
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

		public void swapTwoNodeFowWholeLinkedList() {
			LinkedNode pre = preHead;
			LinkedNode firstNode = pre.next;

			// TODO 链表为空 或 链表只有一个节点, 不交换.
			if (pre == null || firstNode == null || firstNode.next == null) {
				return;
			}

			LinkedNode secondNode = firstNode.next;
			while (firstNode != null && secondNode != null) {
				// TODO 先备份 firstNode.
				LinkedNode tmp = firstNode;

				// TODO pre 指向 secondNode;
				pre.next = secondNode;

				// TODO firstNode 的下一个, 指向第3个节点.
				firstNode.next = secondNode.next;

				// TODO secondNode.next 指向原来第1个节点.
				secondNode.next = tmp;

				// TODO 重置循环条件
				pre = firstNode;
				firstNode = pre.next;
				if (firstNode == null) {
					// TODO	注意: 这里的设置要与 方法开始时, 设置一致, 否则程序不能正确运行.
					// 		把 '最后' 两个节点换过之后, 需要让 pre 指向 下一次循环节点的前面pre节点.
					//		这时, 下一个节点可能为 null, 需要做循环判断.
					secondNode = null;
				} else {
					secondNode = firstNode.next;
				}
			}
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
