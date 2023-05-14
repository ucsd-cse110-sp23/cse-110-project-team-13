package test.java;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.junit.*;

import main.java.ConnectionSetup;
import main.java.CreateRequest;
import main.java.MockResponseHandler;
import main.java.MockTranscribeAudio;
import main.java.ResponseHandler;
import main.java.WriteToOutput;
public class TranscriptionTests {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String MODEL = "whisper-1";
    private static final String FILE_PATH = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/Test.m4a";

    @Test
    public void testTranscription() {
        String transcription = null;
        try {
            transcription = MockTranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/TestMock.txt");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("This is a test.", transcription);
    }

    @Test
    public void testTranscriptionSilence() {
        String transcription = null;
        try {
            transcription = MockTranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/SilenceMock.txt");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("", transcription);
    }

    @Test
    public void testSetupConnection() {
        try {
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            assertEquals("POST", connection.getRequestMethod());
            assertEquals(true, connection.getDoOutput());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testSetupRequestHeader() {
        try {
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            CreateRequest.setupRequestHeader("TOKEN", boundary, connection);
            assertEquals("multipart/form-data; boundary=" + boundary, connection.getRequestProperty("Content-Type"));
            assertNull(connection.getRequestProperty("Authorization"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testCloseRequestBody() {
        try {
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            OutputStream outputStream = connection.getOutputStream();
            CreateRequest.closeRequestBody(boundary, outputStream);
            assertEquals("\r\n--" + boundary + "--\r\n", outputStream.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testHandleResponseSuccess() {
        String transcript = null;
        try {
            InputStream inputStream = new FileInputStream("/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/TestMock.txt");
            transcript = MockResponseHandler.handleResponse(HttpURLConnection.HTTP_OK, inputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("This is a test.", transcript);
    }

    @Test
    public void testHandleResponseError() {
        String transcript = null;
        try {
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            File file = new File(FILE_PATH);
            String boundary = "Boundary-" + System.currentTimeMillis();
            CreateRequest.setupRequestHeader("TOKEN", boundary, connection);
            OutputStream outputStream = connection.getOutputStream();
            WriteToOutput.writeFileToOutputStream(outputStream, file, boundary);
            WriteToOutput.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
            transcript = ResponseHandler.handleResponse(connection.getResponseCode(), connection);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("{    \"error\": {        \"message\": \"\",        "
                     + "\"type\": \"invalid_request_error\",        \"param\": null,        "
                     + "\"code\": \"invalid_api_key\"    }}", transcript);
    }

    @Test
    public void testWriteFile() {
        try {
            File file = new File(FILE_PATH);
            String boundary = "Boundary-" + System.currentTimeMillis();
            String expectedOutputStream = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"" +
            file.getName() + "\"\r\nContent-Type: audio/mpeg\r\n\r\n";
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            OutputStream outputStream = connection.getOutputStream();
            WriteToOutput.writeFileToOutputStream(outputStream, file, boundary);
            assertEquals(expectedOutputStream, outputStream.toString().substring(0, 120));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testWriteParameter() {
        try {
            HttpURLConnection connection = ConnectionSetup.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            String expectedOutputStream = "--" + boundary + 
            "\r\nContent-Disposition: form-data; name=\"model\"\r\n\r\n" + MODEL + "\r\n";
            OutputStream outputStream = connection.getOutputStream();
            WriteToOutput.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
            assertEquals(expectedOutputStream, outputStream.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
