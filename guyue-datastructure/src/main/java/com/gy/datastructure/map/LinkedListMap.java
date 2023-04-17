package com.gy.datastructure.map;

import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

/**
 * @ClassName LinkedListMap
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 18:13
 */
public class LinkedListMap<K, V> implements Map<K, V> {

	private Node dummyHead;
	private int size;

	private class Node {

		private K key;
		private V value;
		private Node next;

		Node() {
			this.key = null;
			this.value = null;
		}

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		Node(K key, V value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

	}

	public LinkedListMap() {
		this.dummyHead = new Node();
		this.size = 0;
	}

	@Override
	public void add(K key, V value) {
		Node node = getNode(key);
		if (null == node) {
			// 向链表头增加一个元素
			// 新节点.next = dummyHead.next
			// dummyHead.next = 新节点
			// size++ 一定不要忘记了.
			dummyHead.next = new Node(key, value, dummyHead.next);
			size++;
		} else {
			node.value = value;
		}
	}

	@Override
	public V remove(K key) {
		// 1. 先看 key 是否存在
		// 2. 存在, 删除, 修改 next 值
		// 3. 不存在, 返回 null
		Node prev = dummyHead;
		while (prev.next != null) {
			if (prev.next.key.equals(key)) {
				break;
			}
			prev = prev.next;
		}
		if (prev != null) {
			Node removeNode = prev.next;
			prev.next = removeNode.next;
			removeNode.next = null;
			size--;
			return removeNode.value;
		}

		return null;
	}

	@Override
	public boolean contains(K key) {
		Node node = getNode(key);
		return null != node ? true : false;
	}

	@Override
	public V get(K key) {
		Node node = getNode(key);
		return null != node ? node.value : null;
	}

	@Override
	public void set(K key, V value) {
		Node node = getNode(key);
		if (null != node) {
			node.value = value;
		} else {
			throw new IllegalArgumentException(key + ", doesn't existing. ");
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	// 2019-12-21
	// 私有函数, 辅助链表实现各种功能.
	// 遍历链表,
	private Node getNode(K key) {
		Node cur = dummyHead.next;
		while (cur != null) {
			if (cur.key.equals(key)) {
				return cur;
			}
			cur = cur.next;
		}

		return null;
	}

	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			LinkedListMap<String, Integer> map = new LinkedListMap<>();
			for (String word : words) {
				if (map.contains(word)) {
					map.set(word, map.get(word) + 1);
				} else {
					map.add(word, 1);
				}
			}

			System.out.println(" total differents words = " + map.size);
			System.out.println(" frequency of pride = " + map.get("pride"));
		}


	}


}
