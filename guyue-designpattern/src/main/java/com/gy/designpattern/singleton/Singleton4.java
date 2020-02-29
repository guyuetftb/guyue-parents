package com.gy.designpattern.singleton;

/**
 * @ClassName Singleton1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 09:12
 */
public class Singleton4 {

	private static final class Singleton4Holder{
		private static final Singleton4 singleton4 = new Singleton4();
	}

	private static Singleton4 singleton = null;

	private Singleton4() {
	}

	public static Singleton4 getSingleton() {
		return Singleton4Holder.singleton4;
	}
}
