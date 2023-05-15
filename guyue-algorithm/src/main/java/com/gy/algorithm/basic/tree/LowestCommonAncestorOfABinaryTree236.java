package com.gy.algorithm.basic.tree;

public class LowestCommonAncestorOfABinaryTree236 {

    public static void main(String[] args) {

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 1、如果此时访问的节点 root 为 null，那么直接返回 null
        if (null == root) {
            return null;
        }

        // 2、如果此时访问的节点 root 为指定节点 p，那么返回 p 这个节点
        if (root == p) {
            return p;
        }

        // 3、如果此时访问的节点 root 为指定节点 q，那么返回 q 这个节点
        if (root == q) {
            return q;
        }

        // 4、经过上面 1、2、3 的判断之后，root 这个节点必然不是 p、q、null 这三种情况的节点
        // 接下来，去递归的判断当前节点 root 的左右子树是否包含 p 、q 这两个指定节点


        // 5、递归的去查找 root 的左子树，判断是否包含p 、q 这两个指定节点
        // 如果 root 的左子树节点和它的左子树所有节点中包含 p，那么 left 的值就是 p
        // 如果 root 的左子树节点和它的左子树所有节点中包含 q，那么 left 的值就是 q
        // 如果 root 的左子树节点和它的左子树所有节点中既不包含 p，也不包含 q，那么 left 的值就是 null
        TreeNode left = lowestCommonAncestor(root.left, p, q);

        // 6、递归的去查找 root 的右子树，判断是否包含p 、q 这两个指定节点
        // 如果 root 的右子树节点和它的右子树所有节点中包含 p，那么 right 的值就是 p
        // 如果 root 的右子树节点和它的右子树所有节点中包含 q，那么 right 的值就是 q
        // 如果 root 的右子树节点和它的右子树所有节点中既不包含 p，也不包含 q，那么 right 的值就是 null
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 7、判断完当前节点 root 、 root 的左子树 left、root 的右子树 right 的情况后
        // 开始向父节点传递信息

        // 8、如果 root 节点的左子树 left 和右子树 right 都没有找到指定节点 p、q
        // 并且 root 自己本身也不是指定节点 p、q
        // 那么它给父节点传递的信息就是以 root 为根节点的那颗二叉树没有指定节点 p、q
        if (null == right && null == left) {
            return null;
        }
        // 9、如果在 root 节点的左子树 left 中没有找到指定节点 p、q
        // 那么以 root 为根节点的那颗二叉树能不能找到指定节点 p、q  完全取决于 right 了
        else if (null == left) {
            return right;
        }
        // 10、如果在 root 节点的右子树 right 中没有找到指定节点 p、q
        // 那么以 root 为根节点的那颗二叉树能不能找到指定节点 p、q  完全取决于 left 了
        else if (null == right) {
            return left;
        }
        // 11、此时，left != null 并且 right != null
        // 这说明在以 root 节点为根节点的那棵二叉树中找到了指定节点 p ，也找到了指定节点 q
        // 那么就告诉父节点，root 就是 p、q 的最近公共祖先
        else {
            return root;
        }
    }
}
