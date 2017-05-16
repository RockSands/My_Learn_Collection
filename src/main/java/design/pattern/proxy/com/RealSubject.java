package design.pattern.proxy.com;

public class RealSubject implements Subject {

	@Override
	public void doSomething() {
		System.out.println( "call doSomething()" );   
	}
}
