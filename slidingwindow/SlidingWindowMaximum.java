package slidingwindow;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

import linkedlist.SortList;

/**
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
 * <p>
 * Example:
 * <p>
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * <p>
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * 20190401
 */
public class SlidingWindowMaximum {
    /**
     * 《剑指offer》原题
     * 题意是一个sliding window不停右移，每移动1位就记录当前window中最大的数字记录下来，最后输出。
     * <p>
     * approach1. O(nlogn)解法，slidingWindow + heap(priorityQueue)
     * 维护一个size为k的最大堆（这个手法在TopKFrequentElements等题也用到）,不停地往里加就完事了；同时要把移出window的数字remove掉，注意heap是支持remove具体数字的。
     * reHeap的复杂度是log n，总时间O(nlogn)
     */
    public int[] maxSlidingWindow__HEAP(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (heap.size() < k) {
                heap.offer(nums[i]);
            }
            if (heap.size() == k) {
                res[i - k + 1] = heap.peek();
                heap.remove(nums[i - k + 1]);
            }
        }
        return res;
    }


    /**
     * approach2. 双端队列O(n)解法，思维难度挺高的，队头保存window中的最大数的index，添加新数的时候不断从队尾移出比新数小的数的index
     * 是个单调队列（单调下降）
     * https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation
     */
    public int[] maxSlidingWindow(int[] a, int k) {
        if (a == null || k <= 0) return new int[0];
        int n = a.length;
        int[] res = new int[n - k + 1];
        int ri = 0;
        // store index
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            // remove numbers out of range k
            if (!q.isEmpty() && q.peek() < i - k + 1) { // peek() 返回 the first element of this deque
                q.poll();//相当于pollFirst，也就是队列正常的出队顺序，把左边的out of window的index移出
            }
            // remove smaller numbers in k range as they are useless
            while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
                //从队尾开始(最近加入的)，把小于即将加入的a[i]的数出队，这样就保证队头(peek)永远是window中的最大数
                q.pollLast();
            }
            // q contains index... r contains content
            q.offer(i);
            if (i >= k - 1) {
                //队头(peek)永远是window中的最大数
                res[ri++] = a[q.peek()];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        new SlidingWindowMaximum().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
    }

}
