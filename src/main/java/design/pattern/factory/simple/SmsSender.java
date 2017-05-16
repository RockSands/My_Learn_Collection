package design.pattern.factory.simple;

/**
 * sms发送
 * @author chenkw
 *
 */
public class SmsSender implements Sender {

	@Override
	public void send() {
		System.out.println("this is sms sender!");
	}
}