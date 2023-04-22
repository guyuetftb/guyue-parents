package com.gy.algorithm.basic.tree;

public class TreeBinaryMaxDepth {
//
//	public static void main(String[] args) {
//
//		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//		int[] nums = {5, 3, 6, 8, 4, 2};
//		for (int index = 0; index < nums.length; index++) {
//			tree.add(nums[index]);
//		}
//
//		System.out.println("-- Max depth Recursion----------------------------");
//		System.out.println(maxDepthRecursion(tree.getRoot()));
//
//		System.out.println("-- Max depth Non-Recursion----------------------------");
//		System.out.println(maxDepthNonRecursion(tree.getRoot()));
//
//		System.out.println("-- Pre Order ----------------------------");
//		preOrderRecursion(tree.getRoot());
//
//		System.out.println("-- Max depth Non-Recursion----------------------------");
//		preOrderNonRecursion(tree.getRoot());
//
//		System.out.println("-- Level Order ----------------------------");
//		levelOrder(tree.getRoot());
//	}
//
//	public static void overview() {
//		/**
//		 * 计算最大深度, 前序, 后序, 中序遍历, 我们都是通过递归方式实现的。
//		 * 递归方式的基本思想是: 深度优先(Depth First Search)，就是不断的深入每个分支的子分支, 直到子分支为 null, 才返回。
//		 *
//		 * 问题:
//		 * 这种算法的一个缺点是: 如果树的深度过深, 不断的递归会触发大量的弹栈, 压栈操作, 超过栈最大深度会引发: 栈溢出。
//		 * 解决:
//		 * 解决问题的方案是, 把递归转化为非递归。引用网友的说法: 99%的 递归 转 非递归 都可以借助 Stack 来实现.
//		 */
//	}
//
//	/**
//	 * 递归-最大深度
//	 */
//	public static int maxDepthRecursion(TreeNode root) {
//
//		/**
//		 * 2020-04-07 当计算一棵树的深度时, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 		a
//		 * 	 -------
//		 * 	b		c
//		 *
//		 * a是 root 节点
//		 * 1. 先计算left 节点深度, 再计算 right 节点深度
//		 * 2. 在 left 节点, right 节点中选择最大值, 返回给 父节点
//		 * 3. 步骤 1, 步骤2, 直tree 被遍历完.
//		 *
//		 */
//
//		if (null == root) {
//			return 0;
//		}
//
//		return Math.max(maxDepthRecursion(root.getLeft()) + 1, maxDepthRecursion(root.getRight()) + 1);
//	}
//
//	/**
//	 * 非递归-最大深度
//	 */
//	public static int maxDepthNonRecursion(TreeNode root) {
//		/**
//		 * 2020-04-07
//		 * 暂不实现.
//		 */
//		return 0;
//	}
//
//	/**
//	 * 非递归-层序遍历
//	 */
//	public static void levelOrder(TreeNode root) {
//		/**
//		 *
//		 * 2020-04-07
//		 * 层序遍历, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 			a
//		 * 	 	-------
//		 * 		b		c
//		 * 	 ---------------
//		 *	d	e		f	g
//		 *
//		 * 我们需要一层 一层的遍历节点,
//		 * 1. 先遍历 a
//		 * 2. 再遍历 b, c
//		 * 3. 再遍历 d,e,f,g
//		 * 从上到下，从左到右，这符合先进, 先出的特点, 所以我们可以借用 Queue.
//		 *
//		 * a是 root 节点
//		 * 1. 先把 a 入队列.
//		 * 2. 取出队列顶元素, 输出
//		 * 3. 把 a 的left, right 入队
//		 * 4. 删除 a.
//		 * 5. 取出 b 元素, 输出
//		 * 6. 把 b 的 left, right 入除.
//		 * 7. 删除 b.
//		 * 8. ...依次类推.
//		 *
//		 */
//
//		Queue<TreeNode> queue = new LinkedList<>();
//		queue.add(root);
//
//		while (!queue.isEmpty()) {
//
//			// 窥探, 但不删除
//			TreeNode treeNode = queue.peek();
//			System.out.println(treeNode.getValue());
//
//			/** Queue 先进先出 先遍历left, left 就在 right 前进入Queue */
//			if (null != treeNode.getLeft()) {
//				queue.add(treeNode.getLeft());
//			}
//
//			/** Queue 先进先出 后遍历right, right 就在 left 后进入Queue */
//
//			if (null != treeNode.getRight()) {
//				queue.add(treeNode.getRight());
//			}
//
//			// 元素用完了, 删除.
//			queue.remove();
//		}
//	}
//
//	/**
//	 * 非递归-前序遍历
//	 */
//	public static void preOrderNonRecursion(TreeNode root) {
//		/**
//		 *
//		 * 2020-04-07
//		 * 递归问题:
//		 * 	这种算法的一个缺点是: 如果树的深度过深, 不断的递归会触发大量的弹栈, 压栈操作, 超过栈最大深度会引发: 栈溢出。
//		 *
//		 * 递归解决:
//		 * 	解决问题的方案是, 把递归转化为非递归。引用网友的说法: 99%的 递归 转 非递归 都可以借助 Stack 来实现.
//		 *
//		 * 当计算一棵树的深度时, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 		a
//		 * 	 -------
//		 * 	b		c
//		 *
//		 * a是 root 节点
//		 * 1. 先计算left 节点深度, 再计算 right 节点深度
//		 * 2. 在 left 节点, right 节点中选择最大值, 返回给 父节点
//		 * 3. 步骤 1, 步骤2, 直tree 被遍历完.
//		 *
//		 */
//
//		Stack<TreeNode> stack = new Stack<>();
//		stack.add(root);
//
//		while (!stack.isEmpty()) {
//
//			TreeNode treeNode = stack.pop();
//			/** 第1步 遍历节点自身 */
//			System.out.println(treeNode.getValue());
//
//			/** Stack 先进后出的, 先序遍历, 第2步遍历left, right 就在 left 前入Stack */
//			if (null != treeNode.getRight()) {
//				stack.push(treeNode.getRight());
//			}
//
//			/** Stack 后进先出的, 先序遍历, 第3步先遍历left, left 就在 right 后入Stack */
//			if (null != treeNode.getLeft()) {
//				stack.push(treeNode.getLeft());
//			}
//		}
//	}
//
//	/**
//	 * 递归-前序遍历
//	 */
//	public static void preOrderRecursion(TreeNode root) {
//		if (root == null) {
//			return;
//		}
//
//		/**
//		 * 2020-04-07
//		 * 遍历树的节点时, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 		a
//		 * 	 -------
//		 * 	b		c
//		 *
//		 * 先序遍历
//		 * a是 root 节点
//		 * 1. 遍历 root 自身节点
//		 * 2. 遍历 root 树的 left 节点: b.
//		 * 3. 遍历 root 树的 right 节点: c.
//		 *
//		 */
//		System.out.println(root.getValue());
//		preOrderRecursion(root.getLeft());
//		preOrderRecursion(root.getRight());
//	}
//
//	public static void midOrderRecursion(TreeNode root) {
//		if (root == null) {
//			return;
//		}
//
//		/**
//		 * 2020-04-07
//		 * 遍历树的节点时, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 		a
//		 * 	 -------
//		 * 	b		c
//		 *
//		 * 中序遍历
//		 * a是 root 节点
//		 * 1. 遍历 root 树的 left 节点: b.
//		 * 2. 遍历 root 自身节点.
//		 * 3. 遍历 root 树的 right 节点: c.
//		 *
//		 */
//		midOrderRecursion(root.getLeft());
//		System.out.println(root.getValue());
//		midOrderRecursion(root.getRight());
//	}
//
//	public static void postOrderRecursion(TreeNode root) {
//		if (root == null) {
//			return;
//		}
//
//		/**
//		 * 2020-04-07
//		 * 遍历树的节点时, 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树
//		 * 		a
//		 * 	 -------
//		 * 	b		c
//		 *
//		 * 后序遍历
//		 * a是 root 节点
//		 * 1. 遍历 root 树的 left 节点: b.
//		 * 2. 遍历 root 树的 right 节点: c.
//		 * 3. 遍历 root 自身节点.
//		 *
//		 */
//		postOrderRecursion(root.getLeft());
//		postOrderRecursion(root.getRight());
//		System.out.println(root.getValue());
//	}
}
