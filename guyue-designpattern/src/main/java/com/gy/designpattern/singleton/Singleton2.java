package com.gy.designpattern.singleton;

/**
 * @ClassName Singleton1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 09:12
 */
public class Singleton2 {

	private static Singleton2 singleton = null;

	private Singleton2() {
	}

	public static synchronized Singleton2 getSingleton() {
		if(null == singleton){
			singleton = new Singleton2();
		}
		return singleton;
	}
}
