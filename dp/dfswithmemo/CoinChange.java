package dp.dfswithmemo;

import java.util.Arrays;

/**
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * You may assume that you have an infinite number of each kind of coin.
 * Example 1:
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 * 20191031
 */
public class CoinChange {

    /**
     * 题意：给你一些不同硬币的面值(每种面值可以用无数次)，一个总数，问你最少用多少个硬币可以达到总数。
     * 解法1. dfs + pruning， AC
     * 逆序排列，然后从大额硬币开始用，暴力搜。
     */
    int res = Integer.MAX_VALUE;

    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) return -1;
        //一定要先逆序排列，不然TLE。Java的Arrays.sort()不支持int[]传入comparator,所以手动翻转。
        Arrays.sort(coins);
        for (int i = 0, j = coins.length - 1; i < j; i++, j--) {
            int tmp = coins[j];
            coins[j] = coins[i];
            coins[i] = tmp;
        }
        helper(0, coins, 0, amount);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private void helper(int index, int[] coins, int curCount, int remain) {
        if (index == coins.length - 1) {
            if (remain % coins[index] == 0) {//这个%有两种功能，1代表remain可能已经是0了，2代表remain是最后一枚硬币的倍数
                res = Math.min(res, remain / coins[index] + curCount);
            }
        } else {
            for (int k = remain / coins[index]; k >= 0 && k + curCount < res; k--) {//从大的硬币开始用，这样可以把res尽快变小，以便pruning
                helper(index + 1, coins, curCount + k, remain - k * coins[index]);
            }
        }
    }

    /**
     * 解法2. dfs with memo, top down, code from leetcode solutions
     * memo[i]记录剩下金额i时的最小硬币数。跟上一种解法不同的是，这里每次同种面额只取一个硬币。
     */
    public int coinChange__(int[] coins, int amount) {
        if (amount < 1) return 0;
        return coinChange(coins, amount, new int[amount]);
    }

    private int coinChange(int[] coins, int rem, int[] count) {
        if (rem < 0) return -1;
        if (rem == 0) return 0;
        if (count[rem - 1] != 0) return count[rem - 1];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = coinChange(coins, rem - coin, count);
            if (res >= 0 && res < min)
                min = 1 + res;
        }
        count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return count[rem - 1];
    }

    /**
     * 解法3. dp, bottom up
     * dp[i]表示金额i时的最小硬币数。
     * dp[i] = min(dp[i], dp[i - coins[j]] + 1);
     */
    public int coinChange___(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) return -1;
        if (amount == 0) return 0;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0)
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);//这里是滚动数组，二维转一维；另外这里隐含了一个条件，如果已经计算出dp[i - coins[j]]的话才加以利用
            }
        return dp[amount] == Integer.MAX_VALUE - 1 ? -1 : dp[amount];
    }

//我的解法，DFS，有些case通不过，比如[186,419,83,408]，6249；其实就是的brute force解法，但是我不太清楚自己哪里写错了为什么通不过这个case
//    int count = 0;
//    boolean found = false;
//
//    public int coinChange(int[] coins, int amount) {
//        if (coins == null || amount < 0) return -1;
//        if (amount == 0) return 0;
//        Arrays.sort(coins);
//        for (int i = coins.length - 1; i >= 0; i--) {
//            count = 0;
//            dfs(coins, i, amount);
//            if (found) return count;
//        }
//        return -1;
//    }
//
//    private void dfs(int[] coins, int index, int remaining) {
//        if (index < 0) return;
//        if (remaining - coins[index] > 0) {
//            count++;
//            dfs(coins, index, remaining - coins[index]);
//        } else if (remaining - coins[index] < 0) {
//            dfs(coins, index - 1, remaining);
//        } else {
//            count++;
//            found = true;
//        }
//    }

//    //1. brute force (dfs)
//    public int coinChange(int[] coins, int amount) {
//        return coinChange(0, coins, amount);
//    }
//
//    private int coinChange(int idxCoin, int[] coins, int amount) {
//        if (amount == 0)
//            return 0;
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


    public static void main(String args[]) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.print(new CoinChange().coinChange(coins, amount));
    }
}