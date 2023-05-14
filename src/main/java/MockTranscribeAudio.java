package main.java;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.JSONException;

public class MockTranscribeAudio {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String MOCKTOKEN = "Mock Token";
    private static final String MODEL = "whisper-1";
    private static final String FILE_PATH = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/TestFiles/Lab4.m4a";

    public static String transcribeAudio(String filePath) throws IOException {
        String transcription;

        //HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);

        InputStream inputStream = new FileInputStream(filePath);

        //int responseCode = connection.getResponseCode();

        transcription = MockTranscribeAudio.handleResponse(HttpURLConnection.HTTP_OK, inputStream);

        //connection.disconnect();
        
        return transcription;
    }

    public static String handleResponse(int responseCode, InputStream connection) {
        String transcription = "";
        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                transcription = handleSuccessResponse(connection);
            } else {
                transcription = handleErrorResponse(connection);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return transcription;
    }

    public static String handleSuccessResponse(InputStream connection) 
        throws IOException, JSONException {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection)
            );
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
    }

    public static String handleErrorResponse(InputStream connection)
        throws IOException, JSONException {
            BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection)
            );
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            String errorResult = errorResponse.toString();
            return errorResult;
    }
}