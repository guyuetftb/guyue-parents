package com.gy.algorithm.basic.doublepoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这个类没有调度通过
 */
public class FourNum18 {

    public static void main(String[] args) {
        FourNum18 a = new FourNum18();
        int[] nums = new int[]{0, 0, 0, 1000000000, 1000000000, 1000000000, 1000000000};
        int target = 1000000000;
        System.out.println(a.fourSum(nums, target));
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        if (null == nums || nums.length < 4) {
            return result;
        }

        // 对数组排序
        Arrays.sort(nums);

        int arrLength = nums.length;

        // 开始遍历整个数组
        // 在遍历的过程中，观察设置的四个位置的元素之后的大小
        // 1、第一层循环
        // nums[i] 作为四个元素当中最小的那个元素
        // 需要留下 nums[len - 3] 、nums[len - 2] 、nums[len - 1] 这三个元素
        // 所以 i 的范围是 [ 0  , len - 4 ]
        for (int index = 0; index <= arrLength - 4; index++) {

            if (index > 0 && nums[index] == nums[index - 1]) {
                continue;
            }

            // 如果发现当前区间中，最小的四个元素之和都大于了 target
            // 此时，剩下的三个数无论取什么值，四数之和一定大于 target，可以直接退出第一层循环
            if ((long) nums[index] + nums[index + 1] + nums[index + 2] + nums[index + 3] > target) {
                break;
            }

            // 如果发现当前区间中，选择最大的三个数和 nums[i] 相加都小于了 target
            // 说明此时剩下的三个数无论取什么值，四数之和一定小于 target
            // 因此第一层循环直接进入下一轮，即执行 i++
            if ((long) nums[index] + nums[arrLength - 3] + nums[arrLength - 2] + nums[arrLength - 1] < target) {
                continue;
            }

            // 来到这里时，通过第一层循环，已经确定 nums[i] 这个数
            // 在 [ i + 1 , len - 1 ] 这个区间中寻找剩下的两个数
            // 2、第二层循环
            // nums[j] 作为四个元素当中第二小的那个元素
            // 需要留下 nums[len - 2] 、nums[len - 1] 这三个元素
            // 所以 j 的范围是 [ i + 1  , len - 3 ]
            for (int j = index + 1; j <= arrLength - 3; j++) {

                if (j > index + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // 如果发现当前区间中，最小的四个元素之和都大于了 target
                // 此时，剩下的三个数无论取什么值，四数之和一定大于 target，可以直接退出第二层循环
                if (nums[index] + nums[j] + nums[j + 2] + nums[j + 1] > target) {
                    break;
                }

                // 如果发现当前区间中，选择最大的三个数和 nums[i] 相加都小于了 target
                // 说明此时剩下的三个数无论取什么值，四数之和一定小于 target
                // 因此第二层循环直接进入下一轮，即执行 j++
                if (nums[index] + nums[j] + nums[arrLength - 2] + nums[arrLength - 1] < target) {
                    continue;
                }

                int leftPoint = j + 1;

                int rightPoint = arrLength - 1;

                while (leftPoint < rightPoint) {
                    int sum = nums[index] + nums[j] + nums[leftPoint] + nums[rightPoint];

                    if (sum == target) {
                        // 把这个结果记录一下
                        result.add(Arrays.asList(nums[index], nums[j], nums[leftPoint], nums[rightPoint]));

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

                        leftPoint++;

                        rightPoint--;
                    }

                    // 如果四者之和小于 0 ，那么说明需要找更大的数
                    else if (sum < target) {
                        // left 向右移动
                        leftPoint++;
                    }

                    // 如果四者之和大于 0 ，那么说明需要找更小的数
                    else if (sum > target) {
                        // right 向左移动
                        rightPoint--;
                    }
                }
            }
        }
        return result;
    }
}
