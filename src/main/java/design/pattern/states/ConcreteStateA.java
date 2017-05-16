package design.pattern.states;

/**
 * 具体状态类
 * @author chenkw
 */
public class ConcreteStateA implements State {

	@Override
	public void handle(String sampleParameter) {
		System.out.println("ConcreteStateA handle ：" + sampleParameter);  
	}

}
