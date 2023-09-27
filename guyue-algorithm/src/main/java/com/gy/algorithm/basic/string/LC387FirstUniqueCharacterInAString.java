package com.gy.algorithm.basic.string;

import java.util.HashMap;

public class LC387FirstUniqueCharacterInAString {

    public static void main(String[] args) {
        String str1 = "google";
        String str2 = "loveleetcode";
        LC387FirstUniqueCharacterInAString lc387FirstUniqueCharacterInAString = new LC387FirstUniqueCharacterInAString();
        int index1 = lc387FirstUniqueCharacterInAString.firstUniqChar(str1);
        if (-1 != index1) {
            System.out.println(str1.charAt(index1));
        }

        int index2 = lc387FirstUniqueCharacterInAString.firstUniqChar(str2);
        if (-1 != index2) {
            System.out.println(str2.charAt(index2));
        }
    }

    /**
     * 要解决2个问题
     * 1. 如何判断元素是不是 首次.
     * 2. 如何找出 第1个首次.
     */
    public int firstUniqChar(String s) {
        HashMap<Character, Boolean> dictMap = new HashMap<>();
        char[] sc = s.toCharArray();
        /**
         * 解决了是不是第一次
         */
        for (char c : sc) {
            /**
             * 巧妙:
             * 1. 如果不包含: dictMap.containsKey(c) = false, 再取反 就是 true.
             * 2. 如果包含: dictMap.containsKey(c) = true, 再取反就是 false.
             * 重点: 变成false之后, 不影响下次判断, map已经包含, 下次依然包含
             */
            dictMap.put(c, !dictMap.containsKey(c));
        }

        /**
         * 解决第1个元素:
         * 根据字母顺序, 第一次出现, 无论在何时, 都是第一次.
         */
        for (int index = 0; index < sc.length; index++) {
            if (dictMap.get(sc[index])) {
                return index;
            }
        }
        return -1;
    }
}
