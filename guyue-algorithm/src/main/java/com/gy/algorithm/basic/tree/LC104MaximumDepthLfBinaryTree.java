package com.gy.algorithm.basic.tree;

public class LC104MaximumDepthLfBinaryTree {

    public static void main(String[] args) {

    }

    public int maxDepth(TreeNode root) {

        if (null == root) {
            return 0;
        }

        // 求左子树的最大深度
        int leftDepth = maxDepth(root.left);

        // 求右子树的最大深度
        int rightDepth = maxDepth(root.right);

        // 计算2者的最大值
        int maxDepth = Math.max(leftDepth, rightDepth);

        // 当前节点的最大深度
        return maxDepth + 1;
    }
}
