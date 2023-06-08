import java.util.ArrayList;
import java.util.Date;

public class MockMimeMessage {
    private MockSession session;
    private String sentBody;
    private String sentSubject;
    private String subjectEncoding;
    private String textEncoding;
    private String replyTo;
    private String toEmail;
    private String fromEmail;
    private Date date;
    private ArrayList<String> headers;

    public MockMimeMessage(MockSession session) {
        this.session = session;
        headers = new ArrayList<String>();
    }

    public void addHeader(String header) {
        headers.add(header);
    }

    public void setSubject(String subject, String encoding) {
        this.sentSubject = subject;
        this.subjectEncoding = encoding;
    }

    public void setText(String text, String encoding) {
        this.sentBody = text;
        this.textEncoding = encoding;
    }

    public void setFrom(String fromEmail) {
        this.fromEmail = fromEmail;
    }
    
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public void setRecipients(String toEmail) {
       this.toEmail = toEmail;
    }

    public void setSentDate(Date date) {
        this.date = date;
    }

    public String getSentSubject() {
        return this.sentSubject;
    }

    public String getSentBody() {
        return this.sentBody;
    }

    public MockSession getSession() {
        return this.session;
    }

    public String getTextEncoding() {
        return this.textEncoding;
    }

    public String getSubjectEncoding() {
        return this.subjectEncoding;
    }

    public String getReplyTo() {
        return this.replyTo;
    }

    public String getToEmail() {
        return this.toEmail;
    }

    public String getFromEmail() {
        return this.fromEmail;
    }

    public Date getDate() {
        return this.date;
    }

    public ArrayList<String> getHeaders() {
        return this.headers;
    }
}
