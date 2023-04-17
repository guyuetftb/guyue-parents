package com.gy.designpattern.state;

/**
 * @ClassName State
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:48
 */
public class ConcreteStateA implements State {

	public void handle(String someParameter){
		System.out.println(" I am ConcreteStateA");
	}
}
