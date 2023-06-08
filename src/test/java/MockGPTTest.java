import static org.junit.Assert.*;
import org.junit.Test;

public class MockGPTTest {
    public String reverse(String prompt) {
        StringBuilder reversed = new StringBuilder();
        
        for (int i = prompt.length() - 1; i >= 0; i--) {
            reversed.append(prompt.charAt(i));
        }
        
        return reversed.toString();
    }
    
    @Test
    public void testAPIEndpoint() throws Exception {
        // Arrange
        String prompt = "What is the meaning of life?";
        int maxTokens = 50;
        
        // Act
        String generatedText = MockGPT.generateText(prompt, maxTokens);
        String reversedText = reverse(prompt);
        
        // Assert
        assertEquals(reversedText,generatedText);
        assertNotNull(generatedText);
        assertTrue(generatedText.length() > 0);
    }

    @Test
    public void testAPIEndpointWithLongPrompt() {
        String longPrompt = "a".repeat(2049);
        assertThrows(IllegalArgumentException.class, () -> {
            MockGPT.generateText(longPrompt, 5);
        });
    }

    @Test
    public void testAPIEndpointWithLargeMaxTokens() throws Exception {
        // Arrange
        String prompt = "What is the meaning of life?";
        int maxTokens = 2048;
        
        // Act
        String generatedText = MockGPT.generateText(prompt, maxTokens);
        
        // Assert
        assertNotNull(generatedText);
        assertTrue(generatedText.length() > 0);
    }
}
