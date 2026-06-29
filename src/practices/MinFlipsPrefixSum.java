package practices;

import java.util.PriorityQueue;

public class MinFlipsPrefixSum {

    public static void main(String[] args) {
        int[] nums = {2, -3, -1, 4};
        System.out.println(minFlips(nums)); // Output: 1
    }

    private static int minFlips(int[] nums) {

        long prefixSum = 0;
        int flips = 0;

        // Min heap to store negative numbers
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {

            prefixSum += num;

            // Store negative numbers
            if (num < 0) {
                minHeap.offer(num);
            }

            // If prefix becomes negative → fix it
            if (prefixSum < 0) {

                int mostNegative = minHeap.poll(); // worst number
                prefixSum -= 2 * mostNegative;     // flip effect

                flips++;
            }
        }

        return flips;
    }
}