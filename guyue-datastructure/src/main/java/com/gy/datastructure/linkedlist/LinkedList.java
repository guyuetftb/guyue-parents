package com.gy.datastructure.linkedlist;

/**
 * @ClassName LinkedList
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-11 19:00
 */
public class LinkedList<E> {

	private Node dummyHead;
	private int size;


	public LinkedList() {
		this.dummyHead = new Node(null, null);
		this.size = 0;
	}

	public void addFirst(E e) {
		add(0, e);
	}

	public void addLast(E e) {
		add(size, e);
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void add(int index, E e) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("Add failed. Illegal index.");
		}

		Node prev = dummyHead;
		for (int i = 0; i < index; i++) {
			prev = prev.next;
		}
		prev.next = new Node(e, prev.next);
		size++;
	}

	public E get(int index) {
		if (0 > index || index >= size) {
			throw new IllegalArgumentException("Get failed. Illegal index.");
		}

		Node cur = dummyHead.next;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}

		return cur.e;
	}

	public void set(int index, E e) {
		if (0 > index || index >= size) {
			throw new IllegalArgumentException("Get failed. Illegal index.");
		}

		Node cur = dummyHead.next;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		cur.e = e;
	}

	public E getFirst() {
		return get(0);
	}

	public E getLast() {
		return get(size);
	}

	public E del(int index){
		if(index < 0 || index >= size){
			throw new IllegalArgumentException("Remove failed. Illegal index.");
		}
		Node prev = dummyHead;
		for(int i = 0;i < index;i++){
			prev = prev.next;
		}
		Node delNode = prev.next;
		prev.next = delNode.next;
		delNode.next = null;

		size--;
		return delNode.e;
	}

	public E delFirst(){
		return del(0);
	}

	public E delLast(){
		return del(size-1);
	}

	public boolean contains(E e) {

		Node cur = dummyHead.next;
		while (cur != null) {
			if (cur.e.equals(e)) {
				return true;
			}
			cur = cur.next;
		}

		return false;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		Node cur = dummyHead.next;
		while (cur != null) {
			ret.append(cur.e).append("->");
			cur = cur.next;
		}
		ret.append("NULL");
		return ret.toString();
	}

	private class Node {

		public E e;
		public Node next;

		public Node() {
			this(null, null);
		}

		public Node(E e) {
			this(e, null);
		}

		public Node(E e, Node next) {
			this.e = e;
			this.next = next;
		}
	}

	public static void main(String[] args) {
		LinkedList linkedList = new LinkedList();
		for (int i = 0; i < 5; i++) {
			linkedList.addFirst(i);
			System.out.println(linkedList);
		}

		linkedList.add(2,666);
		System.out.println(linkedList);

		linkedList.del(2);
		System.out.println(linkedList);

		linkedList.delFirst();
		System.out.println(linkedList);

		linkedList.delLast();
		System.out.println(linkedList);

	}
}
