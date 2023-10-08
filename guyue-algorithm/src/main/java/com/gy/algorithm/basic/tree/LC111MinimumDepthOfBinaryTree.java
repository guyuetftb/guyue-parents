package com.gy.algorithm.basic.tree;


import java.util.LinkedList;
import java.util.Queue;

public class LC111MinimumDepthOfBinaryTree {

    public static void main(String[] args) {

    }

    public int minDepth(TreeNode root) {
        // 叶子节点的左，右子节点都为null
        if (null == root) {
            return 0;
        }

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        // 最大深度
        int depth = 0;

        // 如果队列不为null
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();

            // 深度加1
            depth++;

            for (int index = 0; index < size; index++) {
                // 试探元素, 但不取出元素
                TreeNode node = nodeQueue.poll();

                // 叶子节点, 直接返回
                if (node.left == null && node.right == null) {
                    return depth;
                }

                // 把左子树加入到 队列中
                if (node.left != null) {
                    nodeQueue.add(node.left);
                }

                // 把右子树加入到 队列中
                if (node.right != null) {
                    nodeQueue.add(node.right);
                }
            }

        }

        return depth;
    }
}
