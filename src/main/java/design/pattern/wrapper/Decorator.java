package design.pattern.wrapper;

/**
 * 装饰器类,将实现类进行装饰包装
 * 
 * @author chenkw
 *
 */
public abstract class Decorator implements Component {
	private Component component;

	public Decorator(Component component) {
		this.component = component;
	}

	
	/*
	 * 该方法可以由子类覆盖,只需去掉final
	 * 如果知道装饰的策略,书写final在提供对象的抽象方法就可以避免用户覆盖
	 */
	@Override
	public void sampleOperation() {
		before();
		this.component.sampleOperation();
		after();
	}

	/**
	 * 装饰方法1
	 */
	public abstract void before();
	/**
	 * 装饰方法2
	 */
	public abstract void after();

}
