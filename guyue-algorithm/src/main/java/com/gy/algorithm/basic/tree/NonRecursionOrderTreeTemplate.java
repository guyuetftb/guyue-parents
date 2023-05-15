package com.gy.algorithm.basic.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NonRecursionOrderTreeTemplate {

    public List<Integer> midOrderTraversal(TreeNode<Integer> root) {
        List<Integer> resultList = new ArrayList<>();

        /**
         * 定义每个节点的3种状态
         */
        int leftStatus = 111;
        int rightStatus = 222;
        int upState = 333;
        int currentNodeStatus = -1;

        /**
         *  1. 对node节点进行遍历
         *  2. 初始化 当前节点状态
         */
        TreeNode<Integer> currentNode = root;
        currentNodeStatus = leftStatus;

        /**
         * 借助栈, 存储父节点信息
         */
        Stack<TreeNode> stack = new Stack<>();

        while (currentNode != null) {
            // 遍历node的left子树
            if (leftStatus == currentNodeStatus) {
                // 前序: 把值加入到 result中。
                resultList.add(currentNode.val);

                if (currentNode.left != null) {
                    // node的左子树 != null
                    // 当前节点进入stack
                    stack.push(currentNode);
                    // 继续迭代 左子树
                    currentNode = currentNode.left;
                } else {
                    // node的左子树 == null
                    // 遍历node的右子树
                    currentNodeStatus = rightStatus;
                }
            }
            // 遍历 node 的right子树
            else if (rightStatus == currentNodeStatus) {
                // 中序: 把值加入到 result中
                resultList.add(currentNode.val);

                if (currentNode.right != null) {
                    // node的 right 子树的 left子树不为nul，把 right节点加入stack
                    stack.push(currentNode);

                    // 遍历 right节点的右子树
                    currentNode = currentNode.right;

                    // 切换 状态
                    currentNodeStatus = leftStatus;
                } else {
                    // node的 right 子树 为null
                    currentNodeStatus = upState;
                }
            }
            // 遍历node 的父子树
            else if (upState == currentNodeStatus) {
                // 后序: 把值加入到 result中
                resultList.add(currentNode.val);
                TreeNode parent = null;

                // 栈不为null, 说明还没有迭代到 root节点
                if (!stack.isEmpty()) {
                    // 栈不为null, 弹出栈顶父元素
                    parent = stack.pop();

                    // 切换状态

                    if (parent.left == currentNode) {
                        // 从左子树返回的
                        currentNodeStatus = rightStatus;
                    } else if (parent.right == currentNode) {
                        // 从右子树返回的
                        currentNodeStatus = upState;
                    }
                }
                currentNode = parent;
            }
        }

        return resultList;
    }
}
