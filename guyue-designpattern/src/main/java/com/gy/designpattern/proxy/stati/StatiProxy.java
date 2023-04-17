package com.gy.designpattern.proxy.stati;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:07
 */
public class StatiProxy implements Subject {

	private RealSubject realSubject;

	public StatiProxy(RealSubject realSubject) {
		this.realSubject = realSubject;
	}

	public void request() {
		System.out.println(" I am Proxy, request forward.");
		this.realSubject.request();
	}

}
