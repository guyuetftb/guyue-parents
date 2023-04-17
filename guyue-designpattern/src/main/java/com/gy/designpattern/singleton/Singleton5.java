package com.gy.designpattern.singleton;

/**
 * @ClassName Singleton1
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 09:12
 */
public class Singleton5 {

	private Singleton5() {
	}

	private enum Singleton5Enum {
		SINGLETON_5_ENUM;
		private Singleton5 singleton;

		Singleton5Enum() {
			singleton = new Singleton5();
		}

		public Singleton5 getSingletong5() {
			return singleton;
		}
	}


	public static Singleton5 getSingleton() {
		return Singleton5Enum.SINGLETON_5_ENUM.singleton;
	}
}