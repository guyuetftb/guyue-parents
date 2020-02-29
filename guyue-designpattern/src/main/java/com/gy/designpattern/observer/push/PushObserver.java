package com.gy.designpattern.observer.push;

import com.gy.designpattern.observer.Subject;

/**
 * @ClassName Subject
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 11:13
 */
public interface PushObserver {

	public void update(String content);
}
