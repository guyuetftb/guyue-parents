package com.gy.designpattern.abstractfactory.params;

import com.gy.designpattern.abstractfactory.normal.CPUApi;
import com.gy.designpattern.abstractfactory.normal.MainboardApi;

/**
 * @ClassName ComputerEngineer
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 21:56
 */
public class ComputerEngineer {

	private CPUApi cpu = null;
	private MainboardApi mainboard = null;
	private MemoryApi memory = null;

	public void makeComputer(AbstractFactory factory) {
		// 创建 CPU, Mainboard
		prepareResource(factory);
	}

	private void prepareResource(AbstractFactory factory) {
		// 准备材料
		cpu = (CPUApi) factory.create(1);
		mainboard = (MainboardApi) factory.create(2);
		memory = (MemoryApi) factory.create(3);
	}
}
