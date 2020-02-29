package com.gy.datastructure.queue;

/**
 * @ClassName LoopQueue
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-09 19:33
 */
public class LoopQueue<E> implements Queue<E> {

	private E[] data;
	private int front;
	private int tail;
	private int size;


	public LoopQueue(int capacity) {
		data = (E[]) new Object[capacity + 1];
		front = 0;
		tail = 0;
		size = 0;
	}

	public LoopQueue() {
		this(10);
	}

	public int getCapacity() {
		return data.length - 1;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return front == tail;
	}

	@Override
	public E getFront() {
		if (isEmpty()) {
			throw new IllegalArgumentException("Queue is empty.");
		}

		return this.data[front];
	}

	@Override
	public void enqueue(E e) {
		if ((tail + 1) % data.length == front) {
			resize(getCapacity() * 2);
		}

		data[tail] = e;
		tail = (tail + 1) % data.length;
		size++;
	}

	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new IllegalArgumentException("Queue is empty.");
		}

		E ret = data[front];
		data[front] = null;
		front = (front + 1) % data.length;
		size--;
		if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
			resize(getCapacity() / 2);
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(String.format("Queue: size = %d, capacity = %d. \n", this.size, this.getCapacity()));
		res.append("front [");
		for (int index = front; index != tail; index = (index + 1) % data.length) {
			res.append(data[index]);
			if ((index + 1) % data.length != tail) {
				res.append(", ");
			}
		}
		res.append("] tail");
		return res.toString();
	}


	private void resize(int newCapacity) {
		E[] newData = (E[]) new Object[newCapacity + 1];
		for (int index = 0; index < size; index++) {
			newData[index] = data[(index + front) % data.length];
		}

		data = newData;
		front = 0;
		tail = size;
	}

	public static void main(String[] args) {
		LoopQueue<Integer> arrayQueue = new LoopQueue<>();
		for(int index = 0;index < 20;index++){
			arrayQueue.enqueue(index);
			System.out.println(arrayQueue);

			if(index % 3 == 2){
				arrayQueue.dequeue();
				System.out.println(arrayQueue);
			}
		}
	}
}
