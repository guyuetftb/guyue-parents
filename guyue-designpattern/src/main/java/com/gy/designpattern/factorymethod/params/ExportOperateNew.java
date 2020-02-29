package com.gy.designpattern.factorymethod.params;

/**
 * @ClassName ExportDataApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 13:36
 */
public class ExportOperateNew extends ExportOperate{

	public ExportDataApi factoryMethod(int type) {
		ExportDataApi api = null;
		if (type == 3) {
			api = new ExportXmlDataApi();
		} else {
			api = super.factoryMethod(type);
		}
		return api;
	}
}
