package com.canvas.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.canvas.qa.core.ReportManager;

public class EmailUtils {

	static String _fileName = "src/main/resources/report_output.csv";

	public static void send() throws MessagingException {
		try {
			Date date = new Date();
			Format formatter = new SimpleDateFormat("MM/dd/yy HH:mm");
			String from = PropertyUtils.getProperty("mail.smtp.user");
			String host = PropertyUtils.getProperty("mail.smtp.host");
			String pass = PropertyUtils.getProperty("mail.smtp.password");
			String port = PropertyUtils.getProperty("mail.smtp.port");
			final String RECIPIENT = PropertyUtils.getProperty("mail.smtp.recipients");

			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));

			msg.setRecipients(Message.RecipientType.TO, RECIPIENT);
			msg.setSubject("Final Test Result " + formatter.format(date));

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Failures: " + ReportManager.testFailed + " Successes: " + ReportManager.testPassed
					+ "\n\nFailures: \n" + ReportManager.failures + "\nToday");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(_fileName);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(_fileName);
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", from, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
		} catch (Exception e) {
		}

	}

	public static void sendxy(String emailBody) {
		String to = PropertyUtils.getProperty("mail.smtp.recipients");
		String from = PropertyUtils.getProperty("mail.smtp.user");
		String host = PropertyUtils.getProperty("mail.smtp.host");
		String pass = PropertyUtils.getProperty("mail.smtp.password");
		Properties properties = System.getProperties();
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.host", host);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.fallback", "true");
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Ping");
			message.setText("Hello, this is example of sending email  ");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			final Transport transport = session.getTransport("smtps");
			transport.connect(host, 25, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
