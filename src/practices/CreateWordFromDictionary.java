package practices;

import java.util.Arrays;
import java.util.HashSet;

public class CreateWordFromDictionary {

    public static void main(String[] args) {
        String[] dictionary = {"apple", "pen"};
        String word = "applepenapple";
        System.out.println(isPossibleToCreateWord(dictionary, word));
    }

    private static boolean isPossibleToCreateWord(String[] dictionary, String word) {
        boolean[] dp = new boolean[word.length() + 1];
        HashSet<String> dictionarySet = new HashSet<>();
        dictionarySet.addAll(Arrays.stream(dictionary).toList());
        dp[0] = true;
        for (int wordEndIndex = 1; wordEndIndex <= word.length(); wordEndIndex++) {

            for (int wordStartIndex = 0; wordStartIndex < wordEndIndex; wordStartIndex++) {
                String wordPart = word.substring(wordStartIndex, wordEndIndex);
                if (dp[wordStartIndex] && dictionarySet.contains(wordPart)) {
                    dp[wordEndIndex] = true;
                    break;
                }
            }
        }

        return dp[word.length()];
    }
}
