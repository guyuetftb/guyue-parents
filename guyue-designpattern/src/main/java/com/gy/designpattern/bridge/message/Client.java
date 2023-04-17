package com.gy.designpattern.bridge.message;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-24 00:08
 */
public class Client {

	public static void main(String[] args) {
		MessageImplementor smsImplementor = new MessageSMS();
		AbstractMessage commonMessage = new CommonMessage(smsImplementor);
		commonMessage.sendMessage("你好", "李四");

		MessageImplementor emailImplementor = new MessageMail();
		AbstractMessage urgencyMessage = new UrgencyMessage(emailImplementor);
		urgencyMessage.sendMessage("速回邮件", "李四");
	}

}
