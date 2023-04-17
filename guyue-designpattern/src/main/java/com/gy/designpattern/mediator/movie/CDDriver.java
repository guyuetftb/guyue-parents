package com.gy.designpattern.mediator.movie;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public class CDDriver extends Colleague {

	private String data;

	public CDDriver(Mediator mediator) {
		super(mediator);
	}

	public void readData() {
		// 光驱读取数据
		this.data = "Test Data";
		this.getMediator().changed(this);
	}

	public String getData() {
		return data;
	}
}
