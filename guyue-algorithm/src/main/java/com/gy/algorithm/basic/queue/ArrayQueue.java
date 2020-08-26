package com.gy.algorithm.basic.queue;

import java.util.Arrays;

/**
 * @ClassName ArrayQueue
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-08 16:39
 */
public class ArrayQueue<E> {

	public E[] data;
	public int size;

	public ArrayQueue() {
		data = (E[]) new Object[10];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void offer(E e) {
		if (size >= data.length) {
			data = Arrays.copyOf(data, size * 2);
		}
		data[size] = e;
		size++;
	}

	public E remove() {
		if (size <= 0) {
			throw new IllegalArgumentException(" Empty Queue.");
		}
		return remove(size - 1);
	}

	public E remove(int index) {
		E ele = data[index];

		// i 从 index 后一位开始, 依次覆盖前面的元素.
		// 		假如 index = 0, i = 0 + 1 = 1;
		// 		假如 index = 5, i = 5 + 1 = 6;
		// 		假如 index = 7, i = 7 + 1 = 8;
		for (int i = index + 1; i < data.length; i++) {
			data[i - 1] = data[i];
		}

		// 缩小size;
		size--;

		// 释放空间.
		data[size] = null;
		return ele;
	}
}
