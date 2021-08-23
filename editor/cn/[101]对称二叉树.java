//给定一个二叉树，检查它是否是镜像对称的。 
//
// 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
//     1
//   / \
//  2   2
// / \ / \
//3  4 4  3
// 
//
// 
//
// 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
//     1
//   / \
//  2   2
//   \   \
//   3    3
// 
//
// 
//
// 进阶： 
//
// 你可以运用递归和迭代两种方法解决这个问题吗？ 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 
// 👍 1480 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.Stack;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Stack<TreeNode> stackl = new Stack<TreeNode>();
        stackl.push(root.left);
        Stack<TreeNode> stackr = new Stack<TreeNode>();
        stackr.push(root.right);

        while (!stackl.isEmpty() && !stackr.isEmpty()) {
            TreeNode l = stackl.pop();
            TreeNode r = stackr.pop();
            if (l == null && r == null) {
                continue;
            }
            if (l == null && r  != null) {
                return false;
            }
            if (l!= null && r == null) {
                return false;
            }
            if (l.val == r.val) {
                stackl.push(l.left);
                stackl.push(l.right);
                stackr.push(r.right);
                stackr.push(r.left);
            } else {
                return false;
            }
        }
       return stackl.isEmpty() && stackr.isEmpty();


//        return isSame(root.left,root.right);

    }

//    private boolean isSame(TreeNode p,TreeNode q) {
//        if (p == null && q == null) {
//            return true;
//        }
//        if (p == null && q != null) {
//            return false;
//        }
//        if (p != null && q == null) {
//            return false;
//        }
//        if (p.val == q. val) {
//            boolean l = isSame(p.left,q.right);
//            boolean r = isSame(p.right,q.left);
//            if (l&& r) {
//                return true;
//            }
//        }
//        return false;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
