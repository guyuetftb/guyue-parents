package com.gy.designpattern.proxy.stati.example;

/**
 * @ClassName OrderApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:39
 */
public class OrderProxy implements OrderApi {

	private Order order;

	public void setProductName(String user, String name) {
		if (user.equals(order.getOrderUser())) {
			this.order.setProductName(user, name);
		} else {
			System.out.println(" 用户不正确, 不能设置.");
		}
	}

	@Override
	public String getOrderUser() {
		return order.getOrderUser();
	}
}
