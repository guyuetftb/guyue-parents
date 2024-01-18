package com.gy.algorithm.basic.array;

import com.gy.algorithm.basic.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
 * <p>
 * 实现 Solution class:
 * <p>
 * Solution(int[] nums) 使用整数数组 nums 初始化对象
 * int[] reset() 重设数组到它的初始状态并返回
 * int[] shuffle() 返回数组随机打乱后的结果
 * <p>
 * <p>
 * 示例 1：
 * 输入
 * ["Solution", "shuffle", "reset", "shuffle"]
 * [[[1, 2, 3]], [], [], []]
 * 输出
 * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
 * <p>
 * 解释
 * Solution solution = new Solution([1, 2, 3]);
 * solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
 * solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
 * solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
 */
public class LC384ShuffleAnArray {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        LC384ShuffleAnArray lc384ShuffleAnArray = new LC384ShuffleAnArray(nums);
        int[] shuffle = lc384ShuffleAnArray.shuffle();
        Arrays.stream(shuffle).forEach(System.out::println);
        System.out.println("------------------------------------------------");
        int[] reset = lc384ShuffleAnArray.reset();
        Arrays.stream(reset).forEach(System.out::println);
    }

    private int[] nums;
    private int n;
    private Random random = new Random();

    public LC384ShuffleAnArray(int[] _nums) {
        nums = _nums;
        n = nums.length;
    }

    /**
     * 洗牌算法
     * 共有 n 个不同的数，根据每个位置能够选择什么数，共有 n! 种组合。
     * <p>
     * 题目要求每次调用 shuffle 时等概率返回某个方案，或者说每个元素都够等概率出现在每个位置中。
     * <p>
     * 我们可以使用 Knuth 洗牌算法，在 O(n) 复杂度内等概率返回某个方案。
     * 具体的，我们从前往后尝试填充 [0,n−1] 该填入什么数时，通过随机当前下标与（剩余的）哪个下标进行值交换来实现。
     * 对于下标 x 而言，我们从 [x,n−1] 中随机出一个位置与 x 进行值交换，
     * 当所有位置都进行这样的处理后，我们便得到了一个公平的洗牌方案。
     * 对于下标为 0 位置，从 [0,n−1] 随机一个位置进行交换，共有 n 种选择；
     * 下标为 1 的位置，从 [1,n−1] 随机一个位置进行交换，共有 n−1 种选择 ...
     * <p>
     * 链接：https://leetcode.cn/problems/shuffle-an-array/solutions/1114958/gong-shui-san-xie-xi-pai-suan-fa-yun-yon-0qmy/
     *
     * @return
     */
    public int[] reset() {
        return nums;
    }

    public int[] shuffle() {
        int[] ans = nums.clone();
        for (int i = 0; i < n; i++) {
            swap(ans, i, i + random.nextInt(n - i));
        }
        return ans;
    }

    void swap(int[] arr, int i, int j) {
        int c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    public int[] shuffle_ME() {
        int[] ans = nums.clone();
        for (int index = 0; index < n; index++) {
            swap(ans, index, random.nextInt(n - index));
        }
        return ans;
    }

    public void swap_ME(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    // https://leetcode.cn/problems/shuffle-an-array/solutions/1114958/gong-shui-san-xie-xi-pai-suan-fa-yun-yon-0qmy/
}
