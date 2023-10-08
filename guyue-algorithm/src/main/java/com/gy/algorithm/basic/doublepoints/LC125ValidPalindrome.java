package com.gy.algorithm.basic.doublepoints;

public class LC125ValidPalindrome {

    public static void main(String[] args) {

    }

    public boolean isPalindrome(String s) {
        // 左侧指针
        int left = 0;

        // 右侧指针
        int right = s.length() - 1;

        while (left < right) {
            // 左侧字符不是 数字，或 数字
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // 右侧字符不是 数字，或 数字
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }

                // 指针右移
                left++;
                // 指针左移
                right--;
            }
        }

        return true;
    }

}
