package com.gy.algorithm.basic.hash;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfAnArray697 {

    public static void main(String[] args) {
        DegreeOfAnArray697 a = new DegreeOfAnArray697();
        int[] nums = new int[]{1, 2, 2, 3, 1};
        System.out.println(a.findShortestSubArray(nums));
    }

    public int findShortestSubArray(int[] nums) {

        HashMap<Integer, int[]> degree = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {

            if (degree.containsKey(nums[index])) {

                // 出现频率加1
                degree.get(nums[index])[0]++;
                // 更新最后频率
                degree.get(nums[index])[2] = index;
            } else {
                // 初始化
                degree.put(nums[index], new int[]{1, index, index});
            }
        }

        int maxDegree = 0;
        int minDistance = 0;

        for (Map.Entry<Integer, int[]> e : degree.entrySet()) {
            int[] value = e.getValue();
            if (value[0] > maxDegree) {
                maxDegree = value[0];
                minDistance = value[2] - value[1] + 1;
            } else if (value[0] == maxDegree) {
                if (minDistance > value[2] - value[1] + 1) {
                    minDistance = value[2] - value[1] + 1;
                }
            }
        }
        return minDistance;
    }
}
