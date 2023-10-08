package com.gy.algorithm.basic.tree;

public class LC110BalancedBinaryTree {

    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }

    private int recur(TreeNode curNode) {
        // 当前节点是Null, 迭代到某个节点的叶子节点
        if (null == curNode) {
            return 0;
        }

        // 计算左子树的高度
        int leftHigh = recur(curNode.left);

        // 说明左子树中已经有子节点的高度超过了2
        if (leftHigh == -1) {
            return -1;
        }

        // 计算右子树的高度
        int rightHigh = recur(curNode.right);

        // 说明在右子树中已经有子节点的高度超过2
        if (rightHigh == -1) {
            return -1;
        }

        // 计算左，右子树的高度绝对值, 如果小于2, 则把高度最大值 + 1后返回
        return Math.abs(leftHigh - rightHigh) < 2 ? Math.max(leftHigh, rightHigh) + 1 : -1;
    }
}
