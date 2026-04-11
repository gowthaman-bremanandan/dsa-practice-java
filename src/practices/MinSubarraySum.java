package practices;

import java.util.Stack;

public class MinSubarraySum {

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        System.out.println(sumSubarrayMins(arr)); // 17
    }

    public static int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int mod = 1_000_000_007;

        Stack<Integer> stack = new Stack<>();
        long result = 0;

        for (int i = 0; i <= n; i++) {

            // Treat i == n as a dummy 0 to flush stack
            while (!stack.isEmpty() && (i == n || arr[stack.peek()] > arr[i])) {

                int mid = stack.pop();

                int left = stack.isEmpty() ? -1 : stack.peek();
                int right = i;

                int countLeft = mid - left;
                int countRight = right - mid;

                result += (long) arr[mid] * countLeft * countRight;
            }

            stack.push(i);
        }

        return (int) (result % mod);
    }
}