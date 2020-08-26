package com.gy.algorithm.basic;

import java.io.BufferedInputStream;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {

		byte[] buffer = new byte[1024];
		BufferedInputStream bis = new BufferedInputStream(System.in);

		try {
			while (true) {
				System.out.println(" 请输入参与计算的两个参数: ");
				int readSize = bis.read(buffer);
				if (readSize > 0) {
					String line = new String(buffer, 0, readSize - 1);
					if (line.trim().length() <= 0) {
						System.out.println(" 程序输入空值, 退出! ");
						break;
					}
					String[] nums = line.trim().split(" ");
					if (nums.length != 2) {
						System.out.println(" 程序输入参数不合法, 退出! ");
						break;
					}

					int one = Integer.valueOf(nums[0]);
					int two = Integer.valueOf(nums[1]);
					if (0 > one || one > 1000000) {
						System.out.println(" 程序 '参数1' 输入不合法, 退出");
						break;
					}
					if (0 > two || two > 1000000) {
						System.out.println(" 程序 '参数2' 输入不合法, 退出");
						break;
					}
					System.out.println(one + " + " + two + " = " + (one + two));
				} else {
					System.out.println(" 程序输入为空, 请重新输入!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
