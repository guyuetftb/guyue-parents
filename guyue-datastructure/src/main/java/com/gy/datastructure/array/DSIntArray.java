package com.gy.datastructure.array;

/**
 * @ClassName DSIntArray
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-04 17:11
 */
public class DSIntArray {

	private int[] data;
	private int size;

	public DSIntArray(int capacity) {
		data = new int[capacity];
		size = 0;
	}

	public DSIntArray() {
		this(10);
	}

	public int getSize() {
		return size;
	}

	public int getLength() {
		return data.length;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addLast(int e) {
		add(size, e);
	}

	public void addFirst(int e) {
		add(0, e);
	}


	public void add(int index, int e) {
		if (size == data.length) {
			throw new IllegalArgumentException("add element failed. The array is full.");
		}

		if (index < 0 || index > size) {
			throw new IllegalArgumentException("add element failed. Required index >= 0 && index < size. ");
		}

		for (int i = size - 1; i >= index; i--) {
			this.data[i + 1] = this.data[i];
		}

		this.data[index] = e;
		size++;
	}

	public void set(int index, int e) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Set element failed. Required index > 0 && index < size. ");
		}
		this.data[index] = e;
	}

	public int get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Get element failed. Required index > 0 && index < size. ");
		}
		return data[index];
	}

	public int find(int e) {
		for (int index = 0; index < size; index++) {
			if (data[index] == e) {
				return index;
			}
		}
		return -1;
	}

	public boolean contains(int e) {
		for (int index = 0; index < size; index++) {
			if (data[index] == e) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public int remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Removed failed. Index is Illegal. ");
		}

		int res = data[index];
		for (int i = index + 1; i < size; i++) {
			this.data[i - 1] = data[i];
		}
		size--;
		return res;
	}

	public int removeLast() {
		return remove(size);
	}

	public int removeFirst() {
		return remove(0);
	}

	public void removeElement(int e) {
		int index = find(e);
		if (index != -1) {
			remove(index);
		}
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(String.format("DSIntArray. size = %d, capacity = %d. \n", size, data
			.length));
		res.append('[');
		for (int i = 0; i < size; i++) {
			res.append(data[i]);
			if (i < size - 1) {
				res.append(",");
			}
		}
		res.append(']');
		return res.toString();
	}

	public static void main(String[] args) {
		DSIntArray dsIntArray = new DSIntArray(20);
		for (int index = 0; index < 10; index++) {
			dsIntArray.addLast(index);
		}
		System.out.println(dsIntArray.toString());

		dsIntArray.add(1, 100);
		System.out.println(dsIntArray.toString());

		dsIntArray.addFirst(-11);
		System.out.println(dsIntArray.toString());

		dsIntArray.remove(2);
		System.out.println(dsIntArray.toString());

		dsIntArray.removeElement(7);
		System.out.println(dsIntArray.toString());

		dsIntArray.removeFirst();
		System.out.println(dsIntArray.toString());
	}
}
