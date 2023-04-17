package com.gy.datastructure.stack;

/**
 * @ClassName Stack
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-05 01:10
 */
public interface Stack<E> {
	int getSize();
	E pop();
	E peek();
	void push(E e);
	boolean isEmpty();
}
