package com.gy.designpattern.strategy;

/**
 * @ClassName Strategy
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-22 10:15
 */
public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public void algorithmInterface() {
		strategy.algorithmInterface();
	}
}
