package com.gy.offer.string;

/**
 * @ClassName RegexString19
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-13 22:25
 */
public class RegexString19 {

	public static boolean match(String srcStr, String pattern) {
		if (srcStr == null || pattern == null) {
			return false;
		}

		return matchCore(new StringBuilder(srcStr), 0, new StringBuilder(pattern), 0);
	}

	public static boolean matchCore(StringBuilder strBud, int strIndex, StringBuilder patBud, int patIndex) {

		/**
		 * 字符串, 正则都匹配到最后一个字符了
		 */
		if (strBud.length() == strIndex && patBud.length() == patIndex) {
			return true;
		}

		/**
		 * 字符串还没有匹配完,
		 * 正则匹配完了,
		 * 不匹配, 退出
		 */
		if (strBud.length() != strIndex && patBud.length() == patIndex) {
			return false;
		}

		/**
		 * 字符串匹配完了,
		 * 正则还没有匹配完
		 */
		if (strBud.length() == strIndex && patBud.length() != patIndex) {
			if (patIndex + 1 < patBud.length() && patBud.charAt(patIndex + 1) == '*') {
				return matchCore(strBud, strIndex, patBud, patIndex + 2);
			} else {
				return false;
			}
		}

		/**
		 * 如果正则的第2个字符不是*或者已经只剩下一个字符了.
		 */
		if (patIndex == patBud.length() - 1 || patBud.charAt(patIndex + 1) != '*') {
			if (patBud.charAt(patIndex) == '.' || patBud.charAt(patIndex) == strBud.charAt(strIndex)) {
				return matchCore(strBud, strIndex + 1, patBud, patIndex + 1);
			} else {
				return false;
			}
		}
		/**
		 * 如果正则串的第2个字符是*
		 */
		else {
			// 正则字符是., 匹配任意字符.
			// 正则字符, 字符串相应位置上的字符相同
			if (patBud.charAt(patIndex) == '.' || patBud.charAt(patIndex) == strBud.charAt(strIndex)) {
				return matchCore(strBud, strIndex + 1, patBud, patIndex) ||
					matchCore(strBud, strIndex + 1, patBud, patIndex + 2) ||
					matchCore(strBud, strIndex, patBud, patIndex + 2);
			} else {

				return matchCore(strBud, strIndex, patBud, patIndex + 2);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(match("aaa", "a.a"));//true
		System.out.println(match("aaa", "ab*ac*a"));//true
		System.out.println(match("aaa", "aa.a"));//false
		System.out.println(match("aaa", "ab*a"));//false
	}

}
