package design.pattern.wrapper;

public class Main {

	public static void main(String[] args) {
		Component component = new ConcreteDecoratorA(new ConcreteComponent());
		component.sampleOperation();
	}

}
