package com.gy.designpattern.singleton;

/**
 * @ClassName Singleton1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 09:12
 */
public class Singleton1 {

	private final static Singleton1 singleton = new Singleton1();

	private Singleton1() {
	}

	public static Singleton1 getSingleton() {
		return singleton;
	}
}
