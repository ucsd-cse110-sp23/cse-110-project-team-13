package main.java;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionSetup {
    public static HttpURLConnection setupConnection(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoOutput(true);
        return httpConnection;
    }
}
