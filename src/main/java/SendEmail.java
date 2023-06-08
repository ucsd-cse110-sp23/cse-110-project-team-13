/*Citation
Website: https://www.digitalocean.com/community/tutorials/javamail-example-send-mail-in-java-smtp
Title: JavaMail Example - Send Mail in Java using SMTP
Data Accessed: 6/6/22
Source Use: Learning and Template
Initials: BO
*/

import java.util.Properties;
import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static Exception sendEmail (String fEmail, String emailPassword, String tEmail, String SMTPHost, String TLSPort, String body){
    final String fromEmail = fEmail; 
		final String password = emailPassword;
		final String toEmail = tEmail;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTPHost); 
		props.put("mail.smtp.port", TLSPort);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		return makeEmailSession(session, toEmail,"SayIt Email", body);
	}

	public static Exception makeEmailSession(Session session, String toEmail, String subject, String body){
    Exception result = null;
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

	      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
    	  Transport.send(msg);  

	    }
	    catch (Exception e) {
	      return e;
	    }
    return result;
	}
	
}