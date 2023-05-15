package com.gy.algorithm.basic.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 2023-04-22
 * --TAG: 吴师兄算法训练营
 */
public class NonRecursionMidOrderTree94 {

    public List<Integer> midOrderTraversal(TreeNode root) {
        // 二叉树节点 结果
        List result = new ArrayList();

        // 存储二叉树 路径
        Stack<TreeNode> stack1 = new Stack();

        // 设置3种状态, 用来表示当前遍历节点进行到哪一步了.
        // 每个节点都有 left, right, up3种状态.
        // 每个节点按 左 -> 右 -> up的顺序观察每个节点.

        // 代表当前节点，左，右子树都没有遍历
        int leftState = 100;

        // 代表当前节点，左子树已经遍历, 右子树没有遍历.
        int rightState = 200;

        // 代表当前节点, 左，右子树都已经遍历.
        int upState = 300;

        // 每个节点初始化从 左 开始
        int currentNodeState = leftState;

        TreeNode node = root;
        while (node != null) {
            // Tag-1.

            // 遍历的过程中，始终去观察 currentNode的3种状态.
            if (currentNodeState == leftState) {
                /**
                 leftState说明currentNode 左，右节点都没有遍历
                 需要去观察当前节点的 左 树子树.
                 */
                if (node.getLeft() != null) {
                    // 有左子树, 保存当前Node进Stack, 继续观察 当前Node左子树
                    stack1.push(node);
                    node = node.getLeft();
                } else {
                    // 没有左子树, 切换状态为: rightState;
                    currentNodeState = rightState;
                    // 代码执行到 Tag-1.
                }
            } else if (currentNodeState == rightState) {
                ///// midOrder 前序遍历
                result.add(node.getValue());
                /**
                 rightState说明currentNode 左子树遍历完成，需要遍历右子树.
                 */
                if (node.getRight() != null) {
                    // 有右子树, 保存当前Node进Stack, 继续观察 当前Node左子树
                    stack1.push(node);
                    // 观察当前节点的右孩子节点情况。
                    node = node.getRight();
                    // ** 注意: 因为每个节点都从 左边节点开始，所以需要切换 currentNodeState 的状态.
                    currentNodeState = leftState;
                    // 代码执行到 Tag-1.
                } else {
                    // 没有右子树, 切换状态为: upState;
                    currentNodeState = upState;
                }
            } else if (currentNodeState == upState) {
                /**
                 返回当前Node父Node的位置, 通过栈顶元素来获取当前Node的父Node.
                 */
                TreeNode parent = null;
                if (!stack1.isEmpty()) {
                    // 栈不为null;
                    parent = stack1.pop();

                    if (parent.getLeft() == node) {
                        // 左节点 上升到 父节点, 把 currentNodeStatus = rightState;
                        currentNodeState = rightState;
                    } else {
                        // 右节点 上升到 父节点, 把 currentNOdeStatus = upSatte;
                        // 该步骤可以省略.
                        currentNodeState = upState;
                    }
                }
                node = parent;
                // 跳到Tag-1位置.
            }
        }
        return result;
    }
}
