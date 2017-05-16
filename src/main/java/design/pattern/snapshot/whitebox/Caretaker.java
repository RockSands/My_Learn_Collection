package design.pattern.snapshot.whitebox;

/**
 * 备份的操作者
 * @author chenkw
 *
 */
public class Caretaker {
	private Memento memento;

	/**
	 * 备忘录的取值方法
	 */
	public Memento retrieveMemento() {
		return this.memento;
	}

	/**
	 * 备忘录的赋值方法
	 */
	public void saveMemento(Memento memento) {
		this.memento = memento;
	}

}
