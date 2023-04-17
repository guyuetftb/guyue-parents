package com.gy.designpattern.abstractfactory.params;

import com.gy.designpattern.abstractfactory.params.AbstractFactory;
import com.gy.designpattern.abstractfactory.normal.CPUApi;
import com.gy.designpattern.abstractfactory.normal.GAMainboardApi;
import com.gy.designpattern.abstractfactory.normal.IntelCpuApi;
import com.gy.designpattern.abstractfactory.normal.MainboardApi;
import com.gy.designpattern.factorymethod.normal.Product;

/**
 * @ClassName ConcreteFactoryA
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 18:23
 */
public class IntelFactory implements AbstractFactory {

	public CPUApi createCPU() {
		return new IntelCpuApi();
	}

	public MainboardApi createMainboard() {
		return new GAMainboardApi();
	}


	public SamgMemoryApi createMemory() {
		return new SamgMemoryApi();
	}

	@Override
	public Object create(int productType) {
		if (productType == 1) {
			return createCPU();
		} else if (productType == 2) {
			return createMainboard();
		} else if (productType == 3) {
			return createMemory();
		}
		return null;
	}
}
