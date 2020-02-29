package com.gy.designpattern.decorator;

/**
 * @ClassName Component
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 17:39
 */
public class ConcreteDecoratorB extends Decorator {

	public ConcreteDecoratorB(Component component) {
		super(component);
	}

	public void addedBehavior() {
	}

	public void operation() {
		// 调用父类的方法, 可以在调用前后执行一些附加操作
		super.operation();
		// 在调用后执行一些操作
		addedBehavior();
	}
}
