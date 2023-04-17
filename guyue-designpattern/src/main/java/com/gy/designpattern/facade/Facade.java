package com.gy.designpattern.facade;

/**
 * @ClassName Facade
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-15 20:00
 */
public class Facade {


	public void test() {
		AModuleApi apiA = new AModuleApiImpl();
		apiA.testA();
		BModuleApi apiB = new BModuleApiImpl();
		apiB.testB();
		CModuleApi apiC = new CModuleApiImpl();
		apiC.testC();
	}

}
