package com.gy.algorithm.basic.tree;

/**
 * @ClassName BinarySearchTree
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-11-20 07:56
 */
public class BinarySearchTree<E extends Comparable<E>> {
//
//
//    private TreeNode root;
//    private int size;
//
//    public BinarySearchTree() {
//        this.root = null;
//        this.size = 0;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    // 向二分搜索树中插入元素
//    public void add(E e) {
//        if (null == root) {
//            this.root = new TreeNode(e);
//            this.size++;
//        } else {
//            add(root, e);
//        }
//    }
//
//    //
//    // 向 Node 为根的二分搜索树中插入元素 E
//    // 实现递归的方式, 因为在递归调用过程中, 这个 Node 是不断变化的
//    private void add(TreeNode treeNode, E e) {
//        Integer eValue = (Integer) e;
//        Integer treeNodeValue = ((Integer) treeNode.getValue()).intValue();
//        // 采用递归方式向二叉树中插入元素, 可以想像, 向只有1个根节点的树中插入元素
//        // 这样可以使插入操作更加简单
//        /**
//         * 几种操作终止条件:
//         * 1. 等于中间节点
//         * 2. 小于中间节点, 但是左节点为Null
//         * 3. 大于中间节点, 但是右节点为Null
//         */
//        if (eValue.intValue() == treeNodeValue.intValue()) {
//            return;
//        } else if (eValue.intValue() > treeNodeValue.intValue() && treeNode.getLeft() == null) {
//            treeNode.setLeft(new TreeNode(e));
//            size++;
//            return;
//        } else if (eValue.intValue() < treeNodeValue.intValue() && treeNode.getRight() == null) {
//            treeNode.setRight(new TreeNode(e));
//            size++;
//            return;
//        }
//
//        /**
//         * 不满足终止条件, 需要递归
//         * 1. 小于当前节点, 递归当前节点的左子树
//         * 2. 大于当前节点, 递归当前节点的右子树
//         */
//        if (eValue.intValue() > treeNodeValue.intValue()) {
//            add(treeNode.getLeft(), e);
//        } else {
//            add(treeNode.getRight(), e);
//        }
//    }
//
//    public void addAdvance(E e) {
//        root = addAdvance(root, e);
//    }
//
//
//    public TreeNode addAdvance(TreeNode treeNode, E e) {
//        Integer eValue = (Integer) e;
//        Integer treeNodeValue = ((Integer) treeNode.getValue()).intValue();
//        if (null == treeNode) {
//            // 3 种情况，有相同性质
//            // 1. 是 root 节点, 为空
//            // 2. 是 左子树 根节点, 为空
//            // 3. 是 右子树 树节点, 为空
//            size++;
//            return new TreeNode(e);
//        }
//
//        if (e.compareTo(treeNode.e) < 0) {
//            // 插入值 小于 当前节点值
//            // 把当前节点的左子树, 变成 root 节点, 进入迭代循环
//            treeNode.getLeft() = addAdvance(treeNode.getLeft(), e);
//        } else if (e.compareTo(treeNode.e) > 0) {
//            // 插入值 大小 当前节点值
//            // 把当前节点的右子树, 变成 root 节点, 进入循环迭代
//            treeNode.getRight() = addAdvance(treeNode.getRight(), e);
//        }
//        // 节点值相等, 不做任何操作.
//        return treeNode;
//    }
//
//    // 查看树中是否包含指定元素
//    public boolean contains(E e) {
//        return contains(root, e);
//    }
//
//    // 以  node 为根, 查看 node 子树中是否包含指定元素
//    public boolean contains(TreeNode treeNode, E e) {
//        if (null == treeNode) {
//            return false;
//        }
//
//        if (e.compareTo(treeNode.e) == 0) {
//            // node 等于 e, 返回true
//            return true;
//        } else if (e.compareTo(treeNode.e) < 0) {
//            return contains(treeNode.getLeft(), e);
//        } else {
//            // (e.compareTo(node.e) > 0) {
//            return contains(treeNode.getRight(), e);
//        }
//    }
//
//    // 前序遍历整个二叉树
//    public void preOrder() {
//        preOrder(root);
//    }
//
//    // 前序遍历整个二叉树的某个节点, 递归调用
//    public void preOrder(TreeNode treeNode) {
//        // 2019-12-04 当遍历一棵树时,
//        // 为了便于理解, 可以把这棵树理解为只有3个节点的简单的树.
//        // 当理解了这一点, 那遍历整棵树都是一个方式.
//        if (null == treeNode) {
//            return;
//        }
//
//        /**
//         * 而所谓的前序遍历, 中序遍历, 后序遍历
//         * 为了方便记忆, 可以理解为, 以 中心节点 为重点:
//         * 如果先遍历 中心节点,再左子树, 即为 前序遍历
//         * 如果先遍历 左子树, 再中节点, 即为 中序遍历
//         * 如果先遍历 左子树, 再右子树, 即为后序遍历
//         */
//
//        // 访问该节点
//        System.out.println(treeNode.e);
//
//        // 访问该节点的左树
//        preOrder(treeNode.getLeft());
//
//        // 访问该节点的右树
//        preOrder(treeNode.getRight());
//    }
//
//    public void preOrderNR() {
//        // 2019-12-12 by lipeng
//        // 非递归调用树的前序遍历, 需要借助 Stack 这个数据结果.
//        // 特别解释一下操作逻辑
//        // a. 前序遍历的顺序是，先当前节点，再左子树，再右子树
//        // b. Stack 的结构是 先进 后出，
//        // c. 综上所述,借助 Stack 实现遍历, node.right 需要先进栈, node.left 再进栈
//        //		弹出栈的时候, 才能实现先 left, 再right.
//        Stack stack = new Stack();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            TreeNode cur = (TreeNode) stack.pop();
//            System.out.println(cur.e);
//
//            if (cur.getRight() != null) {
//                stack.push(cur.getRight());
//            }
//
//            if (cur.getLeft() != null) {
//                stack.push(cur.getLeft());
//            }
//        }
//
//    }
//
//    public void levelOrder() {
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.add(root);
//        // 2019-12-12 by lipeng
//        // 非递归调用: 树的层序遍历, 或者广度遍历.
//        // 层序遍历需要借助 Queue 数据结构.
//        // 特别说明:
//        // 1. 层序遍历, 是每层遍历, 先把当前节点放入Queue中.
//        // 2. 再把当前节点 left-child 放入Queue中, 再把当前节点的 right-child 放到Queue中.
//        // 3. 在进入下次循环时, 从队列头部head, 取出元素, 先放入的元素先出队，如图:
//        // 			root
//        // 		B		C
//        // 	  D   E   F	   G
//        // pre-root 先入队
//        // 1. 取出 root, 输出 root
//        // 2. 把 root.left 入队, B 入队
//        // 3. 把 root.right 入队, C 入队
//        // 4. 下次循环, 取出B, 输出 B
//        // 5. 把 B.left 入队, D 入队
//        // 6. 把 B.right 入队, E 入队
//        // 7. 下次循环, 取出 C, 输出 C
//        // 8. 把 C.left 入队, F 入队
//        // 9. 把 C.right 入队, G 入队
//        while (!queue.isEmpty()) {
//            TreeNode cur = queue.remove();
//            System.out.println(cur.e);
//
//            if (null != cur.getLeft()) {
//                queue.add(cur.getLeft());
//            }
//
//            if (null != cur.getRight()) {
//                queue.add(cur.getRight());
//            }
//        }
//    }
//
//    public void midOrder() {
//        midOrder(root);
//    }
//
//    private void midOrder(TreeNode treeNode) {
//        if (null == treeNode) {
//            return;
//        }
//
//        // 访问该节点的左树
//        midOrder(treeNode.getLeft());
//
//        // 访问该节点
//        System.out.println(treeNode.e);
//
//        // 访问该节点的右树
//        midOrder(treeNode.getRight());
//    }
//
//    public void postOrder() {
//        postOrder(root);
//    }
//
//    public void postOrder(TreeNode treeNode) {
//        if (null == treeNode) {
//            return;
//        }
//
//        // 访问该节点的左树
//        midOrder(treeNode.getLeft());
//
//        // 访问该节点的右树
//        midOrder(treeNode.getRight());
//
//        // 访问该节点
//        System.out.println(treeNode.e);
//    }
//
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        // 3个参数, 要遍历的当前节点, 当前节点的深度, lianjie zi fu chuan.
//        generateSBTString(root, 0, sb);
//        return sb.toString();
//    }
//
//    private void generateSBTString(TreeNode treeNode, int depth, StringBuilder res) {
//        if (treeNode == null) {
//            res.append(generateDepthString(depth) + "null\n");
//            return;
//        }
//
//        // 存储当前节点的值
//        res.append(generateDepthString(depth) + treeNode.e + "\n");
//        // 遍历当前节点的左子树
//        generateSBTString(treeNode.getLeft(), depth + 1, res);
//        // 遍历当前节点的右子树
//        generateSBTString(treeNode.getRight(), depth + 1, res);
//    }
//
//    // 描述当前节点的深度, 深度越深, 打印出来的东西就越多.
//    private String generateDepthString(int depth) {
//        StringBuilder res = new StringBuilder();
//        for (int i = 0; i < depth; i++) {
//            res.append("--");
//        }
//
//        return res.toString();
//    }
//
//    public TreeNode getRoot() {
//        return root;
//    }
//
//    public static void main(String[] args) {
//        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//        int[] nums = {5, 3, 6, 8, 4, 2};
//        for (int index = 0; index < nums.length; index++) {
//            tree.add(nums[index]);
//        }
//
//        System.out.println("----------------- preOrder ");
//        tree.preOrder();
//        System.out.println();
//        // System.out.println(tree);
//
//        System.out.println("----------------- preOrderNR");
//        tree.preOrderNR();
//
//        System.out.println("----------------- levelOrder");
//        tree.levelOrder();
//
//        // tree.midOrder();
//    }
}
