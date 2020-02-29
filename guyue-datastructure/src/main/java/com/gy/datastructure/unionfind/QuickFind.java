package com.gy.datastructure.unionfind;

/**
 * @ClassName QuickFind
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-03 15:10
 */
public class QuickFind implements UF {

	private int[] id;

	public QuickFind(int size) {
		id = new int[size];
		for (int index = 0; index < size; index++) {
			id[index] = index;
		}
	}

	@Override
	public int getSize() {
		return id.length;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	private int find(int e) {
		if (e < 0 || e >= id.length) {
			throw new IllegalArgumentException(" index is wrong. index = " + e);
		}
		return id[e];
	}

	/**
	 * QuickFind的合并操作是 O(n)级别的
	 * @param p
	 * @param q
	 */
	@Override
	public void unionElements(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		if (pID == qID) {
			return;
		}

		for (int index = 0; index < id.length; index++) {
			if (id[index] == pID) {
				id[index] = qID;
			}
		}
	}
}
