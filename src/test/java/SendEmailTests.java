import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import org.junit.Before;
import org.junit.Test;

public class SendEmailTests {

  @Test
  public void sendOneEmail(){
    String fromEmail = "bgsong03@gmail.com";
    String emailPassword = "lqbdysmhzgotcjeb";
    String toEmail = "bong@ucsd.edu";
    String SMTPHost = "smtp.gmail.com";
    String TLSPort = "465";
    String body = "Test";

    assertNull(SendEmail.sendEmail(fromEmail, emailPassword, toEmail, SMTPHost, TLSPort, body));
  }

  @Test
  public void failEmailSend() {
    String fromEmail = "fake_email@gmail.com";
    String emailPassword = "password123";
    String toEmail = "bgsong03@gmail.com";
    String SMTPHost = "smtp.gmail.com";
    String TLSPort = "465";
    String body = "Test";

    assertNotNull(SendEmail.sendEmail(fromEmail, emailPassword, toEmail, SMTPHost, TLSPort, body));
  }

  @Test
  public void setupEmail(){
    Delete.clearDatabase();
    String newUserEmail = "email_test@gmail.com";
    String newUserPassword = "12345";
    String fromEmail = "fake_email@gmail.com";
    String firstName = "Jack";
    String lastName = "Black";
    String displayName = "JB";
    String emailPassword = "password123";
    String SMTPHost = "smtp.gmail.com";
    String TLSPort = "587";
    String[] userInfo = new String[7];

    Create.addLoginInfo(newUserEmail, newUserPassword);
    Update.updateSetupEmailInfo(newUserEmail, firstName, lastName, displayName, fromEmail, SMTPHost, TLSPort, emailPassword);

    userInfo = Read.sendEmailInfo(newUserEmail);
    assertTrue(userInfo[0].equals(firstName));
    assertTrue(userInfo[1].equals(lastName));
    assertTrue(userInfo[2].equals(displayName));
    assertTrue(userInfo[3].equals(fromEmail));
    assertTrue(userInfo[4].equals(SMTPHost));
    assertTrue(userInfo[5].equals(TLSPort));
    assertTrue(userInfo[6].equals(emailPassword));

    Delete.clearDatabase();
  }

  @Test
  public void createEmail(){
    Delete.clearDatabase();
    String transcript = "Create email to Jill for our meeting at 3";
    String testEmail = "email_test@gmail.com";
    Create.addLoginInfo(testEmail, "12345");
    Body body = new Body("email_test@gmail.com");
    try {
      body.newQuestion(transcript, true);
    }
    catch (IOException | InterruptedException e){
      e.getStackTrace();
    }

    assertNotNull(body.currQuestion);
    assertTrue(body.currQuestion.isEmail);
    assertTrue(body.currQuestion.qName.getText().equals("Email: " + transcript));
    assertTrue(Read.readUserChatDataByEmail(testEmail).size() == 1);
    Delete.clearDatabase();
  }

  @Test
  public void sendCreatedEmail(){
    Delete.clearDatabase();
    String transcript = "Create email to Jill for our meeting at 3";
    String firstName = "Bryce";
    String lastName = "Ong";
    String displayName = "Bong";
    String testEmail = "email_test@gmail.com";
    String fromEmail = "bgsong03@gmail.com";
    String emailPassword = "lqbdysmhzgotcjeb";
    String SMTPHost = "smtp.gmail.com";
    String TLSPort = "465";

    Create.addLoginInfo(testEmail, "12345");
    Body body = new Body("email_test@gmail.com");
    try {
      body.newQuestion(transcript, true);
    }
    catch (IOException | InterruptedException e){
      e.getStackTrace();
    }

    Update.updateSetupEmailInfo(testEmail, firstName, lastName, displayName, fromEmail, SMTPHost, TLSPort, emailPassword);
    assertTrue(body.sendEmail("Send Email to bong at ucsd.edu"));

    Delete.clearDatabase();
  }

  @Test
  public void sendCreatedEmailNoSetup(){
    String transcript = "Create email to Jill for our meeting at 3";
    String testEmail = "email_test@gmail.com";

    Create.addLoginInfo(testEmail, "12345");
    Body body = new Body("email_test@gmail.com");
    body.debugOn();
    try {
      body.newQuestion(transcript, true);
    }
    catch (IOException | InterruptedException e){
      e.getStackTrace();
    }

    assertFalse(body.sendEmail("Send Email to bong at ucsd.edu"));
    Delete.clearDatabase();
  }

}
