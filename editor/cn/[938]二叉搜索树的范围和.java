//给定二叉搜索树的根结点 root，返回值位于范围 [low, high] 之间的所有结点的值的和。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [10,5,15,3,7,null,18], low = 7, high = 15
//输出：32
// 
//
// 示例 2： 
//
// 
//输入：root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10
//输出：23
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [1, 2 * 104] 内 
// 1 <= Node.val <= 105 
// 1 <= low <= high <= 105 
// 所有 Node.val 互不相同 
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 207 👎 0


//leetcode submit region begin(Prohibit modification and deletion)



import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.DelayQueue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 * 
 *            10
 *        5          15
 *     3     7    N      18   
 */
class Solution {
    // 解法 2 迭代
    public int rangeSumBST(TreeNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        int sum = 0;
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if (poll == null) {
                continue;
            }
            if (poll.val > high) {
                queue.add(poll.left);
            } else if (poll.val < low) {
                queue.add(poll.right);
            } else {
                sum += poll.val;
                queue.add(poll.left);
                queue.add(poll.right);
            }
        }
        return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)






    // 解法1 递归
//    public int rangeSumBST(TreeNode node, int low, int high) {
//        if (node == null) return 0;
//        int sum = 0;
//
//        if (node.val > high) {
//            sum +=rangeSumBST(node.left,low,high);
//        } else if (node.val < low) {
//            sum +=rangeSumBST(node.right,low,high);
//        } else {
//            sum +=node.val;
//            sum +=rangeSumBST(node.left,low,high);
//            sum +=rangeSumBST(node.right,low,high);
//        }
//        return sum;
//    }