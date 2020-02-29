package com.gy.designpattern.simplefactory;

/**
 * @ClassName SimpleFactory
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-15 17:10
 */
public class SimpleFactory {

	public static Api createApi(int condition) {
		if (condition == 1) {
			return new ImplA();
		} else {
			return new ImplB();
		}
	}

}
