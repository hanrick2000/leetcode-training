package dfs;

/**
 * You are given coins of different denominations and a total amount of money. Write a function to compute the number of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * Example 2:
 * <p>
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 * Example 3:
 * <p>
 * Input: amount = 10, coins = [10]
 * Output: 1
 * <p>
 * 20190129
 */
public class CoinChangeII {
    //经典背包问题, knapsack problem

    //1. dfs brute force, 借鉴coin change 1的代码，TLE
//    int res = 0;
//    public int change(int amount, int[] coins) {
//        coinChange(0, coins, amount);
//        return res;
//    }
//
//    private int coinChange(int idxCoin, int[] coins, int amount) {
//        if (amount == 0) {
//            res++;
//            return 0;
//        }
//        if (idxCoin < coins.length && amount > 0) {
//            int maxVal = amount / coins[idxCoin];
//            int minCost = Integer.MAX_VALUE;
//            for (int x = 0; x <= maxVal; x++) {
//                if (amount >= x * coins[idxCoin]) {
//                    int res = coinChange(idxCoin + 1, coins, amount - x * coins[idxCoin]);
//                    if (res != -1)
//                        minCost = Math.min(minCost, res + x);
//                }
//            }
//            return (minCost == Integer.MAX_VALUE) ? -1 : minCost;
//        }
//        return -1;
//    }

    //2. dp
    // if(amount - coin >= 0) dp[amount] += dp[amount - coin]
    // 例子
    // 12, [1,2,5]
    // 第一趟：使用1元coin时，dp[2] = dp[2 - 1] + dp[2]，意思是amount等于1元时候的combination数量，加上剩下的钱的组合数量
    public int change(int amount, int[] coins) {
        if (amount == 0 && coins.length == 0) return 1;
        if (amount < 0 || coins == null || coins.length == 0) return 0;
        int[] dp = new int[amount + 1];
        dp[0] = 1;//如果有amount = 1, coin = 1，那dp[1] = dp[1 - 1]
        for (int coin : coins) {
            for (int i = 1; i <= amount; i++) {
                if (i - coin >= 0) {//剩余的钱可以用当前硬币来找钱
                    dp[i] += dp[i - coin];//not dp[i - 1]
                    System.out.print(dp[i] + " ");
                }
            }
            System.out.println();
        }
        return dp[dp.length - 1];
    }

    public static void main(String args[]) {
        int[] coins = {4};
        System.out.println(new CoinChangeII().change(7, coins));
    }
}
