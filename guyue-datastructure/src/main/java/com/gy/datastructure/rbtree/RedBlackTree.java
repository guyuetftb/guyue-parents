package com.gy.datastructure.rbtree;

import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

public class RedBlackTree<K extends Comparable<K>, V> {

	private final static boolean RED = true;
	private final static boolean BLACK = false;

	private class Node {

		public K key;
		public V value;
		public Node left, right;
		public boolean color;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
			this.color = RED;
		}
	}

	private Node root;
	private int size;

	public RedBlackTree() {
		root = null;
		size = 0;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	//	左旋转
	//
	//		node                       x
	//		/	\					  / \
	//	  T1	 x	   ------>      node T3
	//		    /  \                /   \
	//		  T2	T3             T1   T2
	//
	private Node leftRotate(Node node) {
		Node x = node.right;

		node.right = x.left;
		x.left = node;

		x.color = node.color;
		node.color = RED;

		return x;
	}

	// 右旋转
	//    node				x
	//    /	 \			   / \
	//   x	 T2   --->	  y   node
	//  / \					  /	 \
	// y   T1				 T1	 T2
	//
	private Node rightRotate(Node node) {
		Node x = node.left;

		node.left = x.right;
		x.right = node;

		x.color = node.color;
		node.color = RED;

		return x;
	}

	private boolean isRed(Node node) {
		if (node == null) {
			return BLACK;
		}

		if (node.color == RED) {
			return true;
		}
		return false;
	}

	// 向红黑树中添加新的元素(key, value)
	public void add(K key, V value) {
		root = add(root, key, value);
		root.color = BLACK;
	}

	// 向以node为根的红黑树中插入元素(key, value)，递归算法
	// 返回插入新节点后红黑树的根
	private Node add(Node node, K key, V value) {

		if (node == null) {
			size++;
			return new Node(key, value);
		}

		if (key.compareTo(node.key) < 0) {
			node.left = add(node.left, key, value);
		} else if (key.compareTo(node.key) > 0) {
			node.right = add(node.right, key, value);
		} else // key.compareTo(node.key) == 0
		{
			node.value = value;
		}

		// 下面这个过程就是维护红黑树性质的过程了.
		// 下面3个过程不是互斥的, 而是并列过程
		// step1. 是否需要左旋转: node.right = red, node.left != red
		// step2. 是否需要右旋转: node.left = red, node.left.left = red
		// step3. 是否需要颜色翻转: node.left = red, node.right = red

		// step1
		if (isRed(node.right) && !isRed(node.left)) {
			node = leftRotate(node);
		}

		// step2
		if (isRed(node.left) && isRed(node.left.left)) {
			node = rightRotate(node);
		}

		// step3
		if (isRed(node.left) && isRed(node.right)) {
			flipColor(node);
		}

		return node;
	}

	// 返回以node为根节点的红黑树中，key所在的节点
	private Node getNode(Node node, K key) {

		if (node == null) {
			return null;
		}

		if (key.equals(node.key)) {
			return node;
		} else if (key.compareTo(node.key) < 0) {
			return getNode(node.left, key);
		} else // if(key.compareTo(node.key) > 0)
		{
			return getNode(node.right, key);
		}
	}

	public boolean contains(K key) {
		return getNode(root, key) != null;
	}

	public V get(K key) {

		Node node = getNode(root, key);
		return node == null ? null : node.value;
	}

	public void set(K key, V newValue) {
		Node node = getNode(root, key);
		if (node == null) {
			throw new IllegalArgumentException(key + " doesn't exist!");
		}

		node.value = newValue;
	}

	// 返回以node为根的红黑树的最小值所在的节点
	private Node minimum(Node node) {
		if (node.left == null) {
			return node;
		}
		return minimum(node.left);
	}

	// 颜色翻转
	private void flipColor(Node node) {
		// node
		//
		// 传入的 node 要满足视频中 子树的形状
		// 	 node
		//	/	 \
		// l1	 l2
		//
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;

	}

	// 删除掉以node为根的红黑树中的最小节点
	// 返回删除节点后新的红黑树的根
	private Node removeMin(Node node) {

		if (node.left == null) {
			Node rightNode = node.right;
			node.right = null;
			size--;
			return rightNode;
		}

		node.left = removeMin(node.left);
		return node;
	}

	// 从红黑树中删除键为key的节点
	public V remove(K key) {

		Node node = getNode(root, key);
		if (node != null) {
			root = remove(root, key);
			return node.value;
		}
		return null;
	}

	private Node remove(Node node, K key) {

		if (node == null) {
			return null;
		}

		if (key.compareTo(node.key) < 0) {
			node.left = remove(node.left, key);
			return node;
		} else if (key.compareTo(node.key) > 0) {
			node.right = remove(node.right, key);
			return node;
		} else {   // key.compareTo(node.key) == 0

			// 待删除节点左子树为空的情况
			if (node.left == null) {
				Node rightNode = node.right;
				node.right = null;
				size--;
				return rightNode;
			}

			// 待删除节点右子树为空的情况
			if (node.right == null) {
				Node leftNode = node.left;
				node.left = null;
				size--;
				return leftNode;
			}

			// 待删除节点左右子树均不为空的情况

			// 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
			// 用这个节点顶替待删除节点的位置
			Node successor = minimum(node.right);
			successor.right = removeMin(node.right);
			successor.left = node.left;

			node.left = node.right = null;

			return successor;
		}
	}

	public static void main(String[] args) {

		System.out.println("Pride and Prejudice");

		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			RedBlackTree<String, Integer> map = new RedBlackTree<>();
			for (String word : words) {
				if (map.contains(word)) {
					map.set(word, map.get(word) + 1);
				} else {
					map.add(word, 1);
				}
			}

			System.out.println("Total different words: " + map.getSize());
			System.out.println("Frequency of PRIDE: " + map.get("pride"));
			System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
		}

		System.out.println();
	}
}

