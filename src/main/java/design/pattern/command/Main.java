package design.pattern.command;

public class Main {

	public static void main(String[] args) {
		Receiver receiver = new Receiver();
        ICommand command = new ConcereteCommand(receiver);
        Invoker invoker = new Invoker();
        invoker.SetCommand(command);
        invoker.ExecuteCommand();
	}

}
