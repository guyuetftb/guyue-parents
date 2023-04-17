package com.gy.designpattern.builder;

/**
 * @ClassName Builder
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 17:37
 */
public class XmlBuilder implements Builder {
	private StringBuilder result = new StringBuilder();

	public void builderHeader(String header) {
		result.append(header);
		System.out.println(" XmlBuilder header");
	}

	public void builderPart(String body) {
		result.append(body);
		System.out.println(" XmlBuilder part");
	}

	public void builderFooter(String footer) {
		System.out.println(" XmlBuilder footer");
		result.append(footer);
	}

	public String getResult(){
		return result.toString();
	}
}
