package com.gy.designpattern.abstractfactory.normal;

/**
 * @ClassName ConcreteFactoryA
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 18:23
 */
public class AMDFactory implements AbstractFactory {

	@Override
	public CPUApi createCPU() {
		return new AMDCpuApi();
	}

	@Override
	public MainboardApi createMainboard() {
		return new MSIMainboardApi();
	}
}
