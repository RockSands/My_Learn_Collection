package design.pattern.observer.java;

import java.util.Observable;

/**
 * 被观察者Watched类
 * @author chenkw
 *
 */
public class Watched extends Observable {
	private String data = "";

	public String getData() {
		return data;
	}

	public void setData(String data) {
		if (!this.data.equals(data)) {
			this.data = data;
			setChanged();
		}
		notifyObservers();
		// 如果需要传递信息,使用下面的方式
		// super.notifyObservers(new Object());
	}

}
