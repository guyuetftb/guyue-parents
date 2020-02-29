package com.gy.datastructure.stack;

import java.util.Stack;

/**
 * @ClassName BraceStack
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-05 10:29
 */
public class BraceStack {

	public static void main(String[] args) {
		System.out.println(new BraceStack().isValid("{[(])}"));
	}

	public boolean isValid(String str) {
		ArrayStack<Character> stack = new ArrayStack();
		for (int index = 0; index < str.length(); index++) {
			char c = str.charAt(index);
			if (c == '(' || c == '{' || c == '[') {
				stack.push(c);
			} else {
				if(stack.isEmpty()){
					return false;
				}

				char topChar = stack.pop();
				if (c == ')' && topChar != '(') {
					return false;
				} else if (c == ']' && topChar != '[') {
					return false;
				} else if (c == '{' && topChar != '{') {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

}
