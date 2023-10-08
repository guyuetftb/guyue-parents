package com.gy.algorithm.basic.string;


/**
 * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
 * 如果 needle 不是 haystack 的一部分，则返回  -1 。
 */
public class LC28FindTheIndexOfTheFirstOccurrenceInAString {

    public static void main(String[] args) {
        String haystack = "sadbutsad";
        String needle = "sad";
        LC28FindTheIndexOfTheFirstOccurrenceInAString lc28FindTheIndexOfTheFirstOccurrenceInAString = new LC28FindTheIndexOfTheFirstOccurrenceInAString();
        int i = lc28FindTheIndexOfTheFirstOccurrenceInAString.strStr(haystack, needle);
        System.out.println(i);
    }


    /**
     * 参考:
     * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/solutions/575568/shua-chuan-lc-shuang-bai-po-su-jie-fa-km-tb86/?envType=study-plan-v2&envId=top-interview-150
     */
    public int strStr(String haystack, String needle) {
        int hsLen = haystack.length();
        int ndLen = needle.length();
        char[] hsCharArr = haystack.toCharArray();
        char[] ndCharArr = needle.toCharArray();

        // 枚举原串的「发起点」
        for (int idxHs = 0; idxHs <= hsLen - ndLen; idxHs++) {
            // 从原串的「发起点」和匹配串的「首位」开始，尝试匹配
            int a = idxHs;
            int idxNd = 0;
            while (idxNd < ndLen && hsCharArr[a] == ndCharArr[idxNd]) {
                a++;
                idxNd++;
            }

            // 如果能够完全匹配，返回原串的「发起点」下标
            if (idxNd == ndLen) {
                return idxHs;
            }
        }
        return -1;
    }

    // ss: 原串(string)  pp: 匹配串(pattern)
    public int strStrKMP(String ss, String pp) {
        if (pp.isEmpty()) return 0;

        // 分别读取原串和匹配串的长度
        int n = ss.length(), m = pp.length();
        // 原串和匹配串前面都加空格，使其下标从 1 开始
        ss = " " + ss;
        pp = " " + pp;

        char[] s = ss.toCharArray();
        char[] p = pp.toCharArray();

        // 构建 next 数组，数组长度为匹配串的长度（next 数组是和匹配串相关的）
        int[] next = new int[m + 1];
        // 构造过程 i = 2，j = 0 开始，i 小于等于匹配串长度 【构造 i 从 2 开始】
        for (int i = 2, j = 0; i <= m; i++) {
            // 匹配不成功的话，j = next(j)
            while (j > 0 && p[i] != p[j + 1]) j = next[j];
            // 匹配成功的话，先让 j++
            if (p[i] == p[j + 1]) j++;
            // 更新 next[i]，结束本次循环，i++
            next[i] = j;
        }

        // 匹配过程，i = 1，j = 0 开始，i 小于等于原串长度 【匹配 i 从 1 开始】
        for (int i = 1, j = 0; i <= n; i++) {
            // 匹配不成功 j = next(j)
            while (j > 0 && s[i] != p[j + 1]) j = next[j];
            // 匹配成功的话，先让 j++，结束本次循环后 i++
            if (s[i] == p[j + 1]) j++;
            // 整一段匹配成功，直接返回下标
            if (j == m) return i - m;
        }

        return -1;
    }
}
