import java.net.HttpURLConnection;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class CreateRequest {
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";

    public static void createRequest(HttpURLConnection connection, File file) {
        String boundary = "Boundary-" + System.currentTimeMillis();
        
        setupRequestHeader(TOKEN, boundary, connection);

        try {
            OutputStream outputStream = connection.getOutputStream();

            WriteToOutput.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);

            WriteToOutput.writeFileToOutputStream(outputStream, file, boundary);

            closeRequestBody(boundary, outputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
}
