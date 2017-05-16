package design.pattern.observer.java;

import java.util.Observable;
import java.util.Observer;

public class Watcher implements Observer {

	public Watcher(Observable o) {
		o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// arg是Observable的notifyObservers(Object arg)发放传递的参数
		System.out.println("状态发生改变：" + ((Watched) o).getData());
	}

}
