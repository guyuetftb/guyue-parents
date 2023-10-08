package com.gy.algorithm.basic.tree;

import java.util.HashMap;

public class LC105ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public static void main(String[] args) {

    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 构建root节点
        TreeNode root = new TreeNode(preorder[0]);

        //  为tree中每个节点创建 相对应的 索引位置
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int index = 0; index < inorder.length; index++) {
            // key = 元素值, val = 索引

            // 以中序序列中的元素值 inorder[i] 作为 key
            // 以位置 i 作为 value
            // 存放到哈希表中
            // 比如中序遍历序列中的元素是 [   A  ,   D  ,   E  ,   F  ,   G  ,   H  ,   M  ,   Z  ]
            // 那么这个哈希表就是以下的样子
            // | 索引 | 位置  |
            // | A | 0  |
            // | D | 1  |
            // | E | 2  |
            // | F | 3  |
            // | G | 4  |
            // | H | 5  |
            // | M | 6  |
            // | Z | 7  |
            map.put(inorder[index], index);
        }

        for (int index = 1; index < preorder.length; index++) {
            // 构建当前节点的, TreeNode
            TreeNode currentNode = new TreeNode(preorder[index]);

            // 插入到树中
            insertNode(root, currentNode, map);
        }

        return root;
    }

    private void insertNode(TreeNode root, TreeNode currentNode, HashMap<Integer, Integer> map) {
        while (root != currentNode) {
            int currentNodeIndex = map.get(currentNode.val);
            int rootNodeIndex = map.get(root.val);

            if (currentNodeIndex < rootNodeIndex) {
                // 当前Node的值小于 , root节点的值

                // 此时 root节点的 left == null, 还没有子节点
                if (root.left == null) {
                    root.left = currentNode;
                }

                // 1、如果之前 root 没有左子树，那么通过上面的代码，就设置好了 root 的左子树
                // 也就是说 node 是 root 的一个叶子节点，完成了插入操作
                // 把 root 指向 root.left 后，root 为 node，跳出了循环

                // 2、如果之前 root 已经有左子树，那么就不能直接把 node 插入到 root 的左子树上
                // 需要判断应该把 node 插入到 root 左子树上的孩子节点的那个位置上
                // 比如现在的 root 是这个样子，node 为 A
                //        G
                //       /
                //      D
                //     /  \
                //    ①   ②
                // root 已经有左子树 D 了，所以 node 应该考虑插入到 D 中的 ① 还是 ② 上面
                // 所以，把 root 指向 root.left ，继续遍历 root 的左子树的情况

                // 把 root.left 赋值给 root, 在下次循环的时候 root == currentNode，就退出循环了
                root = root.left;
            } else {
                // 当前Node的值大于 , root节点的值

                // 此时 root节点的 right == null, 还没有子节点
                if (root.right == null) {
                    root.right = currentNode;
                }

                // 把 root.right 赋值给 root, 在下次循环的时候 root == currentNode，就退出循环了
                root = root.right;
            }
        }
    }
}
