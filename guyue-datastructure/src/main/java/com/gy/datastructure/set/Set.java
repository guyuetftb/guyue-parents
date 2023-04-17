package com.gy.datastructure.set;

/**
 * @ClassName Set
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 15:58
 */
public interface Set<E> {

	void add(E e);

	void remove(E e);

	boolean isEmpty();

	int size();

	boolean contains(E e);

}
