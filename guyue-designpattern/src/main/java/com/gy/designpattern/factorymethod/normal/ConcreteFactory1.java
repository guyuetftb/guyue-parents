package com.gy.designpattern.factorymethod.normal;

/**
 * @ClassName ConcreteFactory1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 11:12
 */
public class ConcreteFactory1 extends AbstractFactory {

	@Override
	public Product createProduct() {
		return new ConcreteProduct();
	}
}
