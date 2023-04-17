package com.gy.designpattern.observer.forjava;

/**
 * @ClassName NewPaper
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 17:23
 */
public class NewPaper extends java.util.Observable {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		// Java 中这句话不能少
		this.setChanged();

		// 推的方式
		this.notifyObservers(this.content);

		// 拉的方式
		// 默认Java 使用 "拉模型" 实现观察者模式
		// 拉模型的方式，在目标方法中，可以传参，也可以不传参
		this.notifyObservers();
	}
}
