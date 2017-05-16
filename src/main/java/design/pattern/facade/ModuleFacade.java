package design.pattern.facade;

public class ModuleFacade {
	private ModuleA a = new ModuleA();
	private ModuleB b = new ModuleB();
	private ModuleC c = new ModuleC();

	/**
	 * 下面这些是A、B、C模块对子系统外部提供的方法
	 */
	public void a1() {
		a.a1();
	}

	public void b1() {
		b.b1();
	}

	public void c1() {
		c.c1();
	}

}
