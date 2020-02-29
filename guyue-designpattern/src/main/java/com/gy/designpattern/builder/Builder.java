package com.gy.designpattern.builder;

/**
 * @ClassName Builder
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 17:37
 */
public interface Builder {

	public void builderHeader(String header);

	public void builderPart(String body);

	public void builderFooter(String footer);

}
