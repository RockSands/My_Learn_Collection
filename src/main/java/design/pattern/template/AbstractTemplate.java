package design.pattern.template;

public abstract class AbstractTemplate {
	/**
	 * 模板方法
	 */
	public void templateMethod() {
		// 调用基本方法
		abstractMethod();
		hookMethod();
		concreteMethod();
	}

	/**
	 * 基本方法的声明（由子类实现）必须由子类置换
	 */
	protected abstract void abstractMethod();

	/**
	 * 基本方法(空方法)可以由子类置换
	 */
	protected void hookMethod() {
	}

	/**
	 * 基本方法（已经实现）子类不可以动
	 */
	private final void concreteMethod() {
		// 业务相关的代码
	}
}
