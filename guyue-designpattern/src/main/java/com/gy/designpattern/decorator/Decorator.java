package com.gy.designpattern.decorator;

/**
 * @ClassName Component
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 17:39
 */
public abstract class Decorator extends Component {

	protected Component component;

	public Decorator(Component component) {
		this.component = component;
	}

	public void operation() {
		// 转发请求给组件对象, 可以在转发之前进行一些操作
		component.operation();
	}
}
