package com.gy.datastructure.trie;

import java.util.TreeMap;

/**
 * LeetCode 667号问题
 */
public class MapSum {

	private static class Node {

		// 正规单词的value 值是不等于0的.
		// 通过 value 值判断出, 是否是正规单词.
		private int value;
		private TreeMap<Character, Node> next;

		public Node(int value) {
			this.value = value;
			this.next = new TreeMap<>();
		}

		public Node() {
			this(0);
		}
	}

	private Node root;

	public void add(String word, int value) {
		Node cur = root;
		for (int index = 0; index < word.length(); index++) {
			char c = word.charAt(index);
			if (cur.next.get(c) == null) {
				// Tire 树中没有包含这个字符.
				cur.next.put(c, new Node());
			}
			cur = cur.next.get(c);
		}

		cur.value = value;
	}

	public int sum(String prefix) {

		Node cur = root;

		// 假如 Trie 中包含 panda 单词, value 等于5
		// 我们传入 pan, 求 mapSum 值.
		for (int index = 0; index < prefix.length(); index++) {
			char c = prefix.charAt(index);
			if (cur.next.get(c) == null) {
				return 0;
			}
			cur = cur.next.get(c);
		}

		// 迭代到此处, 说明给的前缀是存在的.
		// 这时候, 迭代前缀之的所有的单词值.

		return 0;
	}

	private int sum(Node node) {
		// 如果当前 Node 也是一个合法单词,
		// 累加当前单词的值.
		int res = node.value;
		for (Character c : node.next.keySet()) {
			res += sum(node.next.get(c));
		}
		return res;
	}
}
