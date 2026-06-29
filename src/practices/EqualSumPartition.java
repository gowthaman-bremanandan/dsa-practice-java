package practices;

import java.util.*;

public class EqualSumPartition {

    public static void main(String[] args) {
        int[] arr = {1, 5, 11, 5};

        splitArray(arr);
    }

    public static void splitArray(int[] arr) {
        int totalSum = 0;

        for (int num : arr) {
            totalSum += num;
        }

        // If total sum is odd, cannot split equally
        if (totalSum % 2 != 0) {
            System.out.println("Cannot split into two equal sum sets.");
            return;
        }

        int target = totalSum / 2;
        int n = arr.length;

        boolean[][] dp = new boolean[n + 1][target + 1];

        // Sum 0 is always possible
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        // Build DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        if (!dp[n][target]) {
            System.out.println("Cannot split into two equal sum sets.");
            return;
        }

        // Backtrack to find first subset
        List<Integer> set1 = new ArrayList<>();
        List<Integer> set2 = new ArrayList<>();

        boolean[] used = new boolean[n];
        int i = n, j = target;

        while (i > 0 && j > 0) {
            if (dp[i - 1][j]) {
                i--;
            } else {
                set1.add(arr[i - 1]);
                used[i - 1] = true;
                j -= arr[i - 1];
                i--;
            }
        }

        // Remaining elements go to second set
        for (int k = 0; k < n; k++) {
            if (!used[k]) {
                set2.add(arr[k]);
            }
        }

        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);
    }
}