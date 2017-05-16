package design.pattern.factory.abs;

public class SendSmsFactory implements SenderFactory {

	@Override
	public Sender produce() {
		return new SmsSender();
	}

}
