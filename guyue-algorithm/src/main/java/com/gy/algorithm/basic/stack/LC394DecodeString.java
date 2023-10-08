package com.gy.algorithm.basic.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class LC394DecodeString {

    public static void main(String[] args) {
        String str = "100[leetcode]";
        LC394DecodeString lc394DecodeString = new LC394DecodeString();
        String s = lc394DecodeString.decodeString(str);
        System.out.println(s);
    }

    /**
     * 两个栈，一个存放数字，一个存放字符串
     * <p>
     * 遍历有四种情况
     * 1. 如果是数字，将数字转换成整型数字待处理
     * 2. 如果是字符，将字符添加到临时字符串待处理
     * 3. 如果是'['，将当前数字，临时字符串添加到 数字栈, 字符串栈中
     * 4. 如果是']'，将数字，字符从各自的栈中取出，然后拼接成新的临时字符串
     */
    public String decodeString(String s) {

        Deque<Integer> stackDigit = new ArrayDeque<>();
        Deque<StringBuilder> stackString = new ArrayDeque<>();
        StringBuilder curString = new StringBuilder();
        Integer curNum = 0;

        // input = "3[a2[c]]";
        // input = "3[a]2[bc]";
        int idx = 0;
        while (idx < s.length()) {
            char ch = s.charAt(idx++);
            if (ch == '[') {
                // 将数字, 临时字符串添加到末尾
                stackDigit.offerLast(curNum);
                // 当遇到 [时, 就把 curString中的内容, 临时保存到 栈中, 遇到 ] 再弹出来, 与[]中的内容 相连
                stackString.offerLast(curString);

                // 每次遇到 '[' 都 重置 临时变量
                // curString只保存 [ 后面的字符串, []中的字符串就是要重复的字符串
                curNum = 0;
                curString = new StringBuilder();
            } else if (ch == ']') {
                // 弹出临时子串
                StringBuilder previousStr = stackString.pollLast();
                // 弹出需要 重复的次数
                int cnt = stackDigit.pollLast();

                // 在之前字符串后面,加上 [] 中需要重复的字符串
                for (int i = 0; i < cnt; i++) {
                    previousStr.append(curString);
                }
                //  不断的对 curString(当前字符串) 进行操作, 每次弹栈后, curString就是最新的字符串
                curString = previousStr;
            } else if (ch >= '0' && ch <= '9') {
                //遇到数字, 转为真正的数字,这里不能用 Integer.valueOf, 因为是字符, 只有一位, 超1位, 出错
                // curNum = Integer.valueOf(ch + "");
                curNum = curNum * 10 + ch - '0';
            } else {
                //收集 临时子串
                curString.append(ch);
            }
        }
        return curString.toString();
    }
}
