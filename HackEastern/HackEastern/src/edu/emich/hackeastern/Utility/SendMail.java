package edu.emich.hackeastern.Utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author paulhuang
 * 
 */
 
public class SendMail {
 
	 private static Properties mailServerProperties;
	 private static Session getMailSession;
	 private static MimeMessage generateMailMessage;
 

	public static void generateAndSendEmail(String Search,String Email) throws AddressException, MessagingException {
 
		// Step1
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
 
		// Step2
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		
		//
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("paul_hzq@hotmail.com"));
		generateMailMessage.setSubject("Course is available.");
		
		String emailBody = "Remind email by QuickReg: " + "<br><br>" + Search;
		generateMailMessage.setContent(emailBody, "text/html");
 
		// Step3
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "paulhzq0502@gmail.com", "paulhuang12345");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
