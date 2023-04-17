package com.gy.designpattern.observer.push;

import com.gy.designpattern.observer.Subject;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public class PushConcreteSubject extends PushSubject {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.notifyObservers(content);
	}
}
