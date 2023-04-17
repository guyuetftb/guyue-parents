package com.gy.java.java8;

/**
 * @ClassName Something
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-19 15:35
 */
public class Something {

	private int age;

	public Something(int a) {
		age = a;
	}

	public static String sayStatic(String className) {
		return " I am static 'saySomething' method in Something class. class_name = " + className;
	}

	public String sayInstance(int age) {
		return " I am static 'sayInstance' method. age = " + age;
	}
}
