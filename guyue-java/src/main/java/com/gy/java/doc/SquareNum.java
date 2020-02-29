package com.gy.java.doc;

import java.io.*;

public class SquareNum {

    /**
     * <pre>
     *     计算平方
     * </pre>
     *
     * @param num 计算平的因子
     *
     * @return double 参数平方
     */
    public double square(double num) {
        return num * num;
    }

    /**
     * Description:
     * <pre>
     *     新平方 方法
     * </pre>
     *
     * @param num1 计算平的因子
     *
     * @return long 参数平方
     */
    public long square(int num1) {
        return num1 * num1;
    }

    /**
     * Description:
     * <pre>
     *     这是获得参数的方法
     * </pre>
     *
     * @return double 获取输入的数
     *
     * @throws IOException IO异常
     */
    public double getNumber() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader inData = new BufferedReader(isr);
        String str;
        str = inData.readLine();
        return (new Double(str)).doubleValue();
    }

    /**
     * <pre>
     *     主函数
     * </pre>
     *
     * @param args 程序启动参数
     *
     * @throws IOException IO异常
     */
    public static void main(String args[]) throws IOException {
        SquareNum ob = new SquareNum();
        double val;
        System.out.println("Enter value to be squared: ");
        val = ob.getNumber();
        val = ob.square(val);
        System.out.println("Squared value is " + val);
    }
}
