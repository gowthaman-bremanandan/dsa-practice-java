package practices;

import java.util.Arrays;

public class SplitArrayToEqualPartition {

    public static void main(String[] args) {
        int[] arr = {4, 3, 2, 3, 5, 2, 1};
        System.out.println(canPartition(arr, 4)); // true
    }

    private static boolean canPartition(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();

        if (sum % k != 0) return false;

        int target = sum / k;

        Arrays.sort(nums); // optimization

        if (nums[nums.length - 1] > target) return false;

        boolean[] visited = new boolean[nums.length];

        return backtrack(nums, visited, k, 0, 0, target);
    }

    private static boolean backtrack(int[] nums, boolean[] visited, int k,
                                     int start, int currSum, int target) {

        // If only one bucket left → valid
        if (k == 1) return true;

        // If current bucket filled → move to next
        if (currSum == target) {
            return backtrack(nums, visited, k - 1, 0, 0, target);
        }

        for (int i = start; i < nums.length; i++) {
            if (visited[i]) continue;

            if (currSum + nums[i] > target) continue;

            visited[i] = true;

            if (backtrack(nums, visited, k, i + 1, currSum + nums[i], target)) {
                return true;
            }

            visited[i] = false;

            // 🔥 optimization: avoid duplicate work
            if (currSum == 0) break;
        }

        return false;
    }
}