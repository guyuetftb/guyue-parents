package com.gy.algorithm.basic.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstringWithConcatenationOfAllWords30 {

    public static void main(String[] args) {
        String s = "wordgoodgoodgoodbestword";
        String[] words = {"word", "good", "best", "word"};
        SubstringWithConcatenationOfAllWords30 a = new SubstringWithConcatenationOfAllWords30();
        a.findSubstring(s, words);
    }

    public List<Integer> findSubstring(String s, String[] words) {

        List<Integer> result = new ArrayList<>();
        // 测试单词长度
        int sLength = s.length();

        // 单个单词长度
        int singleWordLength = words[0].length();

        // 测试单词个数
        int wordsNum = words.length;

        // 初始化 单词频率个数
        HashMap<String, Integer> allWordFre = new HashMap<String, Integer>();
        for (String w : words) {
            allWordFre.put(w, allWordFre.getOrDefault(w, 0) + 1);
        }


        for (int index = 0; index < singleWordLength; index++) {
            // 当前窗口范围为 [ left , right ) ，左闭右开
            // right - left 为字母个数
            // 滑动窗口的左端
            int windowLeft = index;

            // 滑动窗口的右端
            int windowRight = index;

            int tempWordNum = 0;
            Map<String, Integer> tempWordFre = new HashMap<String, Integer>();

            while (windowRight + singleWordLength <= sLength) {
                String tempCurWord = s.substring(windowRight, windowRight + singleWordLength);

                windowRight += singleWordLength;

                // 截取的字符包含在 wordFre 中
                if (allWordFre.containsKey(tempCurWord)) {
                    // 对字符串 频率加1
                    tempWordFre.put(tempCurWord, tempWordFre.getOrDefault(tempCurWord, 0) + 1);

                    // 总共出现多少次
                    tempWordNum++;

                    // 统计某个单词的个数, 超出了 指定范围
                    while (tempWordFre.get(tempCurWord) > allWordFre.get(tempCurWord)) {
                        // 需要删除的 单词片断
                        String tempDelWord = s.substring(windowLeft, windowLeft + singleWordLength);
                        // 单词频率减1
                        tempWordFre.put(tempDelWord, tempWordFre.get(tempDelWord) - 1);
                        // 滑动窗口的左端移动
                        windowLeft += singleWordLength;
                        // 有效单词数 减1
                        tempWordNum--;
                    }
                }
                // 截取的字符串没有包含在 wordFre 中
                else {
                    // 窗口整体右移
                    windowLeft = windowRight;
                    // 临时统计频率清0
                    tempWordFre.clear();
                    // 临时个数清0
                    tempWordNum = 0;
                }

                // 窗口统计个数, 等于 需要统计个数
                if (tempWordNum == wordsNum) {
                    // 这里必须添加 windowLeft, 因为 windowLeft 是一点点 向右移动的, 不断变化的
                    result.add(windowLeft);
                }
            }
        }
        return result;
    }
}
