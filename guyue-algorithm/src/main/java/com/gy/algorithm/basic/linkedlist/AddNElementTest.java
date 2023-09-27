package com.gy.algorithm.basic.linkedlist;

import com.gy.algorithm.basic.common.LinkedNode;

/**
 * @ClassName RemoveTailNElementTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 14:13
 */
public class AddNElementTest {

	private static class LinkedList<E> {

		private LinkedNode preHead;
		private int size;


		public LinkedList() {
			preHead = new LinkedNode();
			size = 0;
		}

		public void add(int index, E e) {

			if (index < 0 || index > size) {
				throw new IllegalArgumentException(" error index .");
			}

			// TODO 假设 index = 2, e = "hello"
			//		 临时 preHead 节点, 方便迭代
			//		 链表状态:
			//		 		preHead -> 0Node -> 1Node -> 2Node -> 3Node
			//		 初始状态:
			//		 		preHead -> 0Node -> 1Node -> 2Node -> 3Node
			//					^
			//					pre
			//		 i = 0;
			//		 		preHead -> 0Node -> 1Node -> 2Node -> 3Node
			//							^
			//					=>		pre
			//		 i = 1;
			//		 		preHead -> 0Node -> 1Node -> 2Node -> 3Node
			//									^
			//							=>		pre
			LinkedNode pre = preHead;
			for (int i = 0; i < index; i++) {
				pre = pre.next;
			}

			// TODO
			// 		index = 1 退出循环
			//		pre.next 指向的就是 index = 2 的节点.
			LinkedNode oldNode = pre.next;
			LinkedNode newNode = new LinkedNode(e, oldNode);
			pre.next = newNode;

			size ++;
		}
	}
}
