package com.gy.designpattern.strategy.price;

/**
 * @ClassName Strategy
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-22 10:15
 */
public class NormalCustomerStrategy implements PriceStrategy {

	public double calcPrice(double price) {
		System.out.println(" I am NormalCustomerStrategy. ");
		return price;
	}
}
