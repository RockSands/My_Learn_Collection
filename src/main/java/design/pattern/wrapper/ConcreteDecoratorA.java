package design.pattern.wrapper;

public class ConcreteDecoratorA extends Decorator {

	public ConcreteDecoratorA(Component component) {
		super(component);
	}

	@Override
	public void before() {
		System.out.println("===> ");
		
	}

	@Override
	public void after() {
		System.out.println(" <===");
	}


}
