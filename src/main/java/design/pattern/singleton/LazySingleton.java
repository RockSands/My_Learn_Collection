package design.pattern.singleton;

public class LazySingleton {
	//懒汉就是不预先New实例
	private static LazySingleton instance = null;

	/**
	 * 私有默认构造子
	 */
	private LazySingleton() {
	}

	/**
	 * 静态方法
	 * synchronized避免多线程下创建多个instance
	 */
	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}
