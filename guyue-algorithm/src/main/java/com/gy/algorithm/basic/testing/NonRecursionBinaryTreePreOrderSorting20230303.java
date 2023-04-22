package com.gy.algorithm.basic.testing;

import com.gy.algorithm.basic.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NonRecursionBinaryTreePreOrderSorting20230303 {

    public List<Integer> preOrderBinaryOrderSorting(TreeNode root) {

        List<Integer> result = new ArrayList<Integer>();
        // 每个Node有3种状态, left, right, up.
        // 访问每个节点，都从 左 -> 右 -> 上 的顺序.

        // 左状态
        int leftState = 100;

        // 右状态
        int rightState = 200;

        // 上状态
        int upState = 300;

        Stack<TreeNode> stack = new Stack();

        TreeNode currentNode = root;
        int tempState = leftState;
        while (currentNode != null) {
            /** 每个节点有3种状态， left, right, up */

            // left 状态
            if (leftState == tempState) {
                // preOrder
                result.add((Integer) currentNode.getValue());
                if (currentNode.getLeft() != null) {
                    // 有左子树, 当前节点进Stack
                    stack.push(currentNode);
                    // 遍历左子树
                    currentNode = currentNode.getLeft();
                } else {
                    // 没有左子树, 切换为 right 状态.
                    tempState = rightState;
                }
            }
            // right 状态
            else if (rightState == tempState) {
                // inOrder
                if (currentNode.getRight() != null) {
                    // 保存当前节点
                    stack.push(currentNode);
                    // 遍历当前节点的左子树
                    currentNode = currentNode.getRight();
                    // 切换临时状态
                    tempState = leftState;
                } else {
                    // 没有右子树, 切换为upState状态.
                    tempState = upState;
                }
            }
            // up 状态
            else if (upState == tempState) {
                // postOrder
                // 状态为 upState, 从Stack中弹出 栈顶元素
                TreeNode parent = null;
                // 不要忘记: 判断Stack是否为Null.
                if (!stack.isEmpty()) {
                    parent = stack.pop();

                    //注意: 这里判断不对， 需要判断 parent.left 是否等于 currentNode,
                    // 等于就说明是从 left 上来的.
                    // 不等于就说明是从 right 上来的.
                    if (parent.getLeft() == currentNode) {
                        // 左子树返回, 状态切为rightState
                        tempState = rightState;
                    } else {
                        // 右子树返回
                        tempState = upState;
                    }
                }
                currentNode = parent;
            }
        }
        return result;
    }
}
