package com.gy.designpattern.decorator;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TempDB
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 18:55
 */
public class TempDB {

	private TempDB() {

	}

	public static Map<String, Double> mapMonthSaleMoney = new HashMap<String, Double>();

	static {
		mapMonthSaleMoney.put("张三", 10000d);
		mapMonthSaleMoney.put("李四", 20000d);
		mapMonthSaleMoney.put("王五", 30000d);
	}
}
