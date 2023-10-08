package com.gy.algorithm.basic.string;

import java.util.Locale;

public class LC18PalindromeString {

    public static void main(String[] args) {
        String str = "A man, a plan, a canal: Panama";
        LC18PalindromeString lc18PalindromeString = new LC18PalindromeString();
        boolean palindrome = lc18PalindromeString.isPalindrome(str);
        System.out.println(palindrome);
    }

    public boolean isPalindrome(String s) {
        s = s.toLowerCase(Locale.ROOT);
        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            char leftChar = s.charAt(left);
            if ((leftChar >= 48 && leftChar <= 57) || (leftChar >= 97 && leftChar <= 122)) {
            } else {
                left++;
                continue;
            }

            char rightChar = s.charAt(right);
            if ((rightChar >= 48 && rightChar <= 57) || (rightChar >= 97 && rightChar <= 122)) {
            } else {
                right--;
                continue;
            }

            if (leftChar != rightChar) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
