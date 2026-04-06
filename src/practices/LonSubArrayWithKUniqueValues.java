package practices;

import java.util.HashMap;
import java.util.HashSet;

public class LonSubArrayWithKUniqueValues {

    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 3, 2, 1, 4};
        int k = 1;
        System.out.println(longestSubArrayWithKUniqueValues(arr, k)); // Output: 3
    }

    private static int longestSubArrayWithKUniqueValues(int[] arr, int k) {
        HashMap<Integer, Integer> uniqueValues = new HashMap<>();
        int maxLength = Integer.MIN_VALUE;
        int startIndex = 0;
        for (int endIndex = 0; endIndex < arr.length; endIndex++) {
            int num = arr[endIndex];
            uniqueValues.put(num, uniqueValues.getOrDefault(num, 0) + 1);
            if (uniqueValues.size() <= k) {
                int length = endIndex - startIndex + 1;
                maxLength = Math.max(maxLength, length);
            } else {
                while (uniqueValues.size() > k) {
                    int numToRemove = arr[startIndex];
                    int frequencyCount = uniqueValues.getOrDefault(arr[startIndex], 0);
                    if (frequencyCount > 1) {
                        uniqueValues.put(numToRemove, uniqueValues.get(numToRemove) - 1);
                    } else {
                        uniqueValues.remove(numToRemove);
                    }
                    startIndex++;
                }
            }
        }
        return maxLength;
    }
}
