package practices;

import java.util.HashMap;
import java.util.Map;

public class SubArraysOfArrays {

    public static void main(String[] args) {
        int[] num = new int[]{3, 4, 7, 2, -3, 1, 4, 2};
        System.out.println(subarraySum(num, 7));
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixMap = new HashMap<>();

        prefixMap.put(0, 1); // Important: base case

        int currentSum = 0;
        int count = 0;

        for (int num : nums) {
            currentSum += num;

            // Check if (currentSum - k) exists
            if (prefixMap.containsKey(currentSum - k)) {
                count += prefixMap.get(currentSum - k);
            }

            // Store current sum
            prefixMap.put(currentSum, prefixMap.getOrDefault(currentSum, 0) + 1);
        }

        return count;
    }

    public static int countSubsets(int[] nums, int k) {
        return helper(nums, k, 0);
    }

    private static int helper(int[] nums, int k, int index) {
        if (k == 0) return 1;
        if (index == nums.length) return 0;

        // Include current element
        int include = helper(nums, k - nums[index], index + 1);

        // Exclude current element
        int exclude = helper(nums, k, index + 1);

        return include + exclude;
    }

    public static int longestSubarraySumK(int[] nums, int k) {
        Map<Integer, Integer> prefixMap = new HashMap<>();

        int currentSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];

            // Case 1: subarray from index 0 to i
            if (currentSum == k) {
                maxLength = i + 1;
            }

            // Case 2: subarray exists somewhere in middle
            if (prefixMap.containsKey(currentSum - k)) {
                int prevIndex = prefixMap.get(currentSum - k);
                maxLength = Math.max(maxLength, i - prevIndex);
            }

            // Store first occurrence only
            if (!prefixMap.containsKey(currentSum)) {
                prefixMap.put(currentSum, i);
            }
        }

        return maxLength;
    }

}
