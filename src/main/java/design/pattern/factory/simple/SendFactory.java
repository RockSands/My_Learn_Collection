package design.pattern.factory.simple;

/**
 * 发送工厂
 * 
 * @author chenkw
 *
 */
public class SendFactory {
	public final static String MAIL_SENDER = "mail";
	public final static String SMS_SENDER = "sms";

	/**
	 * 常用简单工厂方法
	 * @param type
	 * @return
	 */
	public Sender produce(String type) {
		if (MAIL_SENDER.equals(type)) {
			return new MailSender();
		} else if (SMS_SENDER.equals(type)) {
			return new SmsSender();
		} else {
			System.out.println("请输入正确的类型!");
			return null;
		}
	}

	/**
	 * 可以使用静态方法直接返回常用的
	 */
	public static Sender produceMailSender() {
		return new MailSender();
	}

	public static Sender produceSmsSender() {
		return new SmsSender();
	}
}
