package com.gy.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName RecursionDigitToChar
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-29 18:31
 */
public class RecursionDigitToChar {

	public static ArrayList<String> res = new ArrayList<String>();

	public static String[] letterMap = new String[]{
		" ",    //0
		"",        //1
		"abc",    //2
		"def",    //3
		"ghi",    //4
		"jkl",    //5
		"mno",    //6
		"pqrs",    //7
		"tuv",    //8
		"wxyz"    //9
	};


	// TODO s中保存了此时从 digits[0....index-1] 翻译得到的一个字母字符串
	//		寻找和 digits[index] 匹配的字母, 获得 digits[0....index]翻译得到的解.
	public static void findCombination(String digits, int digitIndex, String s) {

		System.out.println("digitIndex = " + digitIndex + ", s = " + s);

		// TODO 递归调用时, digits 的索引已经到 末尾了, 退出.
		if (digitIndex == digits.length()) {
			System.out.println("	get s = " + s + ", return");
			res.add(s);
			return;
		}
		char indexChar = digits.charAt(digitIndex);

		// TODO 从 letterMap 中寻找数字代表哪些字母.
		String letters = letterMap[indexChar - '0'];

		// TODO 实现思路是这样的
		//		1. s 是 digitIndex 之前的字符串, 比如 digitIndex 之前的字符串是2, 那s 就代表2所指的那个字母.
		//		2. 用 s 所代表的这个字母, 不断的迭代后继的数字所代表的字母, 然后两者相加, 就是一种组合.
		for (int i = 0; i < letters.length(); i++) {
			// TODO digitIndex = 0;
			//		2=abc
			//			s=a
			//				a + 3 def
			//					ad
			//					ae
			//					af
			//			s=b
			//				b + 3 def
			//					bd
			//					be
			//					bf
			//			s=c
			//				c + 3 def
			//					cd
			//					ce
			//					cf
			System.out.println("	digits[" + digitIndex + "]=" + indexChar + ", use " + letters);
			findCombination(digits, digitIndex + 1, s + letters.charAt(i));
		}

		System.out.println("	digits[" + digitIndex + "]=" + indexChar + " complete, return ");
		return;

	}

	public static void main(String[] args) {
		findCombination("234", 0, "");
		res.forEach(new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		});
	}

}
