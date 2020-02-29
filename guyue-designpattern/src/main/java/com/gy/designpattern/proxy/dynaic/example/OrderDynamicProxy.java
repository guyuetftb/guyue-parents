package com.gy.designpattern.proxy.dynaic.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName OrderApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:39
 */
public class OrderDynamicProxy implements InvocationHandler {

	private OrderApi order;

	public void setProductName(String user, String name) {
		if (user.equals(order.getOrderUser())) {
			this.order.setProductName(user, name);
		} else {
			System.out.println(" 用户不正确, 不能设置.");
		}
	}

	public String getOrderUser() {
		return order.getOrderUser();
	}

	public OrderApi getProxyInterface(Order order) {
		this.order = order;
		OrderApi orderApi = (OrderApi) Proxy.newProxyInstance(
			order.getClass().getClassLoader(),
			order.getClass().getInterfaces(),    // 目标对象
			this); // 代理对象
		return orderApi;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().startsWith("set")) {
			if (order.getOrderUser().equals(args[0])) {
				return method.invoke(order, args);
			} else {
				System.out.println(" 用户不正确, 不能设置.");
			}
		} else {
			return method.invoke(order, args);
		}
		return null;
	}
}
