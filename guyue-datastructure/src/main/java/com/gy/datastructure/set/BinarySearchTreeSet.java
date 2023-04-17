package com.gy.datastructure.set;

import com.gy.datastructure.tree.BinarySearchTree;
import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

/**
 * @ClassName BinarySearchTreeSet
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 15:59
 */
public class BinarySearchTreeSet<E extends Comparable> implements Set<E> {

	private BinarySearchTree tree;

	public BinarySearchTreeSet() {
		this.tree = new BinarySearchTree();
	}

	@Override
	public void add(E e) {
		tree.add(e);
	}

	@Override
	public void remove(E e) {
		// 2019-12-21 删除元素暂未实现, 后期实现.

		// tree.remove(e);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return tree.getSize();
	}

	@Override
	public boolean contains(E e) {
		return tree.contains(e);
	}

	public static void main(String[] args) {

		System.out.println("Pride and Prejudice");

		ArrayList<String> words1 = new ArrayList<>();
		if (FileOperation.readFile("pride-and-prejudice.txt", words1)) {
			System.out.println("Total words: " + words1.size());

			BinarySearchTreeSet<String> set1 = new BinarySearchTreeSet<>();
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

			BinarySearchTreeSet<String> set2 = new BinarySearchTreeSet<>();
			for (String word : words2) {
				set2.add(word);
			}
			System.out.println("Total different words: " + set2.size());
		}
	}
}
