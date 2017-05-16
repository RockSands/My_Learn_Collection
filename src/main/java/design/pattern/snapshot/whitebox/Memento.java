package design.pattern.snapshot.whitebox;

/**
 * 备份记录对象
 * @author chenkw
 *
 */
public class Memento {
	private String state;

	public Memento(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
