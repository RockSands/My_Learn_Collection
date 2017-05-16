package design.pattern.visitor;

public class NodeA extends Node {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * NodeA特有的方法
	 */
	public String operationA() {
		return "NodeA";
	}

}
