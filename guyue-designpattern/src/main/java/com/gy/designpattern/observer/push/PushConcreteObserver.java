package com.gy.designpattern.observer.push;

import com.gy.designpattern.observer.ConcreteSubject;
import com.gy.designpattern.observer.Observer;
import com.gy.designpattern.observer.Subject;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public class PushConcreteObserver implements PushObserver {


	@Override
	public void update(String content) {
		System.out.println(" content = " + content);
	}
}
