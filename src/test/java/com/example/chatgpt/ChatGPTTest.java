package com.example.chatgpt;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testAPIEndpointWithLongPrompt() {
        String longPrompt = "a".repeat(2049);
        assertThrows(IllegalArgumentException.class, () -> {
            ChatGPT.generateText(longPrompt, 5);
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
