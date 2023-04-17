package com.gy.designpattern.bridge.message;

/**
 * @ClassName AbstractMessage
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:49
 */
public abstract class AbstractMessage {

	private MessageImplementor implementor;

	public AbstractMessage(MessageImplementor implementor) {
		this.implementor = implementor;
	}

	public void sendMessage(String message, String toUser) {
		implementor.send(message, toUser);
	}
}
