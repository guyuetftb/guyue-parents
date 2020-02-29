package com.gy.datastructure.avltree;

import com.gy.datastructure.map.BinarySearchTreeMap;
import com.gy.datastructure.rbtree.RedBlackTree;
import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;
import java.util.Collections;

public class AVLTree<K extends Comparable<K>, V> {

	private class Node {

		private K key;
		private V value;

		private Node left;
		private Node right;
		private int height;

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
			this.height = 1;
		}


	}

	private Node root;
	private int size;

	AVLTree() {
		this.root = null;
		this.size = 0;
	}

	public void add(K key, V value) {
		root = add(root, key, value);
	}

	// 以node为根的二分搜索树中插入元素(key,value), 递归算法
	// 返回插入新节点后的二分搜索树的根
	private Node add(Node node, K key, V value) {

		/**
		 * 处理递归到底的情况.
		 */
		if (null == node) {
			// 如果创建节点的子树为空,在新创建节点的时候,高度自然是 1.
			// 这里返回一个新创建的节点, 并返回新创建节点的索引.
			// 在递归调用 返回处, 上一层节点相应的 left, right 会自动填充上内容.
			// 同理, 如果递归了很多层, 返回逻辑不变.
			size++;
			return new Node(key, value);
		}

		/**
		 * 向树中添加新的节点.
		 */
		if (node.key.compareTo(key) > 0) {

			// 向左子树中添加一个节点.
			node.left = add(node.left, key, value);
		} else if (node.key.compareTo(key) < 0) {

			// 向右子树中添加一个节点
			node.right = add(node.right, key, value);
		} else {
			// 树中已经存在该 key, 把新 node.value = value.
			node.value = value;
		}

		/**
		 * 上图中由于向左 或者 右子树中添加了一个节点
		 * 所以这里, 更新一下当前 node 所对应的 height 值.
		 */
		node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

		// 更新一下平衡因子
		// 如果平衡因子大于1, 并且 当前节点的左节点的平衡因子
		int balanceFactor = getBalanceFactor(node);
//		if (Math.abs(balanceFactor) > 1) {
//			System.out.println(" unbalanced: " + balanceFactor);
//		}

		// 当前节点的平衡因子大于1, 但是 当前节点的左子树的平衡因子正常
		// 那么，对当前节点进行右旋转
		// 并返回新的根节点, 也就是返回到递归的上一层.
		// balanceFactor > 1 : 说明左子树 >= 右子树
		// TODO 将节点的平衡性更新完后, 才返回 node.
		// getBalanceFactor(node.left) 说明, 左子树的平衡因子 大于等于0.
		// LL
		if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
			return rightRotate(node);
		}

		// TODO 将节点更新的平衡性更新完后, 才返回 node.
		// 当前节点的平衡因子小于-1, 但是 当前节点的右子树的平衡因子正常
		// 那么, 对当前节点进行左旋转
		// 并返回更新后的根节点, 也就是说返回上一层递归调用
		// balanceFactory < -1 说明, 右子树 <= 左子树
		// getBalanceFactor(node.right) 说明, 右子树的平衡因子 小于等于0.
		// RR
		if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
			return leftRotate(node);
		}

		// LR:
		// 说明节点的不平衡性被打破, 只是 左子树 < 右子树
		if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {

			// 把 LR 情况转化为 LL 情况
			// 也就是对当前节点的left, 进行一次左旋转.
			node.left = leftRotate(node.left);

			//  再对 LL 节点进行右旋转
			return rightRotate(node);
		}

		// RL:
		// 说明节点的不平衡性被打破, 只是 左子树 > 右子树
		if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {

			// 把 RL 情况转化为 RR 的情况.
			// 也就是对当前节点的 right, 进行一次右旋转
			node.right = rightRotate(node.right);

			// 再对 RR 节点进行左旋转.
			return leftRotate(node);
		}
		return node;
	}

	// auxiliary function
	private int getHeight(Node node) {
		if (null == node) {
			return 0;
		}
		return node.height;
	}

	// auxiliary function
	private int getBalanceFactor(Node node) {
		if (null == node) {
			return 0;
		}
		return getHeight(node.left) - getHeight(node.right);
	}

	public boolean isBalanced() {
		return isBalanced(root);
	}

	private boolean isBalanced(Node node) {
		if (null == node) {
			return true;
		}

		int balancedFactor = getBalanceFactor(node);
		if (Math.abs(balancedFactor) > 1) {
			return false;
		}

		// 递归查看左,右子树的平衡因子是否满足 平衡二叉树的情况.
		return isBalanced(node.left) && isBalanced(node.right);
	}

	// 判断当前树是否是一棵二分搜索树.
	// 二分搜索树的左子树的节点, 都小于右子树的节点.
	// 所以只要我们中序遍历这棵树, 得到的数据是从小到大有序的,就说明是一棵有效的二分搜索树
	private boolean isBST() {
		ArrayList<K> arrayList = new ArrayList<>();
		inOrder(root, arrayList);

		for (int index = 1; index < arrayList.size(); index++) {
			if (arrayList.get(index - 1).compareTo(arrayList.get(index)) > 0) {
				return false;
			}
		}

		return true;
	}

	private void inOrder(Node node, ArrayList<K> list) {
		if (null == node) {
			return;
		}

		// 遍历左子树
		inOrder(node.left, list);

		// 记录当前节点的值
		list.add(node.key);

		// 遍历右子树
		inOrder(node.right, list);
	}

	//        对节点y进行右旋转,返回旋转后的新根节点 x
	//
	//            y                       x
	//           / \                    /   \
	//          x  T4                  z     y
	//         / \      ---------->   / \   / \
	//        z   T3                T1  T2 T3  T4
	//       / \
	//      T1  T2
	// 1. 暂存 x 的左节点 T3
	// 2. 提出 y 的左节点 X
	// 3. 将 y 赋值给 x 的右节点
	// 4. 将 T3 赋值给 y 左节占
	// 6. 根据y 的新子节点，重新计算 y 节点的高度值, 并加1
	// 7. 根据x 的新子节点，重新计算 x 节点的高度值, 并加1
	private Node rightRotate(Node y) {

		Node x = y.left;
		Node t3 = x.right;

		x.right = y;
		y.left = t3;

		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

		return x;
	}

	//        对节点y进行左旋转,返回旋转后的新根节点 x
	//
	//            y                            x
	//           / \                         /   \
	//          T1  x        向左旋转(y)     y      z
	//             / \      ---------->   /  \    / \
	//            T2  z                  T1  T2  T3  T4
	//               / \
	//              T3  T4
	//
	// 1. 暂存 x 的左节点 T2
	// 2. 提出 y 的右节点 X
	// 3. 将 y 赋值给 x 的左节点
	// 4. 将 T2 赋值给 y 右节占
	// 6. 根据y 的新子节点，重新计算 y 节点的高度值, 并加1
	// 7. 根据x 的新子节点，重新计算 x 节点的高度值, 并加1
	private Node leftRotate(Node y) {

		Node x = y.right;
		Node t2 = x.left;

		x.left = y;
		y.right = t2;

		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

		return x;
	}


	public V  remove(K key) {
		Node node = getNode(root, key);
		if (null != node) {
			root = remove(root, key);
			return node.value;
		}
		return null;
	}

	// 删除二分搜索树中键为Key的节点
	private Node remove(Node node, K key) {

		// null 为空, 说明树中不存在我们要找的元素.
		// 我们直接返回 null 就好了.
		if (node == null) {
			return null;
		}


		if (key.compareTo(node.key) > 0) {

			// 当前 key > node.key
			// 就递归去当前节点的右子树中删除 key
			node.right = remove(node.right, key);

			// 在删除了树中的节点之后，新的根节点就被返回了.
			// 那么就很有可能打破这棵树的平衡性.
			// 所以，我们不能直接返回 node 节点.

			return node;
		} else if (key.compareTo(node.key) < 0) {

			// 当前 key 小于 node.key
			// 在右子树中的元素对比
			// 就递归去当前节点的左子树中删除 key.
			node.left = remove(node.left, key);
			// 在删除了树中的节点之后，新的根节点就被返回了.
			// 那么就很有可能打破这棵树的平衡性.
			// 所以，我们不能直接返回 node 节点.

			return node;
		} else {
			// key.compareTo(node.key) == 0

			// 待删除的节点左子树为空, 右子对不为空
			if (node.left == null) {

				Node rightNode = node.right;
				node.right = null;
				size--;
				return rightNode;
			} else

			// 待删除的节点右子树为空, 左子村不为空
			if (node.right == null) {

				Node leftNode = node.left;
				node.left = null;
				size--;
				return leftNode;
			} else {

				// 待删节点左, 右子树都不为空的情况
				// 找到比待删除节点大的最小的节点, 即待删除节点右子树最小节点，或者，先走删除节点，左子树最大节点
				// 用这个节点顶替待删除节点的位置
				Node successor = munimum(node.right);

				// TODO 这里有一个小Bug, 删除最小元素有可能会打破这棵树的平衡
				// 这里可以有两种处理方式
				// 1. 在 removeMin 中也添加上维护自平衡的代码
				// 2. 调用 remove 方法, 因为 我们已经知 道了要删除最小值元素的节点
				// 		所以我们直接根据节点值, 删除元素就可以了.
				successor.right = remove(node, successor.key);
				successor.left = node.left;

				node.left = node.right = null;
				return successor;
			}
		}
	}

	public boolean contains(K key) {
		Node node = getNode(root, key);
		return null == node ? false : true;
	}

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

	public void set(K key, V value) {
		Node node = getNode(root, key);
		if (null == node) {
			throw new IllegalArgumentException(key + ", doesn't exist!");
		}
		node.value = value;
	}

	public int getSize() {
		return size;
	}

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
	// 在 AVL 树中, 此方法可以不需要了, 直接删除就好了.
	@Deprecated
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
		testAVLTree();
		System.out.println("--------------------------------------");
		testSpeed();
	}

	public static void testAVLTree(){
		ArrayList<String> words = new ArrayList<String>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			AVLTree<String, Integer> map = new AVLTree<>();
			for (String word : words) {
				if (map.contains(word)) {
					map.set(word, map.get(word) + 1);
				} else {
					map.add(word, 1);
				}
			}

			System.out.println(" total differents words = " + map.size);
			System.out.println(" frequency of pride = " + map.get("pride"));
			System.out.println(" is BST tree " + map.isBST());
			System.out.println(" is Balanced tree " + map.isBalanced());
		}
	}

	public static void testSpeed() {

		System.out.println("Pride and Prejudice");

		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			Collections.sort(words);

			// Test BST
			long startTime = System.nanoTime();

			BinarySearchTreeMap<String, Integer> bst = new BinarySearchTreeMap<>();
			for (String word : words) {
				if (bst.contains(word)) {
					bst.set(word, bst.get(word) + 1);
				} else {
					bst.add(word, 1);
				}
			}

			for (String word : words) {
				bst.contains(word);
			}

			long endTime = System.nanoTime();

			double time = (endTime - startTime) / 1000000000.0;
			System.out.println("BST: " + time + " s");

			// Test AVL Tree
			startTime = System.nanoTime();

			AVLTree<String, Integer> avl = new AVLTree<>();
			for (String word : words) {
				if (avl.contains(word)) {
					avl.set(word, avl.get(word) + 1);
				} else {
					avl.add(word, 1);
				}
			}

			for (String word : words) {
				avl.contains(word);
			}

			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;
			System.out.println("AVL: " + time + " s");


			// Test RedBlack Tree
			startTime = System.nanoTime();

			RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<>();
			for (String word : words) {
				if (redBlackTree.contains(word)) {
					redBlackTree.set(word, redBlackTree.get(word) + 1);
				} else {
					redBlackTree.add(word, 1);
				}
			}

			for (String word : words) {
				redBlackTree.contains(word);
			}

			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;
			System.out.println("RedBlackTree: " + time + " s");



		}



		System.out.println();
	}
}
