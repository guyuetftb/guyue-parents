package com.gy.datastructure.unionfind;


// 基于 size 的优化
public class UnionFind2 implements UF {

	private int parent[];
	private int sz[];

	public UnionFind2(int size) {
		// 初始化 并查集
		parent = new int[size];
		sz = new int[size];

		// 让并查集中的每个元素都指向它自己.
		// 即, 每个元素的根, 都是他自己.
		for (int index = 0; index < size; index++) {
			parent[index] = index;
			sz[index] = 1;
		}
	}


	@Override
	public int getSize() {
		return parent.length;

	}

	/**
	 * UnionFind 由于采用了树结构, 操作的时间复杂度与树的高度相关
	 */
	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}


	// 查找 e 元素所对应的集合编号
	private int find(int e) {
		if (e < 0 || e > parent.length) {
			throw new IllegalArgumentException(" Index is wrong.");
		}

		while (e != parent[e]) {
			e = parent[e];
		}

		return e;
	}

	/**
	 * UnionFind 由于采用了树结构, 合并操作的时间复杂度与树的高度相关
	 */
	@Override
	public void unionElements(int p, int q) {

		// 查找元素p所对应的集合编号
		// 查找元素q所对应的集合编号
		int pRoot = find(p);
		int qRoot = find(q);

		if (pRoot == qRoot) {
			return;
		}

		if (sz[pRoot] < qRoot) {
			// pRoot 的 size 小于 qRoot 的 size
			// 更新 pRoot 值等于 qRoot
			parent[pRoot] = qRoot;

			// 让 pRoot 指向 qRoot
			sz[qRoot] += sz[pRoot];
		} else {
			// qRoot 的 size 小于 pRoot 的 size
			// 更新 pRoot 值等于 qRoot
			parent[qRoot] = pRoot;

			// 让 qRoot 指向 pRoot
			sz[pRoot] += sz[qRoot];
		}
	}
}
