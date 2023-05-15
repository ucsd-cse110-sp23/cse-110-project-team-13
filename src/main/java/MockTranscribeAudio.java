package main.java;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class MockTranscribeAudio {
    
    public static String transcribeAudio(String filePath) throws IOException {
        String transcription;

        InputStream inputStream = new FileInputStream(filePath);

        transcription = MockResponseHandler.handleResponse(HttpURLConnection.HTTP_OK, inputStream);
        
        return transcription;
    }

}