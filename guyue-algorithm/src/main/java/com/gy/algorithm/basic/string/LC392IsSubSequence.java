package com.gy.algorithm.basic.string;

public class LC392IsSubSequence {

    public static void main(String[] args) {
        String subStr = "abc";
        String srcStr = "ahbgdc";
        LC392IsSubSequence lc392IsSubSequence = new LC392IsSubSequence();
        boolean subsequence = lc392IsSubSequence.isSubsequence(subStr, srcStr);
        System.out.println(subsequence);
    }

    /**
     * 设置双指针 i , j 分别指向字符串 s , t 的首个字符，遍历字符串 t ：
     * <p>
     * 1. 当 s[i] == t[j] 时，代表匹配成功，此时同时 i++ , j++ ；
     * 2. 进而，若 i 已走过 s 尾部，代表 s 是 t 的子序列，此时应提前返回 true ；
     * 3. 当 s[i] != t[j] 时，代表匹配失败，此时仅 j++ ；
     * 4. 若遍历完字符串 t 后，字符串 s 仍未遍历完，代表 s 不是 t 的子序列，此时返回 false 。
     */
    public boolean isSubsequence(String subStr, String srcStr) {
        if (subStr.length() == 0) {
            return true;
        }

        int idxSub = 0;
        int idxSrc = 0;
        for (; idxSrc < srcStr.length(); idxSrc++) {
            // 逐个判断 subStr 的字符 与 srcStr的字符是否相等
            if (subStr.charAt(idxSub) == srcStr.charAt(idxSrc)) {
                // 已经遍历到sub串的结尾 ，则提前返回 true
                if (++idxSub == subStr.length()) {
                    return true;
                }
            }
        }
        return false;
    }
}
