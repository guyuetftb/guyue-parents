package com.gy.designpattern.observer.forjava;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 17:46
 */
public class Client {

	public static void main(String[] args) {
		NewPaper subject = new NewPaper();

		Reader r1 = new Reader();
		r1.setName("a1");

		Reader r2 = new Reader();
		r2.setName("a2");

		subject.addObserver(r1);
		subject.addObserver(r2);

		subject.setContent("新内容");

	}
}
