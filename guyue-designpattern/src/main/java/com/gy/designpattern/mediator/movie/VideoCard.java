package com.gy.designpattern.mediator.movie;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public class VideoCard extends Colleague {


	public VideoCard(Mediator mediator) {
		super(mediator);
	}

	public void showData(String data) {
		System.out.println("看的数据是:" + data);
	}
}
