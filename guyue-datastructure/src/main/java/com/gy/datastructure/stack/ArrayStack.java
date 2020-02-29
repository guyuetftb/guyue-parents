package com.gy.datastructure.stack;

import com.gy.datastructure.array.DSArray;

/**
 * @ClassName ArrayStack
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-05 01:11
 */
public class ArrayStack<E> implements Stack<E> {

	DSArray<E> data;

	public ArrayStack() {
		data = new DSArray<>();
	}

	public ArrayStack(int capacity) {
		data = new DSArray<>(capacity);
	}

	@Override
	public int getSize() {
		return data.getSize();
	}

	@Override
	public E pop() {
		return data.removeLast();
	}

	@Override
	public E peek() {
		return data.getLast();
	}

	@Override
	public void push(E e) {
		data.addLast(e);
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	public int getCapacity() {
		return data.getCapacity();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(String.format("ArrayStack: size = %d, capacity = %d. \n", data.getSize(), data.getCapacity()));
		res.append("[");
		for (int index = 0; index < data.getSize(); index++) {
			res.append(data.get(index));
			if (index != data.getSize() - 1) {
				res.append(", ");
			}
		}
		res.append("] top");
		return res.toString();
	}

	public static void main(String[] args) {

		ArrayStack stack = new ArrayStack();
		for (int index = 0; index < 5; index++) {
			stack.push(index);
			System.out.println(stack);
		}

		System.out.println(stack.pop());
		System.out.println(stack);


	}
}
