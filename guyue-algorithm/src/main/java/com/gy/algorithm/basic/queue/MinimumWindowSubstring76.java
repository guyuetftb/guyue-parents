package com.gy.algorithm.basic.queue;

public class MinimumWindowSubstring76 {

    public static void main(String[] args) {

    }

    public String minWindow(String s, String t) {

        // 使用map数组, 模拟128个ascii码值,
        // 统计字符串 t  中每个元素出现的频次
        int[] map = new int[128];
        for (int index = 0; index < t.length(); index++) {
            map[t.charAt(index)]++;
        }

        // 窗口长度初始值为: s字符串长度+1
        int windowLength = s.length() + 1;

        //窗口左侧
        int windowLeft = 0;
        //窗口右侧
        int windowRight = 0;
        //需要查找的字符串个数
        int seekCharCount = t.length();
        int start = 0;

        // 循环退出条件, 窗口右边 超出 字符串长度
        while (windowRight < s.length()) {
            // windowRight 初始值为0,  windowRight 不断向右移动
            char currentChar = s.charAt(windowRight);

            //  如果当前char是要找的char，那 map[char] 的值是大于0的.
            //  seekCharCount 减1, 说明已经找到了一个 需要找的『字符』
            if (map[currentChar] > 0) {
                seekCharCount--;
            }

            // 说明遇到了 要找的字符
            // 对map中的其他元素 值减1
            map[currentChar]--;
            System.out.println(" currentChar = " + currentChar + ", map-c:" + map[currentChar]);

            // 如果此时 seekCharCount == 0 ，表明滑动窗口中包含了 t 中全部的字符
            // 此时，找到了一个符合条件的子串
            // 但想尝试一下，能否满足条件的情况下子串更短一些
            // 于是，去尝试把滑动窗口的左端向右移动一下
            // 可以移动的前提是，滑动窗口的左端元素抛弃后剩下的元素依旧满足条件
            // 意思就是实际上左端元素是多余的
            // 而如果这个元素对应的值在 map[] 数组中小于 0，说明它是一个多余元素
            // 反复的删除这些多余的元素
            while (seekCharCount == 0) {

                // 如果 当前的这个窗口值比之前维护的窗口值更小, 需要进行更新
                if (windowRight - windowLeft + 1 < windowLength) {
                    // 更新窗口的长度
                    windowLength = windowRight - windowLeft + 1;

                    // 更新窗口的起始位置
                    start = windowLeft;
                }

                // 窗口左侧 开始 向右移动
                // 删除操作需要执行以下三个步骤
                // 如果这个元素不是多余的元素，比如滑动窗口为 ADBC，t 为 ABC
                // 移除了 A，那么滑动窗口又需要去新增其它的元素了
                // 所以通过 map[s.charAt(left)] == 0 来判断它是否是多余的元素
                // 或者说：来判断，是不是 删除了一个 需要查找的字符,
                // 如果map[s.chartAt(windowLeft)] == 0, 则说明删除了一个需要查找的字符
                if (map[s.charAt(windowLeft)] == 0) {
                    seekCharCount++;
                }

                //char离开了滑动窗口, 在map中的值需要加1
                map[s.charAt(windowLeft)]++;

                // windowLeft 向右移动
                windowLeft++;
            }

            //  windowRight 向右移动
            windowRight++;
        }

        return windowLength == s.length() + 1 ? "" : s.substring(start, start + windowLength);
    }
}
