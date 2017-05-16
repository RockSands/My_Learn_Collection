package design.pattern.visitor;

public class NodeB extends Node {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * NodeB特有的方法
	 */
	public String operationB() {
		return "NodeB";
	}
}
