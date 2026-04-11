package practices;

public class BullonBurst {

    public static void main(String[] args) {
        int[] nums = {3, 1, 5, 8};
        System.out.println(maxCoins(nums)); // 167
    }

    private static int maxCoins(int[] nums) {
        int[] newArr = new int[nums.length + 2];
        int n = nums.length;
        newArr[0] = 1;
        newArr[newArr.length - 1] = 1;

        for (int i = 0; i < nums.length; i++) {
            newArr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];
        for (int length = 2; length < n + 2; length++) {

            for (int left = 0; left < n + 2 - length; left++) {
                int right = left + length;

                for (int middleMan = left + 1; middleMan < right; middleMan++) {
                    dp[left][right] = Math.max(dp[left][right],
                            dp[left][middleMan]
                                    + dp[middleMan][right]
                                    + (newArr[left] * newArr[middleMan] * newArr[right]));

                }
            }
        }

        return dp[0][n + 1];
    }

}
