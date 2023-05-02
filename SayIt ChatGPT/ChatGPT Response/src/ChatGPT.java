import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    public static void main(String[] args) throws IOException, InterruptedException{
      String prompt = args[1];
      int maxTokens = Integer.parseInt(args[0]);

      JSONObject requestBody = new JSONObject();
      requestBody.put("model", MODEL);
      requestBody.put("prompt", prompt);
      requestBody.put("max_tokens", maxTokens);
      requestBody.put("temperature", 1.0);

      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create(API_ENDPOINT))
      .header("Content-Type", "application/json")
      .header("Authorization", String.format("Bearer %s", API_KEY))
      .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
      .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      String responseBody = response.body();

      JSONObject responseJSON = new JSONObject(responseBody);
      JSONArray choices = responseJSON.getJSONArray("choices");
      String generatedText = choices.getJSONObject(0).getString("text");

      System.out.println(generatedText);
    }
 
    
}
