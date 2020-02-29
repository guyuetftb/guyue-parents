package com.gy.designpattern.abstractfactory.params;

import com.gy.designpattern.abstractfactory.normal.CPUApi;
import com.gy.designpattern.abstractfactory.normal.MainboardApi;

/**
 * @ClassName AbstractFactory
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-18 18:22
 */
public interface AbstractFactory {

	public Object create(int productType);
}
