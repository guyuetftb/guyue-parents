package com.gy.designpattern.template.callback;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 23:30
 */
public class Client {


	public static void main(String[] args) {
		String userId = "李四";
		CallbackTemplate callbackTemplate = new CallbackTemplate();
		callbackTemplate.doSomething(userId, new DoCallback() {
			@Override
			public void setup(String userId) {
				System.out.println(" setup " + userId);
			}

			@Override
			public void realDo(String userId) {
				System.out.println(" realDo " + userId);
			}

			@Override
			public void cleanup(String userId) {
				System.out.println(" cleanup " + userId);
			}
		});
	}
}
