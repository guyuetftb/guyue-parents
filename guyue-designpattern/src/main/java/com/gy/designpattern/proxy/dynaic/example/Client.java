package com.gy.designpattern.proxy.dynaic.example;

import com.gy.designpattern.proxy.stati.RealSubject;
import com.gy.designpattern.proxy.stati.StatiProxy;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 23:07
 */
public class Client {

	public static void main(String[] args) {
		Order order = new Order("李三","aaa");

		OrderDynamicProxy dynamicProxy = new OrderDynamicProxy();
		OrderApi orderApi = dynamicProxy.getProxyInterface(order);
		orderApi.setProductName("孔四","bbb");
		orderApi.setProductName("李三","aaa111");
	}
}
