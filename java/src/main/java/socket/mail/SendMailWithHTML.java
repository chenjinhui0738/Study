package socket.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送html的邮件
 */
public class SendMailWithHTML {

    public static void main(String[] args) throws Exception {
        final String smtp = "smtp.qq.com";
        final String username = "1228793447@qq.com";
        final String password = "kiiwrnrcodvoieeb";
        final String from = "1228793447@qq.com";
        final String to = "1228793447@qq.com";
        SendMail sender = new SendMail(smtp, username, password);
        Session session = sender.createTLSSession();
        Message message = createHtmlMessage(session, from, to, "Java HTML邮件",
                "<h1>Hello</h1><p>这是一封<u>javamail</u>HTML邮件！</p>");
        Transport.send(message);
    }

    static Message createHtmlMessage(Session session, String from, String to, String subject, String body)
            throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject, "UTF-8");
        message.setText(body, "UTF-8", "html");
        return message;
    }

}
