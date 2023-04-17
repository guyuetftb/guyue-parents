package com.gy.datastructure.unionfind;

import java.util.Random;

/**
 * @ClassName UnionFind
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-03 15:36
 */
public class UnionFind implements UF {

	private int parent[];

	public UnionFind(int size) {
		// 初始化 并查集
		parent = new int[size];

		// 让并查集中的每个元素都指向它自己.
		// 即, 每个元素的根, 都是他自己.
		for (int index = 0; index < size; index++) {
			parent[index] = index;
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

		parent[qRoot] = pRoot;
	}

	public static double testUF(UF uf, int times) {
		int size = uf.getSize();
		Random random = new Random();
		long startTime = System.nanoTime();

		// 执行 times 次合并操作
		for (int index = 0; index < index; index++) {
			int a = random.nextInt(size);
			int b = random.nextInt(size);

			uf.unionElements(a, b);
		}

		// 执行 times 次查询操作
		for (int index = 0; index < times; index++) {
			int a = random.nextInt(size);
			int b = random.nextInt(size);
			uf.isConnected(a, b);
		}

		long endTime = System.nanoTime();
		return (endTime - startTime) / 1000000000.0;
	}

	public static void main(String[] args) {
		int size = 10000;
		int times = 10000;

//		QuickFind qf = new QuickFind(size);
//		System.out.println(" QuickFind : " + testUF(qf, times) + " s");
//
//		UnionFind uf = new UnionFind(size);
//		System.out.println(" UnionFind : " + testUF(uf, times) + " s");
//
//		System.out.println("--------------");
//
//		size = 100000;
//		times = 10000;
//
//		qf = new QuickFind(size);
//		System.out.println(" QuickFind : " + testUF(qf, times) + " s");
//
//		uf = new UnionFind(size);
//		System.out.println(" UnionFind : " + testUF(uf, times) + " s");
//
//		System.out.println("--------------");
//
//		// 这次 QuickFind 比 UnionFind 快原原因有很多种
//		// 比如 QuickFind 的 unionElements 操作, 是一个普通的循环操作, JVM 对这种操作有很好的优化.
//		// 而 UnionFind 的 unionElements 操作, 是一个不断索引的过程, 他需要在不同的地址之间进行跳转, 这是原因一
//		// 另外一个原因就是, UnionFind 类的 find 操作需要被 isConnected, unionElements 方法不断调用, 这也会影响函数的执行速度.
//		// 双因为操作数也变大了, 在 UnionFind 这个集合中树深度不断加深, 这也是影响执行速度的原因.
//		size = 100000;
//		times = 100000;
//
//		qf = new QuickFind(size);
//		System.out.println(" QuickFind : " + testUF(qf, times) + " s");
//
//		uf = new UnionFind(size);
//		System.out.println(" UnionFind : " + testUF(uf, times) + " s");
//		System.out.println("--------------");

		size = 10000000;
		times = 10000000;

		UnionFind2 uf2 = new UnionFind2(size);
		System.out.println(" UnionFind2 : " + testUF(uf2, times) + " s");

		UnionFind3 uf3 = new UnionFind3(size);
		System.out.println(" UnionFind3 : " + testUF(uf3, times) + " s");

		UnionFind4 uf4 = new UnionFind4(size);
		System.out.println(" UnionFind4 : " + testUF(uf4, times) + " s");
	}
}
