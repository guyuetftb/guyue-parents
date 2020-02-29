package com.gy.datastructure.heap;

import com.gy.datastructure.queue.Queue;

/**
 * @ClassName GyPriorityQueue
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-29 15:09
 */
public class GyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

	/**
	 * 采用堆来实现优先队列, 操作方便, 可以直接复用底层的接口.
	 * 而且不用判断是否有异常， 因为底层堆就已经实现了异常的判断.
	 */
	private MaxHeap<E> maxHeap;

	public GyPriorityQueue() {
		this.maxHeap = new MaxHeap();
	}

	public boolean isEmpty() {
		return maxHeap.isEmpty();
	}

	public int getSize() {
		return maxHeap.size();
	}

	public E getFront() {
		return maxHeap.findMax();
	}

	public void enqueue(E e) {
		maxHeap.add(e);
	}

	public E dequeue() {
		return maxHeap.extractMax();
	}
}
