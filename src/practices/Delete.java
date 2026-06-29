package practices;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Delete {

    static class Jobs {
        int start;
        int end;
        int profit;

        public Jobs(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.profit = value;
        }
    }

    public static void main(String[] args) {
        int[] start = new int[]{1,2,3,3};
        int[] end = new int[]{3,4,5,6};
        int[] profit = new int[]{50,10,40,70};
        System.out.println(calculateTheMaxProfit(start, end, profit));
    }

    private static int calculateTheMaxProfit(int[] start, int[] end, int[] profit) {
        List<Jobs> jobs = new ArrayList<>();

        for (int i = 0; i < start.length; i++) {
            jobs.add(new Jobs(start[i], end[i], profit[i]));
        }

        jobs.sort((a, b) -> Integer.compare(a.start, b.start));

        int[] dp = new int[start.length];
        dp[0] = jobs.get(0).profit;

        for (int i = 1; i < start.length; i++) {
            int currentProfit = jobs.get(i).profit;
            int currentEnd = jobs.get(i).end;
            int lastIndexToConsider = getLastNonOverlappingJob(jobs, i);
            if (lastIndexToConsider != -1) {
                currentProfit += dp[lastIndexToConsider];
            }

            dp[i] = Math.max(dp[i - 1], currentProfit);

        }


        return dp[start.length - 1];
    }

    private static int getLastNonOverlappingJob(List<Jobs> jobs, int i) {
        int left = 0;
        int right = i - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid < i && jobs.get(mid).end <= jobs.get(i).start) {
                if(jobs.get(mid + 1).end <= jobs.get(i).start){
                    left = mid + 1;
                } else {
                    return mid;
                }

            } else {
                right = mid - 1;
            }
        }
        return -1;
    }


}
