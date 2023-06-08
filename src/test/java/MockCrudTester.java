import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

public class MockCrudTester {
    @Test
    void addQuestionAndAnswerTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Call the method with the mock instance
        mockCreate.addQuestionAndAnswer("question", "answer", "email@test.com");
        
        // Verify if the method was called with correct arguments
        Mockito.verify(mockCreate).addQuestionAndAnswer("question", "answer", "email@test.com");
    }

    @Test
    void addLoginInfoTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Mock the behavior
        Mockito.when(mockCreate.addLoginInfo(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
        
        // Call the method with the mock instance
        boolean result = mockCreate.addLoginInfo("email@test.com", "password");
        
        // Assert the result
        assert(result);
        
        // Verify if the method was called with correct arguments
        Mockito.verify(mockCreate).addLoginInfo("email@test.com", "password");
    }

    @Test
    void addEmailTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Call the method with the mock instance
        mockCreate.addEmail("question", "answer", "email@test.com");
        
        // Verify if the method was called with correct arguments
        Mockito.verify(mockCreate).addEmail("question", "answer", "email@test.com");
    }

    @Test
    void userExistsTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);

        mockRead.userExists("email@test.com");

        Mockito.verify(mockRead).userExists("email@test.com");
    }

    @Test
    void successfulLoginTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);

        mockRead.successfulLogin("email@test.com", "password");

        Mockito.verify(mockRead).successfulLogin("email@test.com", "password");
    }

    @Test
    void readUserChatDataByEmailTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        ArrayList<Question> questions = new ArrayList<>();
        Mockito.when(mockRead.readUserChatDataByEmail(ArgumentMatchers.anyString())).thenReturn(questions);

        ArrayList<Question> result = mockRead.readUserChatDataByEmail("email@test.com");

        Mockito.verify(mockRead).readUserChatDataByEmail("email@test.com");
        assert(result.equals(questions));
    }

    @Test
    void checkAutomaticLoginTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        Mockito.when(mockRead.checkAutomaticLogin()).thenReturn("email@test.com");

        String result = mockRead.checkAutomaticLogin();

        Mockito.verify(mockRead).checkAutomaticLogin();
        assert(result.equals("email@test.com"));
    }

    @Test
    void sendEmailInfoTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        String[] expectedInfo = new String[]{"John", "Doe", "jdoe", "jdoe@example.com", "smtp.example.com", "587", "password"};
        Mockito.when(mockRead.sendEmailInfo(ArgumentMatchers.anyString())).thenReturn(expectedInfo);

        String[] result = mockRead.sendEmailInfo("email@test.com");

        Mockito.verify(mockRead).sendEmailInfo("email@test.com");
        assertArrayEquals(expectedInfo, result);
    }

    @Test
    void getUserInfoTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        String[] expectedInfo = new String[]{"email@test.com", "password", "John", "Doe", "jdoe", "jdoe@example.com", "smtp.example.com", "587", "password"};
        Mockito.when(mockRead.getUserInfo(ArgumentMatchers.anyString())).thenReturn(expectedInfo);

        String[] result = mockRead.getUserInfo("email@test.com");

        Mockito.verify(mockRead).getUserInfo("email@test.com");
        assertArrayEquals(expectedInfo, result);
    }

    @Test
    void updateSetupEmailInfoTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);
        
        mockUpdate.updateSetupEmailInfo("applicationEmail@test.com", "firstName", "lastName", "displayName", "email@test.com", "SMTPHost", "TLSPort", "emailPassword");

        Mockito.verify(mockUpdate).updateSetupEmailInfo("applicationEmail@test.com", "firstName", "lastName", "displayName", "email@test.com", "SMTPHost", "TLSPort", "emailPassword");
    }
    
    @Test
    void automaticallyLogTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);

        mockUpdate.automaticallyLog("email@test.com");

        Mockito.verify(mockUpdate).automaticallyLog("email@test.com");
    }

    @Test
    void manuallyLogTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);

        mockUpdate.manuallyLog("email@test.com");

        Mockito.verify(mockUpdate).manuallyLog("email@test.com");
    }

    @Test
    void clearAllTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearAll("email@test.com");

        Mockito.verify(mockDelete).clearAll("email@test.com");
    }
    
    @Test
    void clearOneTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearOne("testQuestion", "email@test.com");

        Mockito.verify(mockDelete).clearOne("testQuestion", "email@test.com");
    }

    @Test
    void clearDatabaseTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearDatabase();

        Mockito.verify(mockDelete).clearDatabase();
    }
}

