package com.gy.designpattern.builder;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 17:42
 */
public class Client {

	public static void main(String[] args) {
		TxtBuilder txtBuilder = new TxtBuilder();
		Director directorTxt = new Director(txtBuilder);
		directorTxt.constructProduct("HEADER", "BODY", "FOOTER");
		txtBuilder.getResult();

		XmlBuilder xmlBuilder = new XmlBuilder();
		Director directorXml = new Director(xmlBuilder);
		directorXml.constructProduct("HEADER", "BODY", "FOOTER");
		xmlBuilder.getResult();

	}
}
