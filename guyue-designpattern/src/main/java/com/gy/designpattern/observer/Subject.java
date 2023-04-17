package com.gy.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public class Subject {

	private List<Observer> observers = new ArrayList<Observer>();

	public void attach(Observer observer) {
		observers.add(observer);
	}

	public void detach(Observer observer) {
		observers.remove(observer);
	}

	protected void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
