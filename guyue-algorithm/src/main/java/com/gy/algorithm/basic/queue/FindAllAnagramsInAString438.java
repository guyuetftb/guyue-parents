package com.gy.algorithm.basic.queue;

import java.util.ArrayList;
import java.util.List;

public class FindAllAnagramsInAString438 {

    public static void main(String[] args) {


    }

    public List<Integer> findAnagrams(String s, String p) {

        // 1. 定义需要维护的变量
        List<Integer> res = new ArrayList<Integer>();

        // 2. 记录 需要查找的 字符串 的每个字符 出现的次数
        int[] needChar = new int[26];
        for (char c : p.toCharArray()) {
            needChar[c - 'a']++;
        }

        // 滑动窗口中的字符
        int[] windowChar = new int[26];

        int windowStartIndex = 0;
        for (int index = 0; index < s.length(); index++) {

            // 获取将要加入窗口的字符
            char cur = s.charAt(index);

            // 对window窗口中的字符数加1
            windowChar[cur - 'a']++;

            if (isSame(windowChar, needChar)) {
                res.add(windowStartIndex);
            }

            // 如果达到了窗口左指针前移一个单位, 从而保证下一次右指针时，窗口长度不变
            if (index >= p.length() - 1) {
                // 更新维护变量
                char chr = s.charAt(windowStartIndex);

                // 最左端那个元素的频次发生变化
                windowChar[chr - 'a']--;

                // 窗口左指针向右移动一个单位，保证下一次右指针右移时，窗口长度不变.
                windowStartIndex++;
            }
        }

        return res;
    }

    boolean isSame(int[] a, int[] b) {
        for (int index = 0; index < a.length; index++) {
            if (a[index] != b[index]) {
                return false;
            }
        }
        return true;
    }
}

