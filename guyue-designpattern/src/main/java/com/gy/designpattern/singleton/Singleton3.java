package com.gy.designpattern.singleton;

/**
 * @ClassName Singleton1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 09:12
 */
public class Singleton3 {

	private static Singleton3 singleton = null;

	private Singleton3() {
	}

	public static Singleton3 getSingleton() {
		if (null == singleton) {
			synchronized (Singleton3.class) {
				if (null == singleton) {
					singleton = new Singleton3();
				}
			}
		}
		return singleton;
	}
}
