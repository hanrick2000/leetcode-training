package bfs;

import java.util.LinkedList;
import java.util.Queue;

import tree.TreeNode;

/**
 * Given a binary tree, return the sum of values of its deepest leaves.
 * Example 1:
 * Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
 * Output: 15
 * Constraints:
 * The number of nodes in the tree is between 1 and 10^4.
 * The value of nodes is between 1 and 100.
 * 20191229
 */
public class DeepestLeavesSum {
    /**
     * 题意：求tree的最深层node的val的和。
     * 解法，白给，bfs
     */
    public int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int sum = 0;
        while (!queue.isEmpty()) {
            sum = 0;
            for (int sz = queue.size(); sz > 0; sz--) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return sum;
    }
}
