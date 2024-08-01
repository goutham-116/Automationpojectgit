package Util;

import java.util.Properties;



import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendExceptionMailPage {
	
	public static void sendEmail(String from, String to, String subject, String body) {
		final String username = "";// smtp user name//llll
		final String password = "";// smtp password

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		
		
		//props.put("mail.smtp.ssl.enable", "true");

		props.put("mail.smtp.host", ConstantsPage.SERVER_HOST);
		// props.put("mail.smtp.host",
		// "tempositions-com.mail.protection.outlook.com");// smtp server
		props.put("mail.smtp.port", "25");// smtp port

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));// from email
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println("Exception at sending email" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void sendInLineImageEmail(String from, String to, String subject, String body, String imagefilepath) {
		final String username = "";// smtp user name
		final String password = "";// smtp password

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");

		props.put("mail.smtp.host", ConstantsPage.SERVER_HOST);
		props.put("mail.smtp.port", "25");// smtp port

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);

			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<P>" + body + "</P><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(imagefilepath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Screenshot mail sent..");
			WriteTextUtil.log("Screenshot mail sent..");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
