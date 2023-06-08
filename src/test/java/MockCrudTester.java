import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;
import org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito.verify;
import org.mockito.Mockito.when;

import java.util.ArrayList;

public class MockCrudTester {
    @Test
    void addQuestionAndAnswerTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Call the method with the mock instance
        mockCreate.addQuestionAndAnswer("question", "answer", "email@test.com");
        
        // Verify if the method was called with correct arguments
        verify(mockCreate).addQuestionAndAnswer("question", "answer", "email@test.com");
    }

    @Test
    void addLoginInfoTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Mock the behavior
        Mockito.when(mockCreate.addLoginInfo(anyString(), anyString())).thenReturn(true);
        
        // Call the method with the mock instance
        boolean result = mockCreate.addLoginInfo("email@test.com", "password");
        
        // Assert the result
        assert(result);
        
        // Verify if the method was called with correct arguments
        verify(mockCreate).addLoginInfo("email@test.com", "password");
    }

    @Test
    void addEmailTest() {
        // Create a mock instance of Create
        MockCreate mockCreate = Mockito.mock(MockCreate.class);
        
        // Call the method with the mock instance
        mockCreate.addEmail("question", "answer", "email@test.com");
        
        // Verify if the method was called with correct arguments
        verify(mockCreate).addEmail("question", "answer", "email@test.com");
    }

    @Test
    void userExistsTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);

        mockRead.userExists("email@test.com");

        verify(mockRead).userExists("email@test.com");
    }

    @Test
    void successfulLoginTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);

        mockRead.successfulLogin("email@test.com", "password");

        verify(mockRead).successfulLogin("email@test.com", "password");
    }

    @Test
    void readUserChatDataByEmailTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        ArrayList<Question> questions = new ArrayList<>();
        when(mockRead.readUserChatDataByEmail(anyString())).thenReturn(questions);

        ArrayList<Question> result = mockRead.readUserChatDataByEmail("email@test.com");

        verify(mockRead).readUserChatDataByEmail("email@test.com");
        assert(result.equals(questions));
    }

    @Test
    void checkAutomaticLoginTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        when(mockRead.checkAutomaticLogin()).thenReturn("email@test.com");

        String result = mockRead.checkAutomaticLogin();

        verify(mockRead).checkAutomaticLogin();
        assert(result.equals("email@test.com"));
    }

    @Test
    void sendEmailInfoTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        String[] expectedInfo = new String[]{"John", "Doe", "jdoe", "jdoe@example.com", "smtp.example.com", "587", "password"};
        when(mockRead.sendEmailInfo(anyString())).thenReturn(expectedInfo);

        String[] result = mockRead.sendEmailInfo("email@test.com");

        verify(mockRead).sendEmailInfo("email@test.com");
        assertArrayEquals(expectedInfo, result);
    }

    @Test
    void getUserInfoTest() {
        MockRead mockRead = Mockito.mock(MockRead.class);
        String[] expectedInfo = new String[]{"email@test.com", "password", "John", "Doe", "jdoe", "jdoe@example.com", "smtp.example.com", "587", "password"};
        when(mockRead.getUserInfo(anyString())).thenReturn(expectedInfo);

        String[] result = mockRead.getUserInfo("email@test.com");

        verify(mockRead).getUserInfo("email@test.com");
        assertArrayEquals(expectedInfo, result);
    }

    @Test
    void updateSetupEmailInfoTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);
        
        mockUpdate.updateSetupEmailInfo("applicationEmail@test.com", "firstName", "lastName", "displayName", "email@test.com", "SMTPHost", "TLSPort", "emailPassword");

        verify(mockUpdate).updateSetupEmailInfo("applicationEmail@test.com", "firstName", "lastName", "displayName", "email@test.com", "SMTPHost", "TLSPort", "emailPassword");
    }
    
    @Test
    void automaticallyLogTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);

        mockUpdate.automaticallyLog("email@test.com");

        verify(mockUpdate).automaticallyLog("email@test.com");
    }

    @Test
    void manuallyLogTest() {
        MockUpdate mockUpdate = Mockito.mock(MockUpdate.class);

        mockUpdate.manuallyLog("email@test.com");

        verify(mockUpdate).manuallyLog("email@test.com");
    }

    @Test
    void clearAllTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearAll("email@test.com");

        verify(mockDelete).clearAll("email@test.com");
    }
    
    @Test
    void clearOneTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearOne("testQuestion", "email@test.com");

        verify(mockDelete).clearOne("testQuestion", "email@test.com");
    }

    @Test
    void clearDatabaseTest() {
        MockDelete mockDelete = Mockito.mock(MockDelete.class);

        mockDelete.clearDatabase();

        verify(mockDelete).clearDatabase();
    }
}

