package design.pattern.factory.abs;

public class Main {

	public static void main(String[] args) {
		SenderFactory provider = new SendMailFactory();  
        Sender sender = provider.produce();  
        sender.send();  
	}

}
