import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChatGPTTest {
    
    @Test
    public void testAPIEndpoint() throws Exception {
        // Arrange
        String prompt = "What is the meaning of life?";
        int maxTokens = 50;
        
        // Act
        String generatedText = ChatGPT.generateText(prompt, maxTokens);
        
        // Assert
        Assertions.assertNotNull(generatedText);
        Assertions.assertTrue(generatedText.length() > 0);
    }

    @Test
    public void testAPIEndpointWithLongPrompt() throws Exception {
        // Arrange
        String prompt = "This is a very long prompt that is over 2048 characters long. This should cause an error when sent to the OpenAI API.";
        int maxTokens = 50;
        
        // Act & Assert
        Assertions.assertThrows(IOException.class, () -> {
            ChatGPT.generateText(prompt, maxTokens);
        });
    }

    @Test
    public void testAPIEndpointWithLargeMaxTokens() throws Exception {
        // Arrange
        String prompt = "What is the meaning of life?";
        int maxTokens = 2048;
        
        // Act
        String generatedText = ChatGPT.generateText(prompt, maxTokens);
        
        // Assert
        Assertions.assertNotNull(generatedText);
        Assertions.assertTrue(generatedText.length() > 0);
    }
}
