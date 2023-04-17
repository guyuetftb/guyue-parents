package com.gy.designpattern.strategy.price;

/**
 * @ClassName Strategy
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-22 10:15
 */
public class PriceContext {

	private PriceStrategy priceStrategy;

	public PriceContext(PriceStrategy priceStrategy) {
		this.priceStrategy = priceStrategy;
	}

	public double calcPrice(double price) {
		return priceStrategy.calcPrice(price);
	}
}
