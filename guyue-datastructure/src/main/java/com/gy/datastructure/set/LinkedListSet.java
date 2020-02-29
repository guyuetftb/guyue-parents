package com.gy.datastructure.set;

import com.gy.datastructure.linkedlist.LinkedList;
import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

/**
 * @ClassName LinkedListSet
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 16:26
 */
public class LinkedListSet<E> implements Set<E> {

	private LinkedList<E> list;

	public LinkedListSet() {
		this.list = new LinkedList();
	}


	@Override
	public void add(E e) {
		if (!this.contains(e)) {
			// 2019-12-21 由于自己实现的链表没有尾指针,所以调用 addFirst 方法
			// 他的时间复杂度是 O(1) 的
			list.addFirst(e);
		}
	}

	@Override
	public void remove(E e) {
		// TODO guyue
		// LinkedList 删除元素暂时还没有实现, 后续补上.
		// list.remove
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.getSize();
	}

	@Override
	public boolean contains(E e) {
		return list.contains(e);
	}

	public static void main(String[] args) {

		System.out.println("Pride and Prejudice");

		ArrayList<String> words1 = new ArrayList<>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words1)) {
			System.out.println("Total words: " + words1.size());

			LinkedListSet<String> set1 = new LinkedListSet<>();
			for (String word : words1) {
				set1.add(word);
			}
			System.out.println("Total different words: " + set1.size());
		}

		System.out.println();

		System.out.println("A Tale of Two Cities");

		ArrayList<String> words2 = new ArrayList<>();
		if (FileOperation.readFile("a-tale-of-two-cities.txt", words2)) {
			System.out.println("Total words: " + words2.size());

			LinkedListSet<String> set2 = new LinkedListSet<>();
			for (String word : words2) {
				set2.add(word);
			}
			System.out.println("Total different words: " + set2.size());
		}
	}
}
