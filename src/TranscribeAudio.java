import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranscribeAudio {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    //private static final String FILE_PATH = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/TestFiles/Lab4.m4a";

    public static void main(String[] args) {
        try {
        String transcription = transcribeAudio(args[0]);
        System.out.println(transcription);
        } catch (IOException exception) {
        }
    }

    public static String transcribeAudio(String filePath) throws IOException {
        String transcription;

        File file = new File(filePath);

        HttpURLConnection connection = setupConnection(API_ENDPOINT);

        String boundary = "Boundary-" + System.currentTimeMillis();
        setupRequestHeader(TOKEN, boundary, connection);

        OutputStream outputStream = connection.getOutputStream();

        writeParameter.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);

        writeFile.writeFileToOutputStream(outputStream, file, boundary);

        closeRequestBody(boundary, outputStream);

        int responseCode = connection.getResponseCode();

        transcription = handleResponse(responseCode, connection);

        connection.disconnect();
        
        return transcription;
    }

    public static HttpURLConnection setupConnection(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoOutput(true);
        return httpConnection;
    }

    public static void setupRequestHeader(String token, String boundary, HttpURLConnection connection) {
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; boundary=" + boundary
        );
        connection.setRequestProperty("Authorization", "Bearer " + token);
    }

    public static void closeRequestBody(String boundary, OutputStream outputStream) throws IOException {
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        outputStream.flush();
        outputStream.close();
    }

    public static String handleResponse(int responseCode, HttpURLConnection connection) {
        String transcription;
        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                transcription = handleSuccess.handleSuccessResponse(connection);
            } else {
                transcription = handleError.handleErrorResponse(connection);
            }
        } catch (IOException exception) {
            transcription = "";
        }
        return transcription;
    }
}