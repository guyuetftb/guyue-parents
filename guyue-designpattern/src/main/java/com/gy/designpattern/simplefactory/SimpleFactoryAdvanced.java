package com.gy.designpattern.simplefactory;

import java.util.Properties;

/**
 * @ClassName SimpleFactoryAdvanced
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-15 17:54
 */
public class SimpleFactoryAdvanced {


	public static Api createApi(int condition) {

		Properties property = new Properties();
		/**
		 * 读取配置地过程略....
		 */
		Api api = null;
		try {
			api = (Api) Class.forName(property.getProperty(String.valueOf(condition))).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return api;
	}
}
