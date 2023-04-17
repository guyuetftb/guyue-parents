package com.gy.designpattern.builder;

/**
 * @ClassName Builder
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 17:37
 */
public class TxtBuilder implements Builder {
	private StringBuilder result = new StringBuilder();

	public void builderHeader(String header) {
		result.append(header);
		System.out.println(" TxtBuilder header");
	}

	public void builderPart(String body) {
		result.append(body);
		System.out.println(" TxtBuilder part");
	}

	public void builderFooter(String footer) {
		System.out.println(" TxtBuilder footer");
		result.append(footer);
	}

	public String getResult(){
		return result.toString();
	}

}
