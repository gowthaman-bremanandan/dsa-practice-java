package practices;

import java.util.HashMap;

public class MaxLengthOfString {

    public static void main(String[] args) {
        String word = "ssttqrstuvqqrrtt";
        System.out.println(getTheLongestSubString(word));
    }

    private static int getTheLongestSubString(String word) {
        HashMap<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < word.length(); right++) {
            char ch = word.charAt(right);

            // If duplicate found, move left
            if (map.containsKey(ch)) {
                left = Math.max(left, map.get(ch) + 1);
            }

            map.put(ch, right);

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}