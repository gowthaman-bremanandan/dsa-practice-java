package practices;

import java.util.HashSet;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class MaxPathSumTree {

    private static int maxValue = 0;


    public static void main(String[] args) {

        /*
                Example Tree:

                     -10
                     /  \
                    9   20
                       /  \
                      15   7

                Expected Output: 42
                (15 + 20 + 7)
        */

        TreeNode root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);

        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        // Call your function here
         maxPathSum(root);
        System.out.println(maxValue);
    }

    private static void maxPathSum(TreeNode root) {
         computeTheSum(root, new HashSet<>());
    }

    private static int computeTheSum(TreeNode root, HashSet<TreeNode> visited) {
        if (root == null) {
            return 0;
        }
        if (visited.contains(root)) {
            return 0;
        }
        visited.add(root);
        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;
        int totalvalue = computeTheSum(leftNode, visited) + computeTheSum(rightNode, visited) + root.val;
        maxValue = Math.max(maxValue, totalvalue);
        return totalvalue;
    }
}