package design.pattern.factory.simple;

/**
 * 邮件发送
 * @author chenkw
 *
 */
public class MailSender implements Sender {  
    @Override  
    public void send() {  
        System.out.println("this is mailsender!");  
    }  
}  