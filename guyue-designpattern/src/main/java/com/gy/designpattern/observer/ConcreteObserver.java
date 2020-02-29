package com.gy.designpattern.observer;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public class ConcreteObserver implements Observer {

	@Override
	public void update(Subject subject) {
		String subjectState = ((ConcreteSubject) subject).getSubjectState();
		System.out.println(" subjectState = " + subjectState);
	}
}
