package com.gy.designpattern.mediator.deft;

/**
 * @ClassName Colleague
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-19 23:59
 */
public class ConcreteMediator implements Mediator {

	private ConcreteColleagueA colleagueA;
	private ConcreteColleagueB colleagueB;

	public void setColleagueA(ConcreteColleagueA colleagueA) {
		this.colleagueA = colleagueA;
	}

	public void setColleagueB(ConcreteColleagueB colleagueB) {
		this.colleagueB = colleagueB;
	}

	@Override
	public void changed(Colleague colleague) {
		// 某个同事类发生变化, 通常与其他同一交互
	}
}
