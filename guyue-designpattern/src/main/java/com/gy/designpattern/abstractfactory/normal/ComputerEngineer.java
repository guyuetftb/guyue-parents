package com.gy.designpattern.abstractfactory.normal;

/**
 * @ClassName ComputerEngineer
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 21:56
 */
public class ComputerEngineer {

	private CPUApi cpu = null;
	private MainboardApi mainboard = null;

	public void makeComputer(AbstractFactory factory) {
		// 创建 CPU, Mainboard
		prepareResource(factory);
	}

	private void prepareResource(AbstractFactory factory) {
		// 准备材料
		cpu = factory.createCPU();
		mainboard = factory.createMainboard();
	}
}
