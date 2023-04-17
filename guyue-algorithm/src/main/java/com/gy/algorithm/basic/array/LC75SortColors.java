package com.gy.algorithm.basic.array;

public class LC75SortColors {

    // 本题：个人刚开始理解，有点快速排序的感觉，细想过后,和快排不同;
    // 本题的解决法有点过于狭窄，缺乏一般性.
    // 思路: 通过2个指针left, right，指向 数组 2边的元素.
    // left 左侧元素都为0,1
    // right 右侧元素都为2
    // index 用于遍历数组元素.
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int index = 0;

        // 注意: 如果 这里 要改写成for循环，要少比一次.
        // for (int index = 0; index < nums.length; ) {
        while (index <= right) {
            int cur = nums[index];

            // 当前元素是红色,放到前面去
            if (cur == 0) {
                // 交换 left,index元素.
                swap(nums, left, index);

                // 移动 index
                index++;
                // 移动 left
                left++;
            } else if (cur == 1) {
                // 说明就应该在中间,不用管
                index++;
            } else if (cur == 2) {
                // 交换 right, index 元素.
                swap(nums, right, index);

                // 由于原先 right 指向的元素可能为 0、1、2 这三种的任何一种
                // 交换到了 index 后，还需要继续观察一轮，所以 index 先不移动
                // index++;

                // 移动 right
                right--;
            }
        }
    }

    public void swap(int[] nums, int i, int k) {
        int temp = nums[i];

        nums[i] = nums[k];

        nums[k] = temp;
    }
}
