package com.gy.designpattern.proxy.dynaic.example;

/**
 * @ClassName OrderApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:39
 */
public class Order implements OrderApi {

	private String name;
	private String user;

	public Order(String user, String name) {
		this.name = name;
		this.user = user;
	}

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
