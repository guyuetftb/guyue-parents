package com.gy.algorithm.basic.string;

/**
 * 假设输入字符串为["h","e","l","l","0"]
 * <p>
 * 1. 定义left和right分别指向首元素和尾元素
 * <p>
 * 2. 当left < right ，进行交换。
 * <p>
 * 3. 交换完毕，left++，right--
 * <p>
 * 4. 直至left == right
 */
public class LC344ReverseString {

    public static void main(String[] args) {

        method1();

        method2();
    }

    /**
     * 以字符串的方式实现
     */
    public static void method1() {
        String[] srcString = "hello".split("");
        for (int left = 0, right = srcString.length - 1; left != right; left++, right--) {
            String leftChar = srcString[left];
            srcString[left] = srcString[right];
            srcString[right] = leftChar;
        }

        for (String s : srcString) {
            System.out.print(s);
        }
        System.out.println();
    }

    /**
     * 以字节数组的方式实现.
     */
    public static void method2() {
        byte[] srcByteArr = "hello".getBytes();
        for (int left = 0, right = srcByteArr.length - 1; left != right; left++, right--) {
            byte leftChar = srcByteArr[left];
            srcByteArr[left] = srcByteArr[right];
            srcByteArr[right] = leftChar;
        }

        System.out.println(new String(srcByteArr));
    }
}

