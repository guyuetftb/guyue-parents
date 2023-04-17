package com.gy.datastructure.queue;

/**
 * @ClassName Queue
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-05 17:43
 */
public interface Queue<E> {

	int getSize();
	boolean isEmpty();
	E getFront();
	void enqueue(E e);
	E dequeue();

}
