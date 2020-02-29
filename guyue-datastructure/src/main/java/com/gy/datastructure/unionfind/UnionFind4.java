package com.gy.datastructure.unionfind;


// 基于 rank, 且有 路径压缩 的优化
public class UnionFind4 implements UF {

	private int parent[];
	private int rank[];

	public UnionFind4(int size) {
		// 初始化 并查集
		parent = new int[size];
		rank = new int[size];    // rank[i] 表示以 i 为根的集合的高度.

		// 让并查集中的每个元素都指向它自己.
		// 即, 每个元素的根, 都是他自己.
		for (int index = 0; index < size; index++) {
			parent[index] = index;
			rank[index] = 1;
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

		// 意思就是将 e 这个节点的 parent, 设置为 parent 的 parent。
		// 也就是说将 e 这个节点的 parent, 设置为 e 的 爷爷节点.
		while (e != parent[e]) {
			parent[e] = parent[parent[e]];
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

		// 这里使用 rank 进行比较
		// 深度小的树，指向深度大的树.
		// 即rank 低的集合, 指向 rank 高的集合.
		if (rank[pRoot] < rank[qRoot]) {
			// TODO 这里定反很多次了.
			// parent[pRoot] 的值是要被修改的, pRoot值小, 所以 pRoot 的 parent 值应该被改成 qRoot (大 rank ) 值
			parent[pRoot] = qRoot;
		} else if (rank[qRoot] < rank[pRoot]) {
			parent[qRoot] = pRoot;
		} else {
			// 深度相等, 谁指向谁都可以
			parent[pRoot] = qRoot;
			// 由于 某个根节点, 指向了另一个根节点, 新的根节点深度需要加1
			rank[pRoot] += 1;

		}
	}
}
