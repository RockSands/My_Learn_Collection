package design.pattern.singleton;

/**
 * 饿汉单列
 * 
 * @author chenkw
 *
 */
public class EagerSingleton {
	//饿汉就是预先创建实例
	private static EagerSingleton instance = new EagerSingleton();

	/**
	 * 私有默认构造子
	 */
	private EagerSingleton() {
	}

	/**
	 * 静态工厂方法
	 */
	public static EagerSingleton getInstance() {
		return instance;
	}

}
