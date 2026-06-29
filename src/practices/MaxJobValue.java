package practices;

import java.util.*;

public class MaxJobValue {

    static class Jobs {
        public final int start;
        public final int end;
        public final int value;

        public Jobs(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> jobs = List.of(
                List.of(1, 3, 50),
                List.of(2, 5, 20),
                List.of(4, 6, 70),
                List.of(6, 7, 60)
        );

        System.out.println(maxJobValue(jobs)); // Output: 180
    }

    private static int maxJobValue(List<List<Integer>> jobs) {

        // Step 1: Convert to object
        List<Jobs> jobList = jobs.stream()
                .map(job -> new Jobs(job.get(0), job.get(1), job.get(2)))
                .toList();

        // Step 2: Sort by end time
        jobList.sort(Comparator.comparingInt(j -> j.end));

        int n = jobList.size();
        int[] dp = new int[n];

        // Base case
        dp[0] = jobList.get(0).value;

        for (int i = 1; i < n; i++) {

            int includeProfit = jobList.get(i).value;

            // Find last non-overlapping job
            int lastIndex = findLastNonOverlapping(jobList, i);

            if (lastIndex != -1) {
                includeProfit += dp[lastIndex];
            }

            // DP choice
            dp[i] = Math.max(dp[i - 1], includeProfit);
        }

        return dp[n - 1];
    }

    // Binary search to find last job that doesn't overlap
    private static int findLastNonOverlapping(List<Jobs> jobs, int currentIndex) {

        int low = 0, high = currentIndex - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (jobs.get(mid).end <= jobs.get(currentIndex).start) {
                if (mid + 1 <= high &&
                        jobs.get(mid + 1).end <= jobs.get(currentIndex).start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}