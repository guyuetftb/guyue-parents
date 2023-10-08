package com.gy.algorithm.basic.string;

public class LC9PalindromeNumber {

    public static void main(String[] args) {
        int num = 12321;
        LC9PalindromeNumber lc9PalindromeNumber = new LC9PalindromeNumber();
        boolean palindrome = lc9PalindromeNumber.isPalindrome(num);
        System.out.println(palindrome);
    }

    /**
     * 思路
     * 1. 标签：数学
     * 2. 如果是负数则一定不是回文数，直接返回 false
     * 3. 如果是正数，则将其倒序数值计算出来，然后比较和原数值是否相等
     * 4. 如果是回文数则相等返回 true，如果不是则不相等 false
     * 比如 123 的倒序 321，不相等；121 的倒序 121，相等
     */
    public boolean isPalindrome(int srcNum) {
        if (srcNum < 0) {
            return false;
        }

        // srcNum = 121
        int cur = 0;
        int num = srcNum;
        while (num != 0) {
            // 获取整数: cur * 10
            // 获取余数: num % 10
            // 第1次: cur = 1
            // 第2次: cur = 1 * 10 + 12 % 10 = 2 -> 12
            // 第3次: cur = 12 * 10 + 1 % 10 = 1 -> 121
            cur = cur * 10 + num % 10;
            // num 取整
            num /= 10;
        }
        return cur == srcNum;
    }
}
