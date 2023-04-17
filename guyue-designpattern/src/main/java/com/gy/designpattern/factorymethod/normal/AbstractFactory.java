package com.gy.designpattern.factorymethod.normal;

/**
 * @ClassName AbstractFactory
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 11:11
 */
public abstract class AbstractFactory {

	protected abstract Product createProduct();

	public void doSomething(int type) {
		Product product = createProduct();
		product.say();
	}
}
