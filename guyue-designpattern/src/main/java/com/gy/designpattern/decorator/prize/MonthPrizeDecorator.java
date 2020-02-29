package com.gy.designpattern.decorator.prize;

import com.gy.designpattern.decorator.TempDB;

/**
 * @ClassName Component
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 17:39
 */
public class MonthPrizeDecorator extends PrizeDecorator {

	public MonthPrizeDecorator(PrizeComponent prizeComponent) {
		super(prizeComponent);
	}

	public double calcPrize(String user) {
		// 调用之前的奖金, 可以是原始对象, 也可以是已经被装饰的对象
		double money = super.calcPrize(user);

		// 奖金
		double prize = TempDB.mapMonthSaleMoney.get(user) * 0.03;
		return money + prize;
	}
}
