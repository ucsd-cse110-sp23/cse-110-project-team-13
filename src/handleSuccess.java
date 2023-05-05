import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.JSONException;
public class handleSuccess {
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
}
