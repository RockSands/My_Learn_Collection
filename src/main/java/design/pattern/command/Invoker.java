package design.pattern.command;

/**
 * 调度类，要求该命令执行这个请求
 * 
 * @author chenkw
 *
 */
public class Invoker {
	private ICommand command;

	/**
	 * 设置命令
	 * @param command
	 */
	public void SetCommand(ICommand command) {
		this.command = command;
	}

	/**
	 * 执行命令
	 */
	public void ExecuteCommand() {
		command.Execute();
	}
}
