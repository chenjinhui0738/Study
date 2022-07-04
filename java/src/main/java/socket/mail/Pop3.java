package socket.mail;

import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 邮件接受，使用pop3协议
 */
public class Pop3 {

    final String popHost;
    final String username;
    final String password;
    final boolean debug;

    public Pop3(String popHost, String username, String password) {
        this.popHost = popHost;
        this.username = username;
        this.password = password;
        this.debug = true;
    }

    public static void main(String[] args) throws Exception {
        Pop3 pop = new Pop3("smtp.qq.com", "1228793447@qq.com", "kiiwrnrcodvoieeb");
        Folder folder = null;
        Store store = null;
        try {
            store = pop.createSSLStore();
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            System.out.println("Total messages: " + folder.getMessageCount());
            System.out.println("New messages: " + folder.getNewMessageCount());
            System.out.println("Unread messages: " + folder.getUnreadMessageCount());
            System.out.println("Deleted messages: " + folder.getDeletedMessageCount());
            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                printMessage((MimeMessage) message);
            }
        } finally {
            if (folder != null) {
                try {
                    folder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Store createSSLStore() throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.port", "995"); // 主机端口号
        props.setProperty("mail.pop3.host", this.popHost);// POP3主机名
        // 启动SSL:
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "995");
        URLName url = new URLName("pop3", this.popHost, 995, "", this.username, this.password);
        Session session = Session.getInstance(props, null);
        session.setDebug(this.debug); // 显示调试信息
        Store store = new POP3SSLStore(session, url);
        store.connect();
        return store;
    }

    static void printMessage(MimeMessage msg) throws IOException, MessagingException {
        System.out.println("--------------------");
        System.out.println("Subject: " + MimeUtility.decodeText(msg.getSubject()));
        System.out.println("From: " + getFrom(msg));
        System.out.println("To: " + getTo(msg));
        System.out.println("Sent: " + msg.getSentDate().toString());
        System.out.println("Seen: " + msg.getFlags().contains(Flags.Flag.SEEN));
        System.out.println("Priority: " + getPriority(msg));
        System.out.println("Size: " + msg.getSize() / 1024 + "kb");
        System.out.println("Body: " + getBody(msg));
        System.out.println("--------------------");
        System.out.println();
    }

    static String getFrom(MimeMessage msg) throws IOException, MessagingException {
        Address[] froms = msg.getFrom();
        return addressToString(froms[0]);
    }

    static String getTo(MimeMessage msg) throws MessagingException, IOException {
        // 使用 msg.getAllRecipients() 获取所有收件人
        Address[] tos = msg.getRecipients(RecipientType.TO);
        List<String> list = new ArrayList<>();
        for (Address to : tos) {
            list.add(addressToString(to));
        }
        return String.join(", ", list);
    }

    static String addressToString(Address addr) throws IOException {
        InternetAddress address = (InternetAddress) addr;
        String personal = address.getPersonal();
        return personal == null ? address.getAddress()
                : (MimeUtility.decodeText(personal) + " <" + address.getAddress() + ">");
    }

    static String getPriority(MimeMessage msg) throws MessagingException {
        String priority = "Normal";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String header = headers[0];
            if ("1".equals(header) || "high".equalsIgnoreCase(header)) {
                priority = "High";
            } else if ("5".equals(header) || "low".equalsIgnoreCase(header)) {
                priority = "Low";
            }
        }
        return priority;
    }

    static String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String body = getBody(bodyPart);
                if (!body.isEmpty()) {
                    return body;
                }
            }
        }
        return "";
    }
}
