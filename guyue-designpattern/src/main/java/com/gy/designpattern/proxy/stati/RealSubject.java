package com.gy.designpattern.proxy.stati;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:07
 */
public class RealSubject implements Subject {

	public void request() {
		System.out.println(" I am RealSubject, real do something.");
	}

}
