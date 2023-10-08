package com.gy.algorithm.basic.string;

public class LC14LongestCommonPrefix {

    public static void main(String[] args) {
        String[] strArr = {"flower", "flow", "flight"};
        LC14LongestCommonPrefix lc14LongestCommonPrefix = new LC14LongestCommonPrefix();
        String s = lc14LongestCommonPrefix.longestCommonPrefix(strArr);
        System.out.println(s);
    }

    /**
     * 标签：链表
     * 当字符串数组长度为 0 时则公共前缀为空，直接返回；
     * 令最长公共前缀 ans 的值为第一个字符串，进行初始化；
     * 遍历后面的字符串，依次将其与 ans 进行比较，两两找出公共前缀，最终结果即为最长公共前缀；
     * 如果查找过程中出现了 ans 为空的情况，则公共前缀不存在直接返回；
     * 时间复杂度：O(s)O(s)O(s)，s 为所有字符串的长度之和。
     */
    public String longestCommonPrefix(String[] strArr) {
        // 数组长度为0
        if (strArr.length == 0) {
            return "";
        }

        // 设置初始result
        String result = strArr[0];

        for (int i = 1; i < strArr.length; i++) {
            int j = 0;
            // 从第0位开始, 逐个 判断 数组中每个 字符串的中的第 j 位, 是否相等
            // 注意:
            // j的位置, 要小于 strArr[i]的长度
            for (; j < result.length() && j < strArr[i].length(); j++) {
                // 遇到不相等的 字符, 退出
                if (result.charAt(j) != strArr[i].charAt(j)) {
                    break;
                }
            }

            // 重新定义 公共前缀
            result = result.substring(0, j);
            if (result.equals("")) {
                return result;
            }
        }
        return result;
    }
}
