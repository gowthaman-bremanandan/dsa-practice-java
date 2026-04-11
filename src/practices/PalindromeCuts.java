package practices;

public class PalindromeCuts {

    public static void main(String[] args) {
        String str1 = "aab";
        String str2 = "ababbbabbababa";
        System.out.println(minCutsRequired(str1));
        System.out.println(minCutsRequired(str2));
        String str3 = "abcdefgh";
        System.out.println(minCutsRequired(str3));
        String str4 = "abad";
        System.out.println(minCutsRequired(str4));
    }

    private static int minCutsRequired(String str) {
        int[][] dp = new int[str.length()][str.length()];
        for (int i = 0; i < str.length(); i++) {
            dp[i][i] = 0;
        }
        for (int i = 1; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                int left = j, right = j + i;

                boolean isPalindrome = true;
                int l = left, r = right;
                while (l < r) {
                    if (str.charAt(l) != str.charAt(r)) {
                        isPalindrome = false;
                        break;
                    }
                    l++;
                    r--;
                }

                if (isPalindrome) {
                    dp[left][right] = 0;
                } else {
                    dp[left][right] = Integer.MAX_VALUE;
                    for (int k = left; k < right; k++) {
                        dp[left][right] = Math.min(
                                dp[left][right],
                                dp[left][k] + dp[k + 1][right] + 1
                        );
                    }
                }
            }
        }
        return dp[0][str.length() - 1];
    }
}
