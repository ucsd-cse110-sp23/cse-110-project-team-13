import java.util.Properties;
import java.util.Date;

public class MockSendEmail {
	private static MockTransport transport;
	public static void main(String[] args) {
		final String fromEmail = "email@gmail.com"; 
		final String password = "password";
		final String toEmail = "otherEmail@gmail.com";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		MockAuthenticator auth = new MockAuthenticator();
		auth.PasswordAuthenticator(fromEmail, password);

		MockSession session = new MockSession(props, auth);
		
		sendEmail(session, toEmail, fromEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
		
	}

	public static void sendEmail(MockSession session, String toEmail, String fromEmail, String subject, String body){
	      MockMimeMessage msg = new MockMimeMessage(session);
	      msg.addHeader("Content-type");
          msg.addHeader("text/HTML; charset=UTF-8");
	      msg.addHeader("format");
          msg.addHeader("flowed");
	      msg.addHeader("Content-Transfer-Encoding");
          msg.addHeader("8bit");

	      msg.setFrom(fromEmail);

	      msg.setReplyTo("no_reply@example.com");

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(toEmail);
    	  transport = new MockTransport();
          transport.send(msg);  
	}
	public static MockTransport retrieveTransport() {
		return MockSendEmail.transport;
	}
}