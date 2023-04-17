package com.gy.designpattern.factorymethod.normal;

/**
 * @ClassName ConcreteProduct
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 11:10
 */
public class ConcreteProduct implements Product {

	@Override
	public void say() {
		System.out.println(" I am Concrete Product");
	}
}
