package com.gy.algorithm.basic.string;

/**
 * @ClassName StringReverseParenthesesTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-27 17:07
 */
public class StringReverseParenthesesTest {


	/**
	 * 示例 1： 输入：s = "(abcd)" 输出："dcba"
	 *
	 * 示例 2： 输入：s = "(u(love)i)" 输出："iloveu"
	 *
	 * 示例 3： 输入：s = "(ed(et(oc))el)" 输出："leetcode"
	 *
	 * 示例 4： 输入：s = "a(bcdefghijkl(mno)p)q" 输出："apmnolkjihgfedcbq"
	 */
	public String reverseParentheses(String s) {

		if (s.length() <= 1) {
			return s;
		}

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				int j = i;
				int bal = 0;
				for (; j < s.length(); j++) {
					if (s.charAt(j) == '(') {
						bal++;
					}
					if (s.charAt(j) == ')') {
						bal--;
					}
					if (bal == 0) {
						break;
					}
				}
				StringBuilder tmp = new StringBuilder(reverseParentheses(s.substring(i + 1, j)));
				for (int k = tmp.length() - 1; k >= 0; k--) {
					str.append(tmp.charAt(k));
				}
				i = j;
			} else {
				str.append(s.charAt(i));
			}
		}

		return str.toString();
	}
}

