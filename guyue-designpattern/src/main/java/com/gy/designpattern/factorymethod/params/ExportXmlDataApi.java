package com.gy.designpattern.factorymethod.params;

/**
 * @ClassName ExportDataApi
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 13:36
 */
public class ExportXmlDataApi implements ExportDataApi{

	public boolean export(String data){
		System.out.println(" 导出 Xml data");
		return true;
	}
}
