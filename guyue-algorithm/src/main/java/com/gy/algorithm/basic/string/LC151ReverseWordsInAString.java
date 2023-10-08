package com.gy.algorithm.basic.string;


/**
 * https://leetcode.cn/problems/reverse-words-in-a-string/description/?envType=study-plan-v2&envId=top-interview-150
 * <p>
 * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
 * <p>
 * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
 * <p>
 * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
 * <p>
 * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
 */
public class LC151ReverseWordsInAString {

    public static void main(String[] args) {
        String str = "the sky is blue";
        LC151ReverseWordsInAString lc151ReverseWordsInAString = new LC151ReverseWordsInAString();
        String s = lc151ReverseWordsInAString.reverseWords(str);
        System.out.println(s);
    }

    /**
     * 算法解析：
     * 倒序遍历字符串 sss ，记录单词左右索引边界 iii , jjj 。
     * 每确定一个单词的边界，则将其添加至单词列表 resresres 。
     * 最终，将单词列表拼接为字符串，并返回即可。
     */
    public String reverseWords(String s) {
        // 删除首尾空格
        s = s.trim();

        int slowPoint = s.length() - 1;
        int fastPoint = s.length() - 1;
        StringBuilder res = new StringBuilder();
        while (fastPoint >= 0) {

            // 目的: 搜索首个空格
            // 从右向左, 依次判断每个字符, 如果不是' '空格, 就继续
            while (fastPoint >= 0 && s.charAt(fastPoint) != ' ') {
                fastPoint--;
            }

            // 目的: 添加单词
            // 因为 fastPoint要快于 slowPoint, 所以截取 word
            res.append(s.substring(fastPoint + 1, slowPoint + 1) + " ");

            // 跳过单词间的空格
            while (fastPoint >= 0 && s.charAt(fastPoint) == ' ') {
                fastPoint--;     // 跳过单词间空格
            }

            // slowPoint 指向下个单词的尾字符
            slowPoint = fastPoint;
        }
        return res.toString().trim();
    }
}
