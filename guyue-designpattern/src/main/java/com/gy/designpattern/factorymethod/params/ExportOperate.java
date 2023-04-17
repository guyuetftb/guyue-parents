package com.gy.designpattern.factorymethod.params;

/**
 * @ClassName ExportDataApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 13:36
 */
public class ExportOperate {

	public boolean export(int type, String data) {
		ExportDataApi api = factoryMethod(type);
		System.out.println(" 导出 DB data");
		return true;
	}

	public ExportDataApi factoryMethod(int type) {
		ExportDataApi api = null;
		if (type == 1) {
			api = new ExportDBDataApi();
		} else {
			api = new ExportFileDataApi();
		}
		return api;
	}
}
