package com.gy.datastructure.map;

import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

/**
 * @ClassName BinarySearchTreeMap
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 22:51
 */
public class BinarySearchTreeMap<K extends Comparable, V> implements Map<K, V> {

	private class Node {

		private K key;
		private V value;

		private Node left;
		private Node right;

		Node() {
			this.key = null;
			this.value = null;
			this.left = null;
			this.right = null;

		}

		Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	private Node root;
	private int size;

	public BinarySearchTreeMap() {
		this.root = null;
		this.size = 0;
	}

	@Override
	public void add(K key, V value) {
		root = add(root, key, value);
	}

	// 以node为根的二分搜索树中插入元素(key,value), 递归算法
	// 返回插入新节点后的二分搜索树的根
	private Node add(Node node, K key, V value) {
		if (null == node) {
			size++;
			return new Node(key, value);
		}

		if (node.key.compareTo(key) > 0) {
			node.left = add(node.left, key, value);
		} else if (node.key.compareTo(key) < 0) {
			node.right = add(node.right, key, value);
		} else {
			// 树中已经存在该 key, 把新 node.value = value.
			node.value = value;
		}
		return node;
	}


	@Override
	public V remove(K key) {
		Node node = getNode(root, key);
		if (null != node) {
			root = remove(root, key);
			return node.value;
		}
		return null;
	}

	// 删除二分搜索树中键为Key的节点
	private Node remove(Node node, K key) {
		if (node == null) {
			return null;
		}

		if (key.compareTo(node.key) > 0) {

			// 当前 key > node.key
			node.right = remove(node.right, key);
			return node;
		} else if (key.compareTo(node.key) < 0) {

			// 当前 key 小于 node.key
			// 在右子树中的元素对比
			node.left = remove(node.left, key);
			return node;
		} else {
			// key.compareTo(node.key) == 0

			// 待删除的节点左子树为空
			if (node.left == null) {
				Node rightNode = node.right;
				node.right = null;
				size--;
				return rightNode;
			}

			// 待删除的节点右子树为空
			if (node.right == null) {
				Node leftNode = node.left;
				node.left = null;
				size--;
				return leftNode;
			}

			// 待删节点左, 右子树都不为空的情况
			// 找到比待删除节点大的最小的节点, 即待删除节点右子树最小节点，或者，先走删除节点，左子树最大节点
			// 用这个节点顶替待删除节点的位置
			Node successor = munimum(node.right);
			successor.right = removeMin(node.right);
			successor.left = node.left;

			node.left = node.right = null;
			return successor;
		}
	}

	@Override
	public boolean contains(K key) {
		Node node = getNode(root, key);
		return null == node ? false : true;
	}

	@Override
	public V get(K key) {
		Node node = getNode(root, key);
		return null == node ? null : node.value;
	}

	private Node getNode(Node node, K key) {
		if (null == node) {
			return null;
		}

		if (node.key.compareTo(key) == 0) {
			return node;
		} else if (node.key.compareTo(key) < 0) {
			// 传入的key, 比我小, 向左
			return getNode(node.left, key);
		} else {
			// 传入的key, 比我大, 向右
			return getNode(node.right, key);
		}
	}

	@Override
	public void set(K key, V value) {
		Node node = getNode(root, key);
		if (null == node) {
			throw new IllegalArgumentException(key + ", doesn't exist!");
		}
		node.value = value;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private Node munimum(Node node) {
		// 如果左子树为空, 则直接返回当前节点
		if (node.left == null) {
			return node;
		}
		// 否则, 迭代当前节点的左子树
		return munimum(node.left);
	}

	// 删除掉以 node 为根的二分搜索树的最小节点
	// 返回删除节点后新的二分搜索树的根
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

	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			BinarySearchTreeMap<String, Integer> map = new BinarySearchTreeMap<>();
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
