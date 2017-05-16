package design.pattern.wrapper;

/**
 * 被包装的内容
 * @author chenkw
 *
 */
public class ConcreteComponent implements Component {

	@Override
	public void sampleOperation() {
		System.out.println("我是内容类ConcreteComponent");

	}

}
