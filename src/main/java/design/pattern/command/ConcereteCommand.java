package design.pattern.command;

/**
 * 具体命令类，实现具体命令。
 * @author chenkw
 *
 */
public class ConcereteCommand implements ICommand {

	//具体命令类包含有一个接收者，将这个接收者对象绑定于一个动作
    private Receiver receiver;

    public ConcereteCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }

    /* 
     * 说这个实现是“虚”的，因为它是通过调用接收者相应的操作来实现Execute的
     * (non-Javadoc)
     * @see design.pattern.command.ICommand#Execute()
     */
    @Override
    public void Execute()
    {
        receiver.Action();
    }
	
}
