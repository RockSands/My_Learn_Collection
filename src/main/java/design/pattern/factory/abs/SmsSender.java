package design.pattern.factory.abs;

public class SmsSender implements Sender {

	@Override
	public void send() {
		System.out.println("this is sms sender!");  
	}

}
