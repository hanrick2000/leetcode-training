package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.
 * <p>
 * Example:
 * <p>
 * nums = [1, 2, 3]
 * target = 4
 * <p>
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * <p>
 * Note that different sequences are counted as different combinations.
 * <p>
 * Therefore the output is 7.
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 * <p>
 * <p>
 * Created by liu chang on Sept 23, 2018.
 */
public class CombinationSum4 {

    //# DP,
    //Imagine we only need one more number to reach target, this number can be any one in the array.
    //comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].
    public int combinationSum4(int[] nums, int target) {
        if (nums == null || nums.length == 0 || target < 1) return 0;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;//In how many ways is it possible to get zero count given that all the elements are positive?" One 1 way - to not choose any element n choose 0
        return helper(dp, nums, target);
    }

    private int helper(int[] dp, int[] nums, int target) {
        if (dp[target] != -1) {// not dp[i]
            return dp[target];
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += helper(dp, nums, target - nums[i]);
            }
        }
        dp[target] = res;
        return res;
    }


//    ## recursion : Time Limit Exceeded
//    int count = 0;
//
//    public int combinationSum4(int[] nums, int target) {
//        if (nums == null || nums.length == 0 || target < 1) return 0;
//        backtrack(nums, target, new ArrayList<Integer>());
//        return count;
//    }
//
//    private void backtrack(int[] nums, int remain, List<Integer> item) {
//        if (remain < 0) return;
//        if (remain == 0) {
//            count++;
//            return;
//        }
//        for (int i = 0; i < nums.length; i++) {
//            item.add(nums[i]);
//            backtrack(nums, remain - nums[i], item);
//            item.remove(item.size() - 1);
//        }
//    }

    public static void main(String args[]) {
        int[] nums = {1, 2, 3};
        int res = new CombinationSum4().combinationSum4(nums, 4);
        System.out.println();
    }
}