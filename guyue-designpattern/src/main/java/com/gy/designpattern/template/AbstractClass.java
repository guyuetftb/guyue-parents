package com.gy.designpattern.template;

/**
 * @ClassName AbstractClass
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 20:15
 */
public abstract class AbstractClass {

	public abstract void doPrimitiveOperation1();

	public abstract void doPrimitiveOperation2();

	protected void setup() {
		System.out.println(" AbstractClass setup() ");
	}

	public void templateMethod() {
		// 钩子方法
		setup();

		// 原语方法
		doPrimitiveOperation1();
		doPrimitiveOperation2();

		// 钩子方法
		cleanup();
	}

	protected void cleanup() {
		System.out.println(" AbstractClass cleanup() ");
	}
}
