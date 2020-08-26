package com.gy.algorithm.basic.stack;

import java.util.Arrays;

/**
 * @ClassName ArrayStack
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-08 16:28
 */
public class ArrayStack<E> {

	E[] data;
	int size;

	public ArrayStack() {
		data = (E[]) new Object[10];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E peek() {
		if (size == 0) {
			throw new IllegalArgumentException(" Stack empty.");
		}
		return data[size - 1];
	}

	public void push(E i) {
		if (size >= data.length) {
			data = Arrays.copyOf(data, size * 2);
		}
		data[size] = i;
		size++;
	}

	public E pop() {
		if (size <= 0) {
			throw new IllegalArgumentException(" Stack empty.");
		}
		E ele = data[size - 1];
		data[size - 1] = null;
		return ele;
	}

	public int size() {
		return size;
	}
}
