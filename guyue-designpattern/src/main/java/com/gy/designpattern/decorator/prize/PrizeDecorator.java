package com.gy.designpattern.decorator.prize;

/**
 * @ClassName Component
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 17:39
 */
public abstract class PrizeDecorator extends PrizeComponent {

	protected PrizeComponent prizeComponent;

	public PrizeDecorator(PrizeComponent prizeComponent) {
		this.prizeComponent = prizeComponent;
	}

	public double calcPrize(String user) {
		return prizeComponent.calcPrize(user);
	}
}
