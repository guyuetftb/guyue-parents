package com.gy.algorithm.basic.linkedlist;

/**
 * @ClassName ReverseLinkedListTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 20:13
 */
public class ReverseKLinkedListTest {

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

		System.out.println("Swap K Node ----------------------");
		list1.reverseK(4);
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

		public void reverseK(int k) {
			reverseK(preHead, k);
		}

		public void reverseK(LinkedNode preHead, int k) {

			LinkedNode pre = preHead;

			// while循环正常执行, 说明 pre 还有下一个节点.
			while (pre.next != null) {

				// 循环列表, 组装子列表, subLinkedHead 是子链表的第1个节点.
				int n = 0;
				LinkedNode tmpPre = pre;
				while (tmpPre != null && tmpPre.next != null && n < k) {
					tmpPre = tmpPre.next;
					n++;
				}

				// 如果 n == k, 说明 子队列 满足 k 的长度, 反转子队列.
				// 1. pre.next 指向的是第1个节点, 反转前的 head, 反转后是 tail.
				// 2. 保存节点为 n 的 next 值, 后续做 连接用.
				// 3. 将节点 n 的 next 设置为 null, 防止循环处理.
				if (n == k) {

					// 1. pre.next 指向的是第1个节点, 反转前的 head, 反转后是 tail.
					LinkedNode srcHeadReverseTail = pre.next;

					// 2. 保存节点为 n 的 next 值, 后续做 连接用.
					LinkedNode srcNNext = tmpPre.next;

					// 3. 将 节点 next 设置为 null;
					tmpPre.next = null;

					// 4. 翻转n个元素, 并返回头节点.
					LinkedNode reverseNode = reverse(srcHeadReverseTail);

					// 5. pre 指向 反转后的第1个节点.
					//		TODO 第1次反转后, pre 与 preHead 指向同一个元素.
					//			第一次反转后, 设置了 pre.next 相当于设置了 preHead.next,
					//			所以链表不会断.
					pre.next = reverseNode;

					// 6. 反转前的 Head, 反转后的 tail 指向 反转前的 后续节点.
					srcHeadReverseTail.next = srcNNext;

					// 7. pre 指针后移, 接着下一次循环.
					pre = srcHeadReverseTail;
				} else {
					break;
				}
			}
		}


		private LinkedNode reverse(LinkedNode head) {
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
