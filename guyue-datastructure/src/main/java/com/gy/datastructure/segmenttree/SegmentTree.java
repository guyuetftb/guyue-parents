package com.gy.datastructure.segmenttree;

/**
 * @ClassName SegmentTree
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-31 08:36
 */
public class SegmentTree<E> {

	private E[] tree;
	private E[] data;
	private Merger<E> merger;

	public SegmentTree(E[] arr, Merger<E> merger) {
		this.merger = merger;

		data = (E[]) new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			data[i] = arr[i];
		}

		// 使用数组来存储线段树的话, 是需要4倍的空间来存储元素的.
		tree = (E[]) new Object[4 * arr.length];

		// 创建根节点, 孩子索引从0, 至data. length -1 线段树
		/**
		 * 			0
		 * 		1		2
		 * 	  3	  4	  5	  6
		 */
		// treeIndex = 0;
		// l = 0;
		// r = 6
		buildSegmentTree(0, 0, data.length - 1);
	}

	/**
	 * @param treeIndex 当前节点 index
	 * @param l 当前节点 左孩子 index
	 * @param r 当前节点	右孩子 index
	 */
	private void buildSegmentTree(int treeIndex, int l, int r) {

		// index 节点上，左孩子Index值等于右孩子的index 值, 说明该 index没有子节点了, 退出.
		// 因为 l == r, 所以 treeIndex 的赋值内容是 l, 或 r 都可以.
		if (l == r) {
			tree[treeIndex] = data[l];
			return;
		}

		// 第一次递归:
		// treeIndex = 0;
		// leftChildIndex = 2 * 0 + 1;
		// rightChildIndex = 2 * 0 + 2;

		// 分别得到左,右子节点的 Index 值.
		int leftTreeIndex = leftChild(treeIndex);
		int rightTreeIndex = rightChild(treeIndex);

		// 第一次递归:
		// mid = 3 = (0 + (6-0) / 2));

		// 为什么要用下面这个公式?
		// 因为 直接用 (l + r) / 2,
		// 当 l, r 都很大时, 相加容易内存溢出.
		int midIndex = l + (r - l) / 2;

		// 第一次递归:
		// 0, 0, 3
		// 1, 4, 6
		// 创建左线段树
		buildSegmentTree(leftTreeIndex, l, midIndex);

		// 创建右线段树
		buildSegmentTree(rightTreeIndex, midIndex + 1, r);

		tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
	}

	// 查询索引在 [queryL, queryR] 之间的线段树的值.
	public E query(int queryL, int queryR) {
		if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length
			|| queryL > queryR) {
			throw new IllegalArgumentException(" Query index is wrong.");
		}
		return query(0, 0, data.length - 1, queryL, queryR);
	}

	private E query(int treeIndex, int l, int r, int queryL, int queryR) {
		if (l == queryL && r == queryR) {
			return tree[treeIndex];
		}

		// treeIndex的节点分为[l...mid]和[mid+1...r]两部分
		int leftTreeIndex = leftChild(treeIndex);
		int rightTreeIndex = leftChild(treeIndex);
		int mid = l + (r - l) / 2;

		if (queryL >= mid + 1) {
			return query(rightTreeIndex, mid + 1, r, queryL, queryR);
		} else if (queryR <= mid) {
			return query(leftTreeIndex, l, mid, queryL, queryR);
		} else {
			E leftChild = query(leftTreeIndex, l, mid, queryL, mid);
			E rightChild = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
			return merger.merge(leftChild, rightChild);
		}
	}

	public void set(int index, E newValue) {

		if (index < 0 || index >= data.length) {
			throw new IllegalArgumentException(" Index is wrong.");
		}

		data[index] = newValue;
		set(0, 0, data.length - 1, index, newValue);
	}

	private void set(int treeIndex, int l, int r, int index, E newValue) {

		// 当节点没有左, 右子树说明到了叶子节点, 更新叶子节点的值
		if (l == r) {
			tree[treeIndex] = newValue;
			return;
		}

		int leftTreeIndex = leftChild(treeIndex);
		int rightTreeIndex = rightChild(treeIndex);

		int mid = l + ((r - l) / 2);

		if (index >= mid + 1) {
			// 说明更新的值在 mid 的右边, 在右子树中
			set(rightTreeIndex, mid + 1, r, index, newValue);
		} else {
			// 说明更新的值在 mid 的左边, 在左子树中
			set(leftTreeIndex, l, mid, index, newValue);
		}

		// 更新了树中的某个节点, 就需要重新计算一下, 左, 右子树的值.
		tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
	}

	public int getSize() {
		return data.length;
	}

	public E get(int index) {
		if (index < 0 || index >= data.length) {
			throw new IllegalArgumentException(" Wrong index. ");
		}
		return data[index];
	}

	public int leftChild(int index) {
		return 2 * index + 1;
	}

	public int rightChild(int index) {
		return 2 * index + 2;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int index = 0; index < tree.length; index++) {
			if (tree[index] != null) {
				sb.append(tree[index]);
			} else {
				sb.append("null");
			}

			if (index != tree.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(']');
		return sb.toString();
	}

	public static void main(String[] args) {
		Integer[] nums = {-2, 0, 3, -5, 2, -1};
//		SegmentTree<Integer> segmentTree = new SegmentTree(nums, new Merger<Integer>() {
//			@Override
//			public Integer merge(Integer a, Integer b) {
//				if (a != null && b != null) {
//					return a + b;
//				} else if (a != null && b == null) {
//					return a;
//				} else {
//					return b;
//				}
//			}
//		});
		SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, (a, b) -> a + b);

		System.out.println(segmentTree);


	}
}
