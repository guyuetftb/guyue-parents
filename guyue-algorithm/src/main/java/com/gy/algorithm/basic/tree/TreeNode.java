package com.gy.algorithm.basic.tree;

public class TreeNode<E> {

    public E val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(E e) {
        this.val = e;
        this.left = null;
        this.right = null;
    }

    public E getValue() {
        return val;
    }

    public void setValue(E e) {
        this.val = e;
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
