package com.gy.datastructure.array;

/**
 * @ClassName DSIntArray
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-04 17:11
 */
public class DSArray<E> {

	private E[] data;
	private int size;

	public DSArray(int capacity) {
		// Java 不支持直接创建一个 泛型数组
		// 在Java1.5之后, Java支持泛型, 创建泛型数组需要 强制类型转换
		data = (E[]) new Object[capacity];
		size = 0;
	}

	public DSArray(E[] arr) {
		data = (E[]) new Object[arr.length];
		for (int index = 0; index < arr.length; index++) {
			data[index] = arr[index];
		}
		size = arr.length;
	}

	public DSArray() {
		this(10);
	}

	public int getSize() {
		return size;
	}

	public int getCapacity() {
		return data.length;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addLast(E e) {
		add(size, e);
	}

	public void addFirst(E e) {
		add(0, e);
	}


	public void add(int index, E e) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("add element failed. Required index >= 0 && index < size. ");
		}

		if (size == data.length) {
			resize(2 * data.length);
		}

		for (int i = size - 1; i >= index; i--) {
			this.data[i + 1] = this.data[i];
		}

		this.data[index] = e;
		size++;
	}

	public void set(int index, E e) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Set element failed. Required index > 0 && index < size. ");
		}
		this.data[index] = e;
	}

	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Get element failed. Required index > 0 && index < size. ");
		}
		return data[index];
	}

	public E getLast() {
		return get(size - 1);
	}

	public E getFirst() {
		return get(0);
	}

	public int find(E e) {
		for (int index = 0; index < size; index++) {
			if (data[index].equals(e)) {
				return index;
			}
		}
		return -1;
	}

	public boolean contains(E e) {
		for (int index = 0; index < size; index++) {
			if (data[index].equals(e)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(" Removed failed. Index is Illegal. ");
		}

		E res = data[index];
		for (int i = index + 1; i < size; i++) {
			this.data[i - 1] = data[i];
		}
		size--;
		this.data[size] = null;

		if ((size == data.length / 4) && (data.length / 2 != 0)) {
			resize(data.length / 2);
		}
		return res;
	}

	public E removeLast() {
		return remove(size - 1);
	}

	public E removeFirst() {
		return remove(0);
	}

	public void removeElement(E e) {
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

	private void resize(int newCapacity) {
		E[] newData = (E[]) new Object[newCapacity];
		for (int index = 0; index < size; index++) {
			newData[index] = data[index];
		}
		this.data = newData;
	}

	public void swap(int i, int j) {
		E tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	public static void main(String[] args) {
		DSArray<Integer> dsArray = new DSArray<>();
		for (int index = 0; index < 10; index++) {
			dsArray.addLast(index);
		}
		System.out.println(dsArray.toString());

		dsArray.add(1, 100);
		System.out.println(dsArray.toString());

		dsArray.addFirst(-11);
		System.out.println(dsArray.toString());

		dsArray.remove(2);
		System.out.println(dsArray.toString());

		dsArray.removeElement(7);
		System.out.println(dsArray.toString());

		dsArray.removeFirst();
		System.out.println(dsArray.toString());
	}
}
