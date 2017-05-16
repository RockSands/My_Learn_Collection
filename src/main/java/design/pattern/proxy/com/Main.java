package design.pattern.proxy.com;

import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {
		RealSubject real = new RealSubject();
		// 通过Proxy代理类进行代理
		Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),
				new Class[] { Subject.class }, new ProxyHandler(real));
		proxySubject.doSomething();
	}

}
