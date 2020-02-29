package com.gy.designpattern.mediator.movie;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public class CPU extends Colleague {

	private String videoData;
	private String soundData;

	public CPU(Mediator mediator) {
		super(mediator);
	}

	public void executeData(String data) {
		// 光驱读取数据
		String[] aa = data.split(",");
		this.videoData = aa[0];
		this.soundData = aa[1];
		this.getMediator().changed(this);
	}

	public String getVideoData() {
		return videoData;
	}

	public String getSoundData() {
		return soundData;
	}
}
