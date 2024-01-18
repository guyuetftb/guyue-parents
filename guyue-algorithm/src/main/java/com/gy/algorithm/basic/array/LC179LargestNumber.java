package com.gy.algorithm.basic.array;

import java.util.Arrays;

/**
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * <p>
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [10,2]
 * 输出："210"
 */
public class LC179LargestNumber {

    public static void main(String[] args) {
        int[] nums = new int[]{10, 2};
        LC179LargestNumber lc179LargestNumber = new LC179LargestNumber();
        String s = lc179LargestNumber.largestNumber(nums);
        System.out.println(s);
    }

    /**
     * https://leetcode.cn/problems/largest-number/solutions/716725/gong-shui-san-xie-noxiang-xin-ke-xue-xi-vn86e/
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return "";
        }

        int n = nums.length;
        //把nums转为字符串
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) {
            ss[i] = "" + nums[i];
        }

        //按字典顺序排序数组中的元素
        Arrays.sort(ss, (a, b) -> {
            String sa = a + b, sb = b + a;
            return sb.compareTo(sa);
        });

        StringBuilder sb = new StringBuilder();
        for (String s : ss) {
            sb.append(s);
        }

        int len = sb.length();
        int k = 0;
        //k小于当前索引,并且k位置上的元素==0
        while (k < len - 1 && sb.charAt(k) == '0') {
            k++;
        }
        return sb.substring(k);
    }
}
