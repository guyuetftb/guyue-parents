package com.gy.designpattern.decorator.prize;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 19:03
 */
public class Client {

	public static void main(String[] args) {

		// 先创建 基本奖金类, 这是被修饰的最原始对象
		PrizeComponent srcComponent = new ConcretePrizeComponent();

		// 被月奖金装饰一次
		PrizeDecorator monthDecorator = new MonthPrizeDecorator(srcComponent);

		// 被累计奖金装饰一次
		PrizeDecorator sumDecorator = new SumPrizeDecorator(monthDecorator);

		double zs = sumDecorator.calcPrize("张三");
		System.out.println("张三  奖金:" + zs);

		double ls = sumDecorator.calcPrize("李四");
		System.out.println("李四  奖金:" + ls);

		PrizeDecorator groupDecorator = new GroupPrizeDecorator(srcComponent);
		double ww = groupDecorator.calcPrize("王五");
		System.out.println("王五  奖金:" + ww);

	}

}
