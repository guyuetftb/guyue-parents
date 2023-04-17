package com.gy.designpattern.abstractfactory.normal;

/**
 * @ClassName AbstractFactory
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 18:22
 */
public interface AbstractFactory {

	public CPUApi createCPU();

	public MainboardApi createMainboard();
}
