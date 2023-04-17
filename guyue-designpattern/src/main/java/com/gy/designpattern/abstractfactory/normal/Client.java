package com.gy.designpattern.abstractfactory.normal;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 21:58
 */
public class Client {

	public static void main(String[] args) {
		// 构造一个将机工程师
		ComputerEngineer engineer = new ComputerEngineer();

		// 先组装一个 Intel 系列的电脑
		engineer.makeComputer(new IntelFactory());

		// 再组装一个 AMD 系列的电脑
		engineer.makeComputer(new AMDFactory());
	}
}
