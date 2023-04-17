package com.gy.designpattern.bridge.message;

/**
 * @ClassName AbstractMessage
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:49
 */
public class CommonMessage extends AbstractMessage {

	public CommonMessage(MessageImplementor implementor) {
		super(implementor);
	}

	public void sendMessage(String message, String toUser) {
		super.sendMessage(message, toUser);
	}
}
