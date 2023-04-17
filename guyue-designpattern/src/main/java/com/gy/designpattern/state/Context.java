package com.gy.designpattern.state;

/**
 * @ClassName Context
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:50
 */
public class Context {

	private State state;

	public Context(State state) {
		this.state = state;
	}

	public void request(String someParameter) {
		this.state.handle(someParameter);
	}
}
