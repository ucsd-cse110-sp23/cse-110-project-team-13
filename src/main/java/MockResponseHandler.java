import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONException;

public class MockResponseHandler {
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
