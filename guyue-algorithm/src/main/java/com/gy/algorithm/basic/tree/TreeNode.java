package com.gy.algorithm.basic.tree;

public class TreeNode<E> {

    private E e;
    private TreeNode left;
    private TreeNode right;

    TreeNode(E e) {
        this.e = e;
        this.left = null;
        this.right = null;
    }

    public E getValue() {
        return e;
    }

    public void setValue(E e) {
        this.e = e;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
