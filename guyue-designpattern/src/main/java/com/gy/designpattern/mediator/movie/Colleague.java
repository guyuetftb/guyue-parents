package com.gy.designpattern.mediator.movie;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public abstract class Colleague {

	private Mediator mediator;

	public Colleague(Mediator mediator) {
		this.mediator = mediator;
	}

	public Mediator getMediator() {
		return mediator;
	}
}
