package com.gy.algorithm.basic.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams49 {

    public static void main(String[] args) {

    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String everyStr : strs) {

            // 统计每个字符 出现的次数
            int[] everyChar = new int[26];
            for (int index = 0; index < everyStr.length(); index++) {
                everyChar[everyStr.charAt(index) - 'a']++;
            }


            //把字符串转换成Key
            StringBuilder sub = new StringBuilder();
            for (int index = 0; index < everyChar.length; index++) {
                if (everyChar[index] != 0) {
                    sub.append((char) ('a' + index)).append(everyChar[index]);
                }
            }

            //
            String key = sub.toString();

            // 获取Key list
            List list = map.getOrDefault(key, new ArrayList<>());
            list.add(everyStr);
            map.put(key, list);
        }

        return new ArrayList<>(map.values());
    }
}

