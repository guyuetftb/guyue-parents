package com.gy.designpattern.adapter;

/**
 * @ClassName Adapter
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-17 08:08
 */
public class Adapter implements Target{

	private Adaptee adaptee;

	public Adapter(){
		this(new Adaptee());
	}

	public Adapter(Adaptee adaptee){
		this.adaptee = adaptee;
	}


	@Override
	public void targetAction() {
		beforeDoSomething();
		adaptee.specifyAction();
		afterDoSomething();
	}

	private void beforeDoSomething(){
		System.out.println(" Adapter before do something. ");
	}

	private void afterDoSomething(){
		System.out.println(" Adapter after do something. ");
	}
}
