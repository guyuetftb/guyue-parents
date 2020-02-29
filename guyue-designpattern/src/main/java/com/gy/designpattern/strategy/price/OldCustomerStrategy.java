package com.gy.designpattern.strategy.price;

/**
 * @ClassName Strategy
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-22 10:15
 */
public class OldCustomerStrategy implements PriceStrategy {

	public double calcPrice(double price) {
		System.out.println(" I am OldCustomerStrategy. ");
		return price * 0.5;
	}
}
