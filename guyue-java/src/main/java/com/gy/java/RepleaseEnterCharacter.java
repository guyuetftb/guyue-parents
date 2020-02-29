package com.gy.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepleaseEnterCharacter {

    public static void main(String[] args) {
        String command = "a1 b1 c1 d1";
        String commandClean = command.replaceAll("1|2", command);
        System.out.println(command);
        System.out.println("-----------------");
        System.out.println(commandClean);
    }
    /*-----------------------------------

    笨方法：String s = "你要去除的字符串";

            1.去除空格：s = s.replace('\\s','');

            2.去除回车：s = s.replace('\n','');

    这样也可以把空格和回车去掉，其他也可以照这样做。

    注：\n 回车(\u000a)
    \t 水平制表符(\u0009)
    \s 空格(\u0008)
    \r 换行(\u000d)*/
}
