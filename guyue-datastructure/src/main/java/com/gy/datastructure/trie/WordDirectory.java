package com.gy.datastructure.trie;

import java.util.TreeMap;

/**
 * @ClassName WordDirectory
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-03 11:27
 */
public class WordDirectory {

	private Node root;

	public WordDirectory() {
		this.root = new Node();
	}

	private static class Node {

		private boolean isWord;
		private TreeMap<Character, Node> next;

		public Node(boolean isWord) {
			this.isWord = isWord;
			this.next = new TreeMap<>();
		}

		public Node() {
			this(false);
		}
	}

	public boolean search(String word) {
		return this.match(root, word, 0);
	}


	/**
	 * @param node 以 Node 这个节点为根, 对 Node 这个 Trie 进行搜索. Node 可以是Trie 中任意节点.
	 * @param word 要搜索的具体的单词, 单词中可以包含 '.', 代表任意一个字符
	 * @param index index 是说从word的第几个字符开始搜索, 因为 需要从当前 Node 搜索的内容已经不是一个完整的单词.
	 */
	public boolean match(Node node, String word, int index) {
		// 首先判断一下, 递归到底的情况
		// 如果搜索的 Index 与 word 的长度相同,
		// 这时候查看 当前 Node 是不是一个有效单词, 并把结果返回.
		if (index == word.length()) {
			return node.isWord;
		}

		char c = word.charAt(index);
		if (c != '.') {
			// word 索引的 index 不为 '.'
			if (node.next.get(c) != null) {
				// 则判断, Node 的 next 中是否包含 c
				// 不包含, 退出 匹配失败.
				return false;
			} else {
				// 包含, 进行一下次迭代
				// 从包含 c 字符的,  Node的子节点中继续下一次迭代
				match(node.next.get(c), word, index + 1);
			}
		} else {
			// word 索引的 index 为 '.'
			// 要迭代遍历 Node 的所有子节点
			for (Character ct : node.next.keySet()) {
				// 如果迭代的节点中有一个匹配成功了, 那我们当前的匹配就算是成功了
				// 就返回 true
				if (match(node.next.get(ct), word, index + 1)) {
					return true;
				}
			}
			// 如果对当前 Node 的所有分支都没有 相应的字符 匹配成功，就说明这次匹配失败
			// 返回 false 就可以了。
			return false;
		}
		return false;
	}
}
