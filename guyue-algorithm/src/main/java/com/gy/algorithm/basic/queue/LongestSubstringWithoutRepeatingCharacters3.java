package com.gy.algorithm.basic.queue;

import java.util.HashSet;

public class LongestSubstringWithoutRepeatingCharacters3 {

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters3 a = new LongestSubstringWithoutRepeatingCharacters3();
        System.out.println(a.lengthOfLongestSubstring("pwwkew"));
    }

    public int lengthOfLongestSubstringB(String s) {

        // 滑动窗口模板化解题，五步走策略

        // 【1、定义需要维护的变量】

        // 对于此题来说，要求是最大长度
        int maxLen = 0;

        // 同时又涉及去重，因此需要一个哈希表
        HashSet<Character> hash = new HashSet<Character>();

        // 【2、定义窗口的首尾端 (start, end)， 然后滑动窗口】

        // 窗口的左端位置从 0 开始
        int start = 0;

        // 窗口的右端位置从 0 开始，可以一直移动到尾部
        for( int end = 0 ; end < s.length() ; end++ ){

            // 【3、更新需要维护的变量, 有的变量需要一个 if 语句来维护 (比如最大最小长度)】

            // 【4、如果题目的窗口长度可变: 这个时候一般涉及到窗口是否合法的问题】
            //  如果当前窗口不合法时, 用一个 while 去不断移动窗口左指针, 从而剔除非法元素直到窗口再次合法

            // 如果哈希表中存储了即将加入滑动窗口的元素
            while(hash.contains(s.charAt(end))){

                // 那么需要不断的把窗口左边的元素移除窗口

                // 把 s.charAt(start) 移除记录
                hash.remove(s.charAt(start));

                // 窗口左端向右移动
                start++;
            }

            // 此时，滑动窗口可以接纳 s.charAt(end)
            hash.add(s.charAt(end));

            // 维护变量 maxLen
            maxLen = Math.max(maxLen,end - start + 1);

        }

        // 【5、返回所需要的答案】
        return maxLen;

    }

    public int lengthOfLongestSubstring(String s) {

        /**
         * 湍动窗口模块化解题，5步走策略
         * 1. 定义需要维护的变量
         * 2. 定义窗口的首，尾，然后滑动窗口
         * 3. 更新需要维护的变量，有的变量需要一个if语句来维护(比如最大, 最小长度)
         * 4. 如果 题目的窗口长度可变，这时候一般涉及到窗口是否合法的问题
         *      -如果当前窗口不合法，用一个while去不断移动窗口，从而移除非法元素直到窗口再次合法
         *  5. 返回需要的答案
         */

        int maxLength = 0;
        int windowStart = 0;

        HashSet<String> hashSet = new HashSet<String>();

        for (int windowEnd = 0; windowEnd < s.length(); windowEnd++) {

            String currentChar = String.valueOf(s.charAt(windowEnd));

            while (hashSet.contains(currentChar)) {

                // hash 中已经存在了 删除开始元素
                hashSet.remove(String.valueOf(s.charAt(windowStart)));

                // 窗口向右移动
                windowStart++;
            }

            // 把当前元素添加到hash中.
            hashSet.add(currentChar);

            // 计算窗口最大值
            maxLength = Math.max(maxLength, (windowEnd - windowStart) + 1);
        }

        return maxLength;
    }
}
