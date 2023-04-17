package com.gy.designpattern.bridge.message;

/**
 * @ClassName AbstractMessage
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 23:49
 */
public class UrgencyMessage extends AbstractMessage {

	public UrgencyMessage(MessageImplementor implementor) {
		super(implementor);
	}

	public void sendMessage(String message, String toUser) {
		String newMessage = "加急:" + message;
		super.sendMessage(newMessage, toUser);
		watch(message);
	}

	/**
	 * 扩展自己的新功能, 监控某消息的处理过程
	 * @param messageId
	 * @return
	 */
	public Object watch(String messageId){
		// 获取相应的数据，组织成监控的数据对象, 然后返回
		System.out.println(" 加急消息, 观察对方回复中....");
		return null;
	}
}
