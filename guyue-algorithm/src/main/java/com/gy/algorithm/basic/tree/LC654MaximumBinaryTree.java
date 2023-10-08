package com.gy.algorithm.basic.tree;

public class LC654MaximumBinaryTree {

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return traversal(nums, 0, nums.length - 1);
    }

    private TreeNode traversal(int[] nums, int left, int right) {
        // 左侧索引 已经大于 右侧索引
        if (left > right) {
            return null;
        }

        // 左侧索引  == 右侧索引, 只有一个节点
        if (left == right) {
            return new TreeNode(nums[left]);
        }

        // 值最大的索引
        int maxValIndex = left;

        // 寻找该区间内的 数值最大的 索引值
        for (int index = left + 1; index <= right; index++) {
            if (nums[index] > nums[maxValIndex]) {
                maxValIndex = index;
            }
        }

        // 创建root节点
        TreeNode root = new TreeNode(nums[maxValIndex]);

        // 创建左子树
        TreeNode leftNode = traversal(nums, left, maxValIndex - 1);

        // 创建右子树
        TreeNode rightNode = traversal(nums, maxValIndex + 1, right);

        root.left = leftNode;
        root.right = rightNode;

        return root;
    }
}
