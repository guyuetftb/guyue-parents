package com.gy.designpattern.template;

/**
 * @ClassName AbstractClass
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 20:15
 */
public class ConcreteClass extends AbstractClass {


	@Override
	public void doPrimitiveOperation1() {
		System.out.println(" ConcreteClass doPrimitiveOperation1()");
	}

	@Override
	public void doPrimitiveOperation2() {
		System.out.println(" ConcreteClass doPrimitiveOperation2()");
	}
}
