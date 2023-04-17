package com.gy.designpattern.proxy.stati;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:07
 */
public class Client {

	public static void main(String[] args) {
		RealSubject realSubject = new RealSubject();
		StatiProxy statiProxy = new StatiProxy(realSubject);
		statiProxy.request();
	}
}
