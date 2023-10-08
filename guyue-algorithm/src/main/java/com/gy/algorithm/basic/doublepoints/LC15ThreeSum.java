package com.gy.algorithm.basic.doublepoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC15ThreeSum {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        LC15ThreeSum lc15ThreeSum = new LC15ThreeSum();
        lc15ThreeSum.threeSum(nums);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 如果数组为null，或者 长度小于3，返回结果集
        if (null == nums || nums.length < 3) {
            return result;
        }

        // 先将数组进行排序操作，从小到大
        Arrays.sort(nums);

        // 获取数组长度
        int arrLength = nums.length;

        for (int index = 0; index < nums.length; index++) {
            // 数组元素已经大于0，不存在等于0的元素
            if (nums[index] > 0) {
                break;
            }

            // 2个相信的元素相等
            // 答案中不可以包含重复的三元组，所以需要执行一个去重的操作
            // 比如这个输入 [-4,-1,-1,0,1,2]
            // i 指向的为第一个 -1 时，left 指向的元素值为 0 ，right 指向的元素值为 1
            // i 指向的为第二个 -1 时，left 指向的元素值为 0 ，right 指向的元素值为 1
            // 这两组解都是 [ -1 , 0 , 1]，所以需要去重
            if (index > 0 && nums[index] == nums[index - 1]) {
                continue;
            }

            int leftPoint = index + 1;
            int rightPoint = arrLength - 1;
            while (leftPoint < rightPoint) {

                int sum = nums[index] + nums[leftPoint] + nums[rightPoint];

                if (sum == 0) {
                    // 把这一组解, 加入到 结果集中
                    result.add(Arrays.asList(nums[index], nums[leftPoint], nums[rightPoint]));

                    // 答案中不可以包含重复的三元组，所以需要执行一个去重的操作
                    // 比如这个输入 [-2,0,0,2,2]
                    // i 指向的元素值为 -2 ，left 指向的元素值为第一个 0 ，right 指向的元素值为倒数第一个 2 时
                    // 它们的 sum 为 0 ，如果让 ，left 向右移动一下，，right 向左移动一下，它们的 sum 也为 0
                    // 但是这两组解都是 [ -2 , 0 , 2]，所以需要去重
                    while (leftPoint < rightPoint && nums[leftPoint] == nums[leftPoint + 1]) {
                        leftPoint++;
                    }

                    while (leftPoint < rightPoint && nums[rightPoint] == nums[rightPoint - 1]) {
                        rightPoint--;
                    }
                    // 接着找下一组解， 左指针++
                    leftPoint++;

                    // 接着找下一组解， 右指针--
                    rightPoint--;

                }
                // 和小于0, 左指针右移
                // 如果三者之和小于 0 ，那么说明需要找更大的数
                else if (sum < 0) {
                    leftPoint++;
                }
                // 和大于0, 右指针左移
                // 如果三者之和大于 0 ，那么说明需要找更小的数
                else if (sum > 0) {
                    rightPoint--;
                }
            }
        }
        return result;
    }
}
