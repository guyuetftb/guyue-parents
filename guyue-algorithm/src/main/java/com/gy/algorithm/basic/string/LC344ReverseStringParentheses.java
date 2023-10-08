package com.gy.algorithm.basic.string;

/**
 * 翻转"括号中的字符串"
 * 示例 1： 输入：s = "(abcd)" 输出："dcba"
 * <p>
 * 示例 2： 输入：s = "(u(love)i)" 输出："iloveu"
 * <p>
 * 示例 3： 输入：s = "(ed(et(oc))el)" 输出："leetcode"
 * <p>
 * 示例 4： 输入：s = "a(bcdefghijkl(mno)p)q" 输出："apmnolkjihgfedcbq"
 */
public class LC344ReverseStringParentheses {

    public static void main(String[] args) {
        String str = "a(ed(et(oc))el)b";
        LC344ReverseStringParentheses lc344ReverseStringParentheses = new LC344ReverseStringParentheses();
        String s = lc344ReverseStringParentheses.reverseParentheses(str);
        System.out.println(s);
    }


    public String reverseParentheses(String s) {

        // 1. 长度为1, 直接返回
        if (s.length() <= 1) {
            return s;
        }

        //
        StringBuilder resStr = new StringBuilder();
        for (int idxOut = 0; idxOut < s.length(); idxOut++) {

            // 0. 在括号中
            if (s.charAt(idxOut) == '(') {

                int idxIn = idxOut;
                int bal = 0;
                /**
                 * 这里是 匹配内部的 第一级 子串.
                 * 找到 完整的子串, 然后, 截取出子串, 递归调用 reverseParentheses 方法
                 */
                for (; idxIn < s.length(); idxIn++) {
                    if (s.charAt(idxIn) == '(') {
                        bal++;
                    }
                    if (s.charAt(idxIn) == ')') {
                        bal--;
                    }
                    if (bal == 0) {
                        break;
                    }
                }

                // 2. 截取到内部第一级子串, 递归调用 reverseParentheses 方法.
                String subStrResult = reverseParentheses(s.substring(idxOut + 1, idxIn));
                for (int k = subStrResult.length() - 1; k >= 0; k--) {
                    resStr.append(subStrResult.charAt(k));
                }

                idxOut = idxIn;
            } else {
                // 1. 不在括号中, 直接 加入到结果中
                resStr.append(s.charAt(idxOut));
            }
        }

        return resStr.toString();
    }
}

