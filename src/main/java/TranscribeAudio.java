import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

public class TranscribeAudio {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";

    public static String transcribeAudio(String filePath) throws IOException {
        String transcription;

        File file = new File(filePath);

        HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);

        CreateRequest.createRequest(connection, file);

        int responseCode = connection.getResponseCode();

        transcription = ResponseHandler.handleResponse(responseCode, connection);

        connection.disconnect();
        
        return transcription;
    }

}