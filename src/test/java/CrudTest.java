import org.junit.After;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class CrudTest {

    @After
    void clearWholeDatabase(){
      Delete.clearDatabase();
    }

    // Test Create class
    @Test
    void addQuestionAndAnswer() {
        // Insert a question and answer
        Create.addQuestionAndAnswer("testQuestion", "testAnswer", "test@test.com");
        // Ensure the question and answer were successfully inserted
        assertEquals("testAnswer", Read.readUserChatDataByEmail("test@test.com").get(0).answer);
    }

    @Test 
    void addLoginInfo() {
    Create.addLoginInfo("test@test.com", "newPassword");
    assertTrue(Read.userExists("test@test.com"), "User does not exist after first insert operation");

    Update.updateSetupEmailInfo("test@test.com", "FirstName", "LastName", "DisplayName", "test@newemail.com", "smtp.gmail.com", "587", "newPassword");
    assertTrue(Read.userExists("test@test.com"), "User does not exist after update operation");

    boolean secondInsert = Create.addLoginInfo("test@test.com", "testPassword");
    assertFalse(secondInsert, "Second insert operation should have failed");
    assertTrue(Read.userExists("test@test.com"), "User does not exist after second insert operation");
    }

    @Test
    void addEmail() {
        // Insert an email
        Create.addEmail("testQuestion", "testAnswer", "test@test.com");
        // Ensure the email was successfully inserted
        assertTrue(Read.userExists("test@test.com"));
    }

    // Test Read class
    @Test
    void userExists() {
        // Ensure the user exists
        Create.addLoginInfo("usertest@test.com", "password");
        assertTrue(Read.userExists("usertest@test.com"));
    }

    @Test
    void readUserChatDataByEmail() {
        // Add a question
        Create.addQuestionAndAnswer("testQuestion", "testAnswer", "test@test.com");
        // Read the chat data
        ArrayList<Question> questionList = Read.readUserChatDataByEmail("test@test.com");
        // Ensure the question and answer were successfully read
        assertEquals("testQuestion", questionList.get(0).qName.getText());
        assertEquals("testAnswer", questionList.get(0).answer);
    }

    @Test
    void successfulLogin() {
        // Ensure the user can successfully login
        Create.addLoginInfo("test6@test.com", "newPassword");
        assertTrue(Read.successfulLogin("test6@test.com", "newPassword"));
    }

    @Test
    void checkAutomaticLogin() {
    // Log in the user automatically
    Update.automaticallyLog("test@test.com");
    // The email of the user that is automatically logged in should be the one we used in the above line
    assertEquals("test@test.com", Read.checkAutomaticLogin());
    }


    @Test
    void sendEmailInfo() {
        // Check if correct email info is sent
        Create.addLoginInfo("test@test.com", "pass");
        Update.updateSetupEmailInfo("test@test.com", "FirstName", "LastName", "DisplayName", "test@newemail.com", "smtp.gmail.com", "587", "newPassword");
        String[] info = Read.sendEmailInfo("test@test.com");
        // Replace "expectedFirstName" and other expected values
        assertEquals("FirstName", info[0]);
        // Repeat for all info array elements
    }

    @Test
    void getUserInfo() {
        // Check if correct user info is returned
        Create.addLoginInfo("test5@test.com", "password");
        String[] info = Read.getUserInfo("test5@test.com");
        assertEquals("test5@test.com", info[0]);
        // Repeat for all info array elements
    }

    // Test Update class
    @Test
    void updateSetupEmailInfo() {
        // Update email info
        Update.updateSetupEmailInfo("test@test.com", "FirstName", "LastName", "DisplayName", "test@newemail.com", "smtp.gmail.com", "587", "newPassword");
        String[] info = Read.getUserInfo("test@test.com");
        // Ensure the info was updated
        assertEquals("FirstName", Read.getUserInfo("test@test.com")[2]);
        assertEquals("LastName", Read.getUserInfo("test@test.com")[3]);
        assertEquals("DisplayName", Read.getUserInfo("test@test.com")[4]);
        assertEquals("test@newemail.com", Read.getUserInfo("test@test.com")[5]);
        assertEquals("smtp.gmail.com", Read.getUserInfo("test@test.com")[6]);
        assertEquals("587", Read.getUserInfo("test@test.com")[7]);
        assertEquals("newPassword", Read.getUserInfo("test@test.com")[8]);
    }

    @Test
    void automaticallyLog() {
    Create.addLoginInfo("test2@test.com", "12345");
    Update.automaticallyLog("test2@test.com");
    assertEquals("test2@test.com", Read.checkAutomaticLogin());
    }

    @Test
    void manuallyLog() {
        Update.manuallyLog("test@test.com");
        String autoLogin = Read.checkAutomaticLogin();
        assertTrue(autoLogin == null || autoLogin.isEmpty());
    }

    // Test Delete class
    @Test
    void clearOne() {
        Create.addQuestionAndAnswer("testQuestion", "testAnswer", "test@test.com");
        Delete.clearOne("testQuestion", "test@test.com");
        assertTrue(Read.readUserChatDataByEmail("test@test.com").isEmpty());
    }

    @Test
    void clearAll() {
        Delete.clearAll("test@test.com");
        assertTrue(Read.readUserChatDataByEmail("test@test.com").isEmpty());
    }

    @Test
    void clearDatabase() {
        Delete.clearDatabase();
        assertFalse(Read.userExists("test@test.com"));
    }

    // End of testing
}
