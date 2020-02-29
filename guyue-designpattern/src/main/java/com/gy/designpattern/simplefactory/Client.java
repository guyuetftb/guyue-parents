package com.gy.designpattern.simplefactory;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-15 17:11
 */
public class Client {

	public static void main(String[] args) {
		Api api = SimpleFactory.createApi(1);
		api.doSomeThing();
	}

}
