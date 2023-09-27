package com.gy.algorithm.basic.string;

import java.util.HashMap;
import java.util.Map;

public class LC2351FirstLetterToAppearTwice {

    public static void main(String[] args) {
        String str1 = "abccbaacz";
        String str2 = "loveleetcode";
        LC2351FirstLetterToAppearTwice lc2351FirstLetterToAppearTwice = new LC2351FirstLetterToAppearTwice();
        char index1 = lc2351FirstLetterToAppearTwice.repeatedCharacter(str1);
        System.out.println(index1);

        char index2 = lc2351FirstLetterToAppearTwice.repeatedCharacter(str2);
        System.out.println(index2);
    }

    public char repeatedCharacter(String s) {
        char[] chars = s.toCharArray();
        Map<Character, Integer> dictMap = new HashMap();
        for (Character c : chars) {
            dictMap.put(c, dictMap.getOrDefault(c, 0) + 1);
        }

        for (Character c : chars) {
            if (2 == dictMap.get(c)) {
                return c;
            }
        }
        return '0';
    }
}
