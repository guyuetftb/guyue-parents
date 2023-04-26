package com.gy.algorithm.basic.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个数组 nums 和滑动窗口的大小 k，请找出『所有』『滑动窗口』里的最大值。
 */
public class SlidingWindowMaximum239 {

    public static void main(String[] args) {

    }

    /**
     * 滑动窗口的最大值:
     * 滑动窗口向右滑动的过程，实际上是就是把窗口的第一个元素删除，在尾部再添加一个元素.
     * 可以借助双端队列来实现, 每次都把尾部的数字弹出, 再把新的数字压入到头部, 然后再找队列中的最大元素。
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {

        /**
         * 双端队列：是 递减队列
         *
         * 如果队列中进来一个较小的数字，那么队列中比这个数字更小的数字就不可能再成为窗口中的最大元素了,
         * 因为这个后进来的元素元素，一定会比之前进来的元素大，而且，比之前的元素后离开窗口
         * 所以，那些先进来的，且比较小的数字，必然不可能成为最大元素，所以要弹出队列。
         */

        /**
         * 输入数据:
         * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
         * 输出: [3,3,5,5,6,7]
         */
        // 判断输入数据
        if (null == nums || nums.length <= 0 || k <= 0) {
            return new int[0];
        }

        // 构建存储最大值的结果数组
        int[] result = new int[nums.length - k + 1];

        // 双端队列
        Deque<Integer> queue = new LinkedList<>();

        // 初始化双端队列, 并使其保持 递减
        // 这里处理完了之后, 队列中的元素 不一定 是k个，有可能比 k小，因为滑动过程中，一直保持着递减
        for (int index = 0; index < k; index++) {
            // 如果队列不为空, 并且 队列的最后一个元素 小于 nums[index]，那么
            while (!queue.isEmpty() && queue.peekLast() < nums[index]) {
                // 把最后一个元素删除掉, 维护队列的递减性
                queue.removeLast();
            }

            queue.addLast(nums[index]);
        }

        // 处理完一次后, 恰好有k个元素, 是合格的滑动窗口
        result[0] = queue.peekFirst();

        for (int index = k; index < nums.length; index++) {

            // 滑动窗口已经装满了元素，向右移动会把窗口最左边的元素抛弃
            // i - k 为滑动窗口的最左边
            // 如果队列的队首元素和窗口最左边的元素相等，需要将队首元素抛出
            // 如果不写这个判断，会导致队列中会包含非当前窗口的元素
            // 比如窗口大小为 1，队列为 1 -1，此时窗口为 【 1 】,队列为 1，输出最大值 1，下一个窗口为 【 -1 】，
            // 准备移动的时候队列 1 和数组最左端元素一样，必须移除，否则队列中是 【 1，-1 】，输出的结果是 1，而 1 不在窗口 【 -1 】中
            if (queue.peekFirst() == nums[index - k]) {
                queue.removeFirst();
            }

            while (!queue.isEmpty() && queue.peekLast() < nums[index]) {
                queue.removeLast();
            }

            // 把当前元素添加到滑动窗口尾部
            queue.addLast(nums[index]);

            // 保存当前滑动窗口的最大值
            result[index - k + 1] = queue.peekFirst();
        }

        return result;
    }
}
