package com.gy.java.java8;

/**
 * @ClassName DoubleColonTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-19 15:35
 */
public class DoubleColonTest {

	public static void main(String[] args) {

		// 访问静态方法必须用Class-Name
		IDoubleColon<String, String> sayStaticMethod = Something::sayStatic;
		System.out.println(sayStaticMethod.doubleColonTest("1-Class"));

		// 访问Static-Method, 必须用Class-Name
		IDoubleColon<String, String> sayStatic101Method = Something::sayStatic;
		System.out.println("static-method-101 -->" + sayStatic101Method);
		String doubleColonStatic101 = sayStatic101Method.doubleColonTest("101-Class");
		System.out.println("static-method-101-result -->" + doubleColonStatic101);

		Something something101 = new Something(101);
		IDoubleColon<Integer, String> sayInstance101Method = something101::sayInstance;
		System.out.println("instance-method-101 -->" + sayInstance101Method);
		System.out.println("instance-method-101-result -->" + sayInstance101Method.doubleColonTest(100));

		// 访问Static-Method, 必须用Class-Name
		IDoubleColon<String, String> sayStatic100Method = Something::sayStatic;
		System.out.println("static-method-100 -->" + sayStatic100Method);
		String doubleColonStatic100 = sayStatic100Method.doubleColonTest("100-Class");
		System.out.println("static-method-100-result -->" + doubleColonStatic100);

		Something something100 = new Something(100);
		IDoubleColon<Integer, String> sayInstance100Method = something100::sayInstance;
		System.out.println("instance-method-100 -->" + sayInstance100Method);
		System.out.println("instance-method-100 -->" + sayInstance100Method.doubleColonTest(100));

		IDoubleColon<Integer, Something> constructorMethod = Something::new;
		Something something2 = constructorMethod.doubleColonTest(2);
		System.out.println(something2.sayInstance(2));
	}
}
