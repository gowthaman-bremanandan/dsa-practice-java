package practices;

import java.util.Arrays;

public class LongestSequence {

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 4, 7};
        findLongestSequence(arr);
    }

    private static void findLongestSequence(int[] arr) {
        int n = arr.length;

        int[] dp = new int[n];
        int[] count = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);

        int maxLength = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {

                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];   // inherit count

                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] += count[j];  // add ways
                    }
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        int totalCount = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == maxLength) {
                totalCount += count[i];
            }
        }

        System.out.println("Length = " + maxLength + ", Count = " + totalCount);
    }
}