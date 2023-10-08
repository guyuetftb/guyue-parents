package com.gy.algorithm.basic.string;


/**
 * https://leetcode.cn/problems/length-of-last-word/description/?envType=study-plan-v2&envId=top-interview-150
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
 * <p>
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 */
public class LC58LengthOfLastWord {

    public static void main(String[] args) {
        String str = "   fly me   to   the moon  ";
        LC58LengthOfLastWord lc58LengthOfLastWord = new LC58LengthOfLastWord();
        int i = lc58LengthOfLastWord.lengthOfLastWord(str);
        System.out.println(i);
    }

    /**
     * 题目要求得到字符串中最后一个单词的长度，可以反向遍历字符串，寻找最后一个单词并计算其长度。
     * <p>
     * 由于字符串中至少存在一个单词，因此字符串中一定有字母。
     * 首先找到字符串中的最后一个字母，该字母即为最后一个单词的最后一个字母。
     * <p>
     * 从最后一个字母开始继续反向遍历字符串，直到遇到空格或者到达字符串的起始位置。
     * 遍历到的每个字母都是最后一个单词中的字母，因此遍历到的字母数量即为最后一个单词的长度。
     * <p>
     */
    public int lengthOfLastWord(String s) {
        // 获取字符串总长度
        int index = s.length() - 1;

        // 跳过尾部空格
        while (s.charAt(index) == ' ') {
            index--;
        }

        // index >= 0时, 并且 所在字符不是 '' 时, 循环
        int wordLength = 0;
        while (index >= 0 && s.charAt(index) != ' ') {
            wordLength++;
            index--;
        }
        return wordLength;
    }
}
