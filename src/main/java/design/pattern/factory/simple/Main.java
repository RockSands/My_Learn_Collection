package design.pattern.factory.simple;

/**
 * 简单工厂
 * @author chenkw
 *
 */
public class Main {
	public static void main(String[] args) {
		/*
		 * 使用通用工厂方法
		 */
		SendFactory factory = new SendFactory();
		Sender mailsender = factory.produce(SendFactory.MAIL_SENDER);
		mailsender.send();
		
		/*
		 * 直接使用静态方法
		 */
		SendFactory.produceSmsSender().send();
	}
}
