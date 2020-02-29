package com.gy.designpattern.bridge;

/**
 * @ClassName Implementor
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:26
 */
public abstract class Abstraction {

	protected Implementor impl;

	public Abstraction(Implementor impl) {
		this.impl = impl;
	}

	public void operation(){
		impl.operationImpl();
	}
}
