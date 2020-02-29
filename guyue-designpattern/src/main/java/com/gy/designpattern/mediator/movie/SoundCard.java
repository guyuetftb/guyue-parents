package com.gy.designpattern.mediator.movie;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public class SoundCard extends Colleague {

	public SoundCard(Mediator mediator) {
		super(mediator);
	}

	public void soundData(String data) {
		System.out.println("画外音" + data);
	}
}
