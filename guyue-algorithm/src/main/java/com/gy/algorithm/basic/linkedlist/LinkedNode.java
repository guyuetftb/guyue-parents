package com.gy.algorithm.basic.linkedlist;

/**
 * @ClassName LinkedNode
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-09 14:16
 */
public class LinkedNode<E> {

    public E e;
    public LinkedNode next;

    public LinkedNode() {
        this.e = null;
        this.next = null;
    }

    public LinkedNode(E e, LinkedNode nextNode) {
        this.e = e;
        this.next = nextNode;
    }

    public E getValue() {
        return e;
    }

    public LinkedNode getNext() {
        return next;
    }
}
