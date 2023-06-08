import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

public class MockEmailTests {
    private MockAuthenticator auth;
    private MockSession session;
    private MockMimeMessage msg;

    @Before
    public void startUp() {
        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
        
        auth = new MockAuthenticator();
        session = new MockSession(props,auth);
        msg = new MockMimeMessage(session);

        // Necessary to test getters
        msg.addHeader("Content-type");
        msg.addHeader("text/HTML; charset=UTF-8");
	    msg.addHeader("format");
        msg.addHeader("flowed");
	    msg.addHeader("Content-Transfer-Encoding");
        msg.addHeader("8bit");

        String fromEmail = "from@ucsd.edu";
        String toEmail = "to@ucsd.edu";
        String subject = "Subject";
        String body = "Body";
	    msg.setFrom(fromEmail);
	    msg.setReplyTo("no_reply@example.com");
	    msg.setSubject(subject, "UTF-8");
	    msg.setText(body, "UTF-8");
	    msg.setSentDate(new Date());
	    msg.setRecipients(toEmail);
    }

    @Test
    public void authenticatorTest() {
        String email1 = "test@ucsd.edu";
        String pass1 = "password";
        String email2 = "wrong@ucsd.edu";
        String pass2 = "wrong";
        auth.addCredentials(email1,pass1);
        auth.addCredentials(email2,pass2);
        boolean correct = auth.PasswordAuthenticator(email1, pass1);
        boolean incorrect = auth.PasswordAuthenticator(email2, pass1);
        boolean incorrect2 = auth.PasswordAuthenticator(email1, pass2);
        assertTrue(correct);
        assertFalse(incorrect);
        assertFalse(incorrect2);
    }

    @Test
    public void sessionTest() {
        assertNotNull(session.getMockAuthenticator());
        assertNotNull(session.getProperties());
    }

    @Test
    public void transportTest() {
        MockTransport transport = new MockTransport();
        transport.send(msg);
        assertNotNull(transport.getMessage());
    }
    
    @Test
    public void MimeTestGetters() {
        //Populate headers, test equivalence
        ArrayList<String> header = new ArrayList<>();
        header.add("Content-type");
        header.add("text/HTML; charset=UTF-8");
        header.add("format");
        header.add("flowed");
        header.add("Content-Transfer-Encoding");
        header.add("8bit");
        ArrayList<String> mimeHeaders = msg.getHeaders();
        assertEquals(mimeHeaders,header);

        //Other getters
        String sentSubject = msg.getSentSubject();
        assertEquals(sentSubject, "Subject");

        String sentBody = msg.getSentBody();
        assertEquals(sentBody, "Body");

        MockSession session = msg.getSession();
        assertNotNull(session);

        String textEncoding = msg.getTextEncoding();
        assertEquals(textEncoding, "UTF-8");

        String subjectEncoding = msg.getSubjectEncoding();
        assertEquals(subjectEncoding, "UTF-8");

        String replyTo = msg.getReplyTo();
        assertEquals(replyTo, "no_reply@example.com");

        String toEmail = msg.getToEmail();
        assertEquals(toEmail, "to@ucsd.edu");

        String fromEmail = msg.getFromEmail();
        assertEquals(fromEmail, "from@ucsd.edu");

        Date date = msg.getDate();
        assertNotNull(date);

    }

    @Test
    public void MimeTestSetters() {
        // Test the setters of MockMimeMessage
        String newSubject = "New Subject";
        String newSubjectEncoding = "UTF-16";
        msg.setSubject(newSubject, newSubjectEncoding);
        assertEquals(newSubject, msg.getSentSubject());
        assertEquals(newSubjectEncoding, msg.getSubjectEncoding());

        String newText = "New Text";
        String newTextEncoding = "UTF-16";
        msg.setText(newText, newTextEncoding);
        assertEquals(newText, msg.getSentBody());
        assertEquals(newTextEncoding, msg.getTextEncoding());

        String newFromEmail = "newfrom@ucsd.edu";
        msg.setFrom(newFromEmail);
        assertEquals(newFromEmail, msg.getFromEmail());

        String newReplyTo = "new_reply@example.com";
        msg.setReplyTo(newReplyTo);
        assertEquals(newReplyTo, msg.getReplyTo());

        String newToEmail = "newto@ucsd.edu";
        msg.setRecipients(newToEmail);
        assertEquals(newToEmail, msg.getToEmail());

        Date newSentDate = new Date();
        msg.setSentDate(newSentDate);
        assertEquals(newSentDate, msg.getDate());
    }

    @Test
    public void sendEmailTest() {
        String fromEmail = "from@ucsd.edu";
        String toEmail = "to@ucsd.edu";
        String subject = "Subject";
        String body = "Body";
        MockSendEmail.sendEmail(session, toEmail, fromEmail, subject, body);
        MockTransport transport = MockSendEmail.retrieveTransport();
        MockMimeMessage sentMessage = transport.getMessage();
        assertNotNull(sentMessage);
        assertEquals(fromEmail, sentMessage.getFromEmail());
        assertEquals(toEmail, sentMessage.getToEmail());
        assertEquals(subject, sentMessage.getSentSubject());
        assertEquals(body, sentMessage.getSentBody());
    }
}
