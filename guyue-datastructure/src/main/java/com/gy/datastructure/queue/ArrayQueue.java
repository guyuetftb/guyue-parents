package com.gy.datastructure.queue;

import com.gy.datastructure.array.DSArray;
import java.lang.reflect.Array;

/**
 * @ClassName ArrayQueue
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-05 17:43
 */
public class ArrayQueue<E> implements Queue<E> {

	private DSArray<E> data;


	public ArrayQueue() {
		this.data = new DSArray<>();
	}

	public ArrayQueue(int capacity) {
		this.data = new DSArray<>(capacity);
	}

	@Override
	public int getSize() {
		return data.getSize();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public E getFront() {
		return data.getFirst();
	}

	@Override
	public void enqueue(E e) {
		data.addLast(e);
	}

	@Override
	public E dequeue() {
		return data.removeFirst();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(String.format("Queue: size = %d, capacity = %d. \n", data.getSize(), data.getCapacity()));
		res.append("front [");
		for (int index = 0; index < data.getSize(); index++) {
			res.append(data.get(index));
			if (index != data.getSize() - 1) {
				res.append(", ");
			}
		}
		res.append("] tail");
		return res.toString();
	}

	public static void main(String[] args) {
		ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
		for(int index = 0;index < 10;index++){
			arrayQueue.enqueue(index);
			System.out.println(arrayQueue);

			if(index % 3 == 2){
				arrayQueue.dequeue();
				System.out.println(arrayQueue);
			}
		}
	}
}
