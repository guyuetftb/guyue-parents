package com.gy.designpattern.template.callback;

/**
 * @ClassName CallbackTemplate
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 23:27
 */
public class CallbackTemplate {
	// 注册本类已经不再是接口了, 而变成了具体的类

	/**
	 * userId 是需要给回调函数传入的参数
	 * callback 是具体的回调对象
	 * @param userId
	 * @param callback
	 * @return
	 */
	public final boolean doSomething(String userId, DoCallback callback) {
		callback.setup(userId);
		callback.realDo(userId);
		callback.cleanup(userId);
		return false;
	}
}
