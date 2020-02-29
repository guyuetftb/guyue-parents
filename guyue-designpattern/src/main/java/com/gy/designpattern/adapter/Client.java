package com.gy.designpattern.adapter;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 08:08
 */
public class Client {

	public static void main(String[] args) {
		Adaptee adaptee = new Adaptee();
		Target target = new Adapter(adaptee);
		target.targetAction();
	}
}
