package practices;

public class MakeMaxProfitForStocks {

    public static void main(String[] args) {
        int[] stocks = new int[]{1, 2, 3, 0, 2};
        System.out.println(maxProfit(stocks));
    }

    private static int maxProfit(int[] stocks) {
        if (stocks.length < 2) {
            return 0;
        }

        int n = stocks.length;
        int[] dp = new int[n];

        dp[0] = 0;
        dp[1] = Math.max(0, stocks[1] - stocks[0]);

        for (int i = 2; i < n; i++) {

            // Option 1: do nothing today
            dp[i] = dp[i - 1];

            for (int j = 0; j < i; j++) {

                int prevProfit = (j >= 2) ? dp[j - 2] : 0;

                int profit = prevProfit + (stocks[i] - stocks[j]);

                dp[i] = Math.max(dp[i], profit);
            }
        }

        return dp[n - 1];
    }
}
