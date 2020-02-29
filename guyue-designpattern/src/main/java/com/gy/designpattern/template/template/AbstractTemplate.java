package com.gy.designpattern.template.template;

/**
 * @ClassName AbstractClass
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 20:15
 */
public abstract class AbstractTemplate {

	public abstract void doPrimitiveOperation1();

	public abstract void doPrimitiveOperation2();

	public abstract Object createOneObject();


	// final 声名，方法不能被子类覆盖
	public final void templateMethod() {
		// 钩子方法
		hookSetup();

		operationPrivate();

		operationProtected();

		// 原语方法
		doPrimitiveOperation1();
		doPrimitiveOperation2();

		// 钩子方法
		hookCleanup();
	}

	/**
	 * 固定实现, 子类不需要访问
	 */
	private void operationPrivate() {

	}

	/**
	 * 固定实现2, 可以被子类覆盖, 也可以实现为 final 的。
	 */
	protected void operationProtected() {

	}

	/**
	 * 子类的公共功能, 不是算法的具体步骤
	 */
	protected void operationCommon(){

	}

	/**
	 * Hook
	 */
	protected void hookCleanup() {
		System.out.println(" AbstractTemplate hookCleanup() ");
	}

	protected void hookSetup() {
		System.out.println(" AbstractTemplate hookSetup() ");
	}
}
