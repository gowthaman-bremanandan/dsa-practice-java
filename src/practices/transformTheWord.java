package practices;

public class transformTheWord {

    public static void main(String[] args) {
        String word = "hello";
        String anotherWord = "";
        int transformationCost = minTransformMationCost(word, anotherWord);
        System.out.println(transformationCost); // Output: h2o
    }

    private static int minTransformMationCost(String word, String anotherWord) {
        int insertion = 1;
        int deletion = 2;
        int replace = 3;
        int[][] dp = new int[word.length() + 1][anotherWord.length() + 1];
        dp[0][0] = 0;
        for (int i = 0; i < word.length() + 1; i++) {
            dp[i][0] = i * insertion;

        }
        for (int i = 0; i < anotherWord.length() + 1; i++) {
            dp[0][i] = i * insertion;

        }
        for (int i = 1; i < word.length() + 1; i++) {
            for (int j = 1; j < anotherWord.length() + 1; j++) {
                if (word.charAt(i - 1) == anotherWord.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }
                dp[i][j] = Math.min(dp[i - 1][j] + deletion, Math.min(dp[i][j - 1] + insertion, dp[i - 1][j - 1] + replace));
            }
        }

        return dp[word.length()][anotherWord.length()];
    }
}
