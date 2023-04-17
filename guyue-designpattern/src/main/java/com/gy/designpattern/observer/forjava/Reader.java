package com.gy.designpattern.observer.forjava;

import java.util.Observable;

/**
 * @ClassName Reader
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 17:43
 */
public class Reader implements java.util.Observer {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable o, Object obj) {

		System.out.println("推的方式: " + name + ", 收到了报纸, 推过来的内容是===" + obj);

		/**
		 * 默认Java 使用 "拉模型" 实现观察者模式
		 * 拉模型中，目标方法可以传参，也可以不传参
		 */
		System.out.println("拉的方式: " + name + ", 收到了报纸, 推过来的内容是===" + ((NewPaper) o).getContent());
	}
}
