package com.gy.designpattern.observer.push;

import com.gy.designpattern.observer.push.PushObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public class PushSubject {

	private List<PushObserver> observers = new ArrayList<PushObserver>();

	public void attach(PushObserver observer) {
		observers.add(observer);
	}

	public void detach(PushObserver observer) {
		observers.remove(observer);
	}

	protected void notifyObservers(String content) {
		for (PushObserver observer : observers) {
			observer.update(content);
		}
	}
}
