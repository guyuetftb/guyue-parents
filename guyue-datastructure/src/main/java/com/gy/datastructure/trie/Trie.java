package com.gy.datastructure.trie;

import com.gy.datastructure.set.BinarySearchTreeSet;
import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @ClassName Trie
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-02 22:31
 */
public class Trie {

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

	private Node root;
	private int size;

	public Trie() {
		this.root = new Node();
		this.size = 0;
	}

	public void add(String word) {
		Node cur = root;
		/**
		 * word: panda
		 * 	root
		 * 	 |- p -> tree
		 * 	    |- a -> tree
		 * 	       |- n -> tree
		 * 	          |- d -> tree
		 *
		 */
		for (int index = 0; index < word.length(); index++) {
			char c = word.charAt(index);
			// 例子: root.next.get(p) == null
			if (cur.next.get(c) == null) {
				// 例子: root.next.put(p, Node)
				cur.next.put(c, new Node());
			}

			// 例子: root.next.get(p) = P 的 Tree, 继续迭代
			cur = cur.next.get(c);
		}

		// 如果Trie之前没有这个单词, 就把 cur 的 isWord 属性标为 true, 单词量加1
		if (!cur.isWord) {
			cur.isWord = true;
			size++;
		}
	}

	public boolean contains(String word) {
		Node cur = root;
		for (int index = 0; index < word.length(); index++) {
			char c = word.charAt(index);
			// 示例: root节点的子集中 是否包含 c 这个节点
			if (cur.next.get(c) == null) {
				return false;
			}

			// 示例: 包含.
			// 继续递归, 指针指向 c 这个元素, 所代表的节点
			// 供下次迭代使用
			cur = cur.next.get(c);
		}

		// 这里有一个坑.
		// 比较 panda 这个单词在 Trie中, 而 pan 不在 Tire 中.
		// 那查询 Trie 时即使存在 pan 的组合, 也不能说明 pan 在 Trie中。
		// 只能查看 节点的 iwWord 值是否为 true.
		return cur.isWord;
	}

	public boolean isPrefix(String prefix) {
		Node cur = root;
		for (int index = 0; index < prefix.length(); index++) {
			char c = prefix.charAt(index);
			if (cur.next.get(c) == null) {
				return false;
			}
			cur = cur.next.get(c);
		}
		return true;
	}

	public int getSize() {
		return size;
	}

	public static void main(String[] args) {
		System.out.println("Pride and Prejudice");

		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {

			long startTime = System.nanoTime();

			BinarySearchTreeSet<String> set = new BinarySearchTreeSet<>();
			for (String word : words) {
				set.add(word);
			}

			for (String word : words) {
				set.contains(word);
			}

			long endTime = System.nanoTime();

			double time = (endTime - startTime) / 1000000000.0;

			System.out.println("Total different words: " + set.size());
			System.out.println("BSTSet: " + time + " s");

			// ---

			startTime = System.nanoTime();

			Trie trie = new Trie();
			for (String word : words) {
				trie.add(word);
			}

			for (String word : words) {
				trie.contains(word);
			}

			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;

			System.out.println("Total different words: " + trie.getSize());
			System.out.println("Trie: " + time + " s");
		}
	}
}
