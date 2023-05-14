package main.java;
import java.net.HttpURLConnection;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ResponseHandler {
    public static String handleResponse(int responseCode, HttpURLConnection connection) {
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

    public static String handleSuccessResponse(HttpURLConnection connection) 
        throws IOException, JSONException {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject responseJson = new JSONObject(response.toString());

            String generatedText = responseJson.getString("text");

            return generatedText;
    }

    public static String handleErrorResponse(HttpURLConnection connection)
        throws IOException, JSONException {
            BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream())
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
