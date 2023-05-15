package com.example.chatgpt;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatGPTTest {
    
    @Test
    public void testAPIEndpoint() throws Exception {
        //Create prompt
        String prompt = "What is the meaning of life?";
        int maxTokens = 50;
        
        //Generate text from ChatGPT API
        String generatedText = ChatGPT.generateText(prompt, maxTokens);
        
        //Test if response received from API
        Assertions.assertNotNull(generatedText);
        Assertions.assertTrue(generatedText.length() > 0);
    }

    @Test
    public void testAPIEndpointWithLongPrompt() {
        //Create extremely long prompt
        String longPrompt = "a".repeat(2049);
        
        //Test if exception occurs from length
        assertThrows(IllegalArgumentException.class, () -> {
            ChatGPT.generateText(longPrompt, 5);
        });
    }


    @Test
    public void testAPIEndpointWithLargeMaxTokens() throws Exception {
        //Create prompt
        String prompt = "What is the meaning of life?";
        //Allot large number of tokens
        int maxTokens = 2048;
        
        //Generate response from ChatGPT API
        String generatedText = ChatGPT.generateText(prompt, maxTokens);
        
        //Test if response was received from API
        Assertions.assertNotNull(generatedText);
        Assertions.assertTrue(generatedText.length() > 0);
    }
}
