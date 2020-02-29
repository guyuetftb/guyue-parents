package com.gy.designpattern.bridge;

/**
 * @ClassName Implementor
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:26
 */
public class RefinedAbstraction extends Abstraction {

	public RefinedAbstraction(Implementor impl) {
		super(impl);
	}

	public void otherOperation() {
		// 实现一定的功能, 可能会使用具体实现部分的方法
		// 本方法最大可能会使用 Abstraction 中的方法
		// 通过组合 Abstraction 中的方法来实现更多的功能
	}
}
