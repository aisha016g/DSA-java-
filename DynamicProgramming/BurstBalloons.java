import java.util.*;

public class BurstBalloons {
    public static int recursion(int[] arr, int i, int j) {

        // No balloon between i and j
        if (i > j) {
            return 0;
        }
        int maxi = 0;

        // Try every balloon as the LAST balloon to burst
        for (int k = i; k <= j; k++) {

            int coins = arr[i - 1] * arr[k] * arr[j + 1] + recursion(arr, i, k - 1) + recursion(arr, k + 1, j);
            maxi = Math.max(maxi, coins);
        }
        return maxi;
    }

    public static int memoization(int[] arr, int i, int j, int[][] dp) {
        if (i > j) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int maxi = 0;

        // Try every balloon as the LAST balloon
        for (int k = i; k <= j; k++) {

            int coins = arr[i - 1] * arr[k] * arr[j + 1]+ memoization(arr, i, k - 1, dp) + memoization(arr, k + 1, j, dp);
            maxi = Math.max(maxi, coins);
        }
        return dp[i][j] = maxi;
    }

    public static int tabulation(int[] arr, int n) {

        int[][] dp = new int[n + 2][n + 2];

        // i goes backwards because dp[i][j] depends on smaller intervals
        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {

                int maxi = 0;

                // Try every balloon as the LAST balloon
                for (int k = i; k <= j; k++) {

                    int coins = arr[i - 1] * arr[k] * arr[j + 1] + dp[i][k - 1] + dp[k + 1][j];
                    maxi = Math.max(maxi, coins);
                }
                dp[i][j] = maxi;
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {

        int[] nums = {3, 1, 5, 8};
        int n = nums.length;

        // Add 1 at both ends
        int[] arr = new int[n + 2];

        arr[0] = 1;
        arr[n + 1] = 1;

        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }


        int ans1 = recursion(arr, 1, n);
        System.out.println("Recursion: " + ans1);

        int[][] dp = new int[n + 2][n + 2];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        int ans2 = memoization(arr, 1, n, dp);
        System.out.println("Memoization: " + ans2);

        int ans3 = tabulation(arr, n);
        System.out.println("Tabulation: " + ans3);
    }
}