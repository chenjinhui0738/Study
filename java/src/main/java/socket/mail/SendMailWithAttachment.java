package socket.mail;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 发送带付件的邮件
 */
public class SendMailWithAttachment {

	public static void main(String[] args) throws Exception {
		final String smtp = "smtp.qq.com";
		final String username = "1228793447@qq.com";
		final String password = "kiiwrnrcodvoieeb";
		final String from = "1228793447@qq.com";
		final String to = "1228793447@qq.com";
		SendMail sender = new SendMail(smtp, username, password);
		Session session = sender.createTLSSession();
		InputStream input2 = SendMailWithAttachment.class.getResourceAsStream("/javamail.jpg");

		try (InputStream input = SendMailWithAttachment.class.getResourceAsStream("/javamail.jpg")) {
			Message message = createMessageWithAttachment(session, from, to, "Hello Java邮件带附件",
					"<h1>Hello</h1><p>这是一封带附件的<u>javamail</u>邮件！</p>", "javamail.jpg", input);
			Transport.send(message);
		}
	}

	static Message createMessageWithAttachment(Session session, String from, String to, String subject, String body,
			String fileName, InputStream input) throws MessagingException, IOException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject, "UTF-8");
		Multipart multipart = new MimeMultipart();
		// 添加text:
		BodyPart textpart = new MimeBodyPart();
		textpart.setContent(body, "text/html;charset=utf-8");
		multipart.addBodyPart(textpart);
		// 添加image:
		BodyPart imagepart = new MimeBodyPart();
		imagepart.setFileName(fileName);
		imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "application/octet-stream")));
		multipart.addBodyPart(imagepart);
		message.setContent(multipart);
		return message;
	}

}
