package com.gy.designpattern.builder;

/**
 * @ClassName Director
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 17:40
 */
public class Director {

	private Builder builder;

	public Director(Builder builder) {
		this.builder = builder;
	}

	public void constructProduct(String header, String part, String footer) {
		this.builder.builderHeader(header);
		this.builder.builderPart(part);
		this.builder.builderFooter(footer);
	}
}
