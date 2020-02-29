package com.gy.designpattern.bridge.message;

/**
 * @ClassName MessageImplementor
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:46
 */
public interface MessageImplementor {

	public void send(String message, String toUser);
}
