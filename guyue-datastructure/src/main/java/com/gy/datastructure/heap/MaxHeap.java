package com.gy.datastructure.heap;

import com.gy.datastructure.array.DSArray;
import java.util.Random;

/**
 * @ClassName MaxHeap
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-25 08:49
 */
public class MaxHeap<E extends Comparable<E>> {

	private DSArray<E> array;
	private int capacity;

	public MaxHeap() {
		this(10);
	}

	public MaxHeap(int capacity) {
		this.capacity = capacity;
		this.array = new DSArray(capacity);
	}

	public MaxHeap(E[] arr) {
		// 2019-12-29 lipeng02
		//
		// Heapify 操作, 从倒数第1个非叶子节点开始, 对其父节点进行 siftDown 操作.
		// 因为之前已经做过了很多操作, 所以这里只是简单的对每个父节点元素进行 siftDown 操作即可.
		array = new DSArray(arr);
		for (int index = parent(arr.length - 1); index >= 0; index--) {
			siftDown(index);
		}
	}


	public int size() {
		return array.getSize();
	}

	public boolean isEmpty() {
		return array.isEmpty();
	}

	// 返回完全二叉树中, 一个索引所表示的元素的父亲节点的索引
	private int parent(int index) {
		if (index <= 0) {
			throw new IllegalArgumentException("Index is illegal.");
		}
		return (index - 1) / 2;
	}

	// 返回完全二叉树中, 一索引节点表示的元素的左子树
	private int leftChild(int index) {
		// if index == 2
		// index * 2 = 4
		// index * 2 + 1 = 5
		return index * 2 + 1;
	}

	// 返回完全二叉树中, 一个索引表示的元素的右子树
	private int rightChild(int index) {
		// if index == 2
		// index * 2 = 4
		// index * 2 + 2 = 6
		return index * 2 + 2;
	}


	// 向堆中添加元素
	public void add(E e) {
		// 1. 向数组的最后一个位置添加元素
		array.addLast(e);

		// 2. 调整元素与父节点的位置
		siftUp(array.getSize() - 1);
	}

	private void siftUp(int k) {
		// 1. 判断 k 必须是大于0的
		// 2. 判断 k 所在元素 父节点, 必须小于 k 所在元素的节点,
		// 满足以上2种情况, 才交的元素的值
		while (k > 0 && array.get(parent(k)).compareTo(array.get(k)) < 0) {
			array.swap(k, parent(k));
			k = parent(k);
		}
	}

	public E findMax() {
		if (array.getSize() == 0) {
			throw new IllegalArgumentException("Can not findMax when heap is null or empty.");
		}
		return array.get(0);
	}

	public E extractMax() {
		// 1. 删除堆顶的元素
		// 2. 为了方便操作, 把堆中最小元素放到堆顶
		// 3. 然后比较堆顶元素与与 其左，右元素，让堆顶元素与最大子节点交换位置, 之后不断迭代交换位置的子节点, 直到满足堆的性质.
		E ret = findMax();

		array.swap(0, array.getSize() - 1);
		array.removeLast();
		siftDown(0);

		return ret;
	}

	// 先查找堆中的最大元素, 然后再替换
	public E replease(E e) {
		// 找到堆中的最大元素
		E ret = findMax();
		// 把堆顶的元素设置为e
		array.set(0, e);
		// 调整堆, 以满足堆的性质
		siftDown(0);
		// 返回旧的堆最大元素
		return ret;

	}

	private void siftDown(int k) {

		// k 所在节点的左孩子 比 整个堆的元素个数还要小, 就说明k所在节点已经是叶子节点了.
		// 为什么不用 右孩子呢, 因为, 右孩子的索引比左孩子的索引大, 左孩子没有了, 右孩子肯定也就没有了.

		while (leftChild(k) < array.getSize()) {
			int maxChildIndex = leftChild(k);
			int rightChildIndex = maxChildIndex + 1;
			// 有右孩子, 并且 右孩子元素比左孩子大.
			if (rightChildIndex < array.getSize() && array.get(rightChildIndex).compareTo(array.get(maxChildIndex)) > 0) {
				maxChildIndex = rightChild(k);
			}
			// data[maxChildIndex] 是 leftChild 和 rightChild 中的最大值.
			// 如果 array.get(k) 的元素值 比 array.get(maxChildIndex) 大, 停止下沉.
			if (array.get(k).compareTo(array.get(maxChildIndex)) >= 0) {
				break;
			}

			// 否则, 交换一下 k元素与 maxChildIndex 位置上的元素值.
			// 进行下一次循环, 看对于新的k值, 是否大于等于他的2个子孩子.
			array.swap(k, maxChildIndex);
			k = maxChildIndex;
		}
	}


	public static void main(String[] args) {
		double time1 = testMaxheap(false);
		double time2 = testMaxheap(true);
		System.out.println(" without heapify time1 = " + time1 + ", heapify time2 = " + time2);
	}

	public static double testMaxheap(boolean isHeapify) {
		int n = 10000000;
		Integer[] testData = new Integer[n];
		Random random = new Random();
		for (int index = 0; index < n; index++) {
			testData[index] = random.nextInt(Integer.MAX_VALUE);
		}

		long startTime = System.nanoTime();
		MaxHeap<Integer> maxHeap = null;
		if (isHeapify) {
			maxHeap = new MaxHeap<>(testData);
		} else {
			maxHeap = new MaxHeap<>();
			for (int i : testData) {
				maxHeap.add(i);
			}
		}
		long endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000.0;
		System.out.println("is_heapify = " + isHeapify + ", total_time = " + totalTime);
		return totalTime;
	}

	public static void testCreateMaxheap() {
		int n = 1000000;
		MaxHeap<Integer> maxHeap = new MaxHeap<>();
		Random random = new Random();
		for (int index = 0; index < n; index++) {
			maxHeap.add(random.nextInt(Integer.MAX_VALUE));
		}

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = maxHeap.extractMax();
		}

		for (int i = 1; i < n; i++) {
			if (arr[i - 1] < arr[i]) {
				throw new IllegalArgumentException("Error");
			}
		}

		System.out.println(" Test MaxHeap completed.");
	}

}
