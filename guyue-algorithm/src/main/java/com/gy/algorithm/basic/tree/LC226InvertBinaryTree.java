package com.gy.algorithm.basic.tree;

public class LC226InvertBinaryTree {

    public static void main(String[] args) {

    }

    public TreeNode invertTree(TreeNode root) {
        // 当前节点为null
        if (null == root) {
            return null;
        }

        // 当前节点的left, right 都为Null, 说明是 叶子节点
        if (null == root.left && null == root.right) {
            return root;
        }

        // 递归迭代root节点的左子树
        TreeNode leftNode = invertTree(root.left);
        // 递归迭代root节点的右子树
        TreeNode rightNode = invertTree(root.right);

        // 交换2个节点
        root.left = rightNode;
        root.right = leftNode;
        return root;
    }
}

