import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class MockGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    
    public static String generateText(String prompt, int maxTokens) throws IOException, InterruptedException {
        if (prompt.length() > 2048) {
          throw new IllegalArgumentException("Prompt is too long. It must be 2048 characters or less.");
        }
        
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);
        
        MockHTTPClient client = new MockHTTPClient();
        
        MockHTTPRequest request = new MockHTTPRequest();
        request.setURI(API_ENDPOINT);
        request.setMethod("POST");
        request.setContentType("application/json");
        request.setAuthorization(String.format("Bearer %s", API_KEY));
        request.setRequestBody(requestBody);
        
        MockHTTPResponse response = client.send("request","String");
        String responseBody = response.getBody(prompt);
        
        String generatedText = responseBody;

        return generatedText;
    }
    public static void main(String[] args) {
        String prompt = args[1];
        int maxTokens = Integer.parseInt(args[0]);

        String generatedText;
        try {
            generatedText = generateText(prompt, maxTokens);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}