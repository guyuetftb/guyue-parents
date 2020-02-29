package com.gy.designpattern.decorator;

/**
 * @ClassName Component
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 17:39
 */
public class ConcreteDecoratorA extends Decorator {

	private String addedState;

	public ConcreteDecoratorA(Component component) {
		super(component);
	}

	public String getAddedState() {
		return addedState;
	}

	public void setAddedState(String addedState) {
		this.addedState = addedState;
	}

	public void operation() {
		// 调用父类的方法, 可以在调用前后执行一些附加操作
		super.operation();
	}
}
