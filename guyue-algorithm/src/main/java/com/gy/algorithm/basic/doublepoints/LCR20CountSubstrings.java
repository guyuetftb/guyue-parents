package com.gy.algorithm.basic.doublepoints;


/**
 * 思路与算法:
 * <p>
 * 计算有多少个回文子串的最朴素方法就是枚举出所有的回文子串，而枚举出所有的回文字串又有两种思路，分别是：
 * 1. 枚举出所有的子串，然后再判断这些子串是否是回文；
 * 2. 枚举每一个可能的回文中心，然后用两个指针分别向左右两边拓展，当两个指针指向的元素相同的时候就拓展，否则停止拓展。
 * <p>
 * <p>
 * <p>
 * 本题解题:
 * 1. 遍历字符串，对每个字符，都看作回文的中心，向两端延申进行判断直到非回文。
 * 2. 回文的中心可能是一个字符，也可能是两个字符。
 * 3. 注意双指针可能越界。
 */
public class LCR20CountSubstrings {

    public static void main(String[] args) {
        String str = "aaa";
        LCR20CountSubstrings lcr20CountSubstrings = new LCR20CountSubstrings();
        int i = lcr20CountSubstrings.countSubstrings(str);
        System.out.println(i);
    }

    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;

        //字符串的每个字符都作为回文中心进行判断，中心是一个字符或两个字符
        for (int index = 0; index < s.length(); ++index) {
            // 判断以当前字符为中心的子串，是不是回文串.
            // 这种情况, 是奇数个字符串.
            count += countPalindrome(s, index, index);

            // 判断以当前字符的 下一个字符中心的子串，是不是回文串.
            // 这种情况, 是偶数个字符串.
            count += countPalindrome(s, index, index + 1);
        }
        return count;
    }

    //从字符串的第start位置向左，end位置向右，比较是否为回文并计数
    private int countPalindrome(String s, int startIndex, int endIndex) {
        int count = 0;
        while (startIndex >= 0 && endIndex < s.length() && s.charAt(startIndex) == s.charAt(endIndex)) {
            // 个数加1
            count++;
            // 开始位置减1. 因为传进来的字符串位置是中心位置, 所以向两边延伸, startIndex向左移
            startIndex--;
            // 开始位置减1. 因为传进来的字符串位置是中心位置, 所以向两边延伸, startIndex向右移
            endIndex++;
        }
        return count;
    }

}

