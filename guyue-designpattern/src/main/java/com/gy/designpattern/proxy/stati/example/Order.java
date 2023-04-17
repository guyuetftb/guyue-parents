package com.gy.designpattern.proxy.stati.example;

/**
 * @ClassName OrderApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:39
 */
public class Order implements OrderApi {

	private String name;
	private String user;

	@Override
	public void setProductName(String user, String name) {
		this.user = user;
		this.name = name;
	}

	@Override
	public String getOrderUser() {
		return user;
	}
}
