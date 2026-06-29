package practices;

import java.util.*;

public class WordDictionary {

    public static void main(String[] args){

        String word = "pineapplepenapple";
        List<String> dictionary = Arrays.asList("apple","pen","applepen","pine","pineapple");
        System.out.println(printTheCombinations(word, dictionary));

    }

    private static List<String> printTheCombinations(String word, List<String> dictionary) {
        Set<String> dict = new HashSet<>(dictionary); // faster lookup
        Map<String, List<String>> memo = new HashMap<>();
        return dfs(word, dict, memo);
    }

    private static List<String> dfs(String s, Set<String> dict, Map<String, List<String>> memo) {

        if (memo.containsKey(s)) {
            return memo.get(s);
        }

        List<String> result = new ArrayList<>();

        // base case
        if (s.isEmpty()) {
            result.add("");
            return result;
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);

            if (dict.contains(prefix)) {
                List<String> suffixWays = dfs(s.substring(i), dict, memo);

                for (String way : suffixWays) {
                    if (way.isEmpty()) {
                        result.add(prefix);
                    } else {
                        result.add(prefix + " " + way);
                    }
                }
            }
        }

        memo.put(s, result);
        return result;
    }
}