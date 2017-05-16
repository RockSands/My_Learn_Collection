package design.pattern.factory.abs;

public class SendMailFactory implements SenderFactory {

	@Override
	public Sender produce() {
		return new MailSender();  
	}

}
