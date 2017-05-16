package design.pattern.mediator;

public class ConcreteMediator implements Mediator {
	// 持有并维护同事A
	private ConcreteColleagueA colleagueA;
	// 持有并维护同事B
	private ConcreteColleagueB colleagueB;

	public void setColleagueA(ConcreteColleagueA colleagueA) {
		this.colleagueA = colleagueA;
	}

	public void setColleagueB(ConcreteColleagueB colleagueB) {
		this.colleagueB = colleagueB;
	}

	@Override
	public void changed(Colleague c) {
		/**
		 * 某一个同事类发生了变化，通常需要与其他同事交互 具体协调相应的同事对象来实现协作行为
		 */
		if (c instanceof ConcreteColleagueA) {
			System.out.println("ConcreteColleagueA");
			excuteColleagueB();
		}
		if (c instanceof ConcreteColleagueB) {
			System.out.println("执行结束");
		}
	}

	private void excuteColleagueB(){
		colleagueB.operation();
	}
}
