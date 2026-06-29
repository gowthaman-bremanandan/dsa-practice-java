package practices;

import java.util.*;

public class SumOfSubArrays {

    public static void main(String[] args) {
        int[] nums = {3, 4, 7, 2, -3, 1, 4, 2};
        int k = 7;

        System.out.println(subarraySum(nums, k)); // Output: 2
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixMap = new HashMap<>();

        // Important: base case
        prefixMap.put(0, 1);

        int sum = 0;
        int count = 0;

        for (int num : nums) {
            sum += num;

            // Check if (sum - k) exists
            if (prefixMap.containsKey(sum - k)) {
                count += prefixMap.get(sum - k);
            }

            // Store current sum
            prefixMap.put(sum, prefixMap.getOrDefault(sum, 0) + 1);
        }

        return count;
    }
}