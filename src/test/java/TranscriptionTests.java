package test.java;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.junit.*;

import main.java.TranscribeAudio;
import main.java.writeFile;
import main.java.writeParameter;
public class TranscriptionTests {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    private static final String FILE_PATH = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/Test.m4a";

    @Test
    public void testTranscription() {
        try {
            String transcription = TranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/Test.m4a");
            assertEquals("This is a test.", transcription);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testTranscriptionSilence() {
        try {
            String transcription = TranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/Silence.m4a");
            assertEquals("", transcription);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testTranscriptionFileNotFound() {
        try {
            String transcription = TranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/Nonexistent.m4a");
            assertEquals("", transcription);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testSetupConnection() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            assertEquals("POST", connection.getRequestMethod());
            assertEquals(true, connection.getDoOutput());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testSetupRequestHeader() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            TranscribeAudio.setupRequestHeader(TOKEN, boundary, connection);
            assertEquals("multipart/form-data; boundary=" + boundary, connection.getRequestProperty("Content-Type"));
            assertNull(connection.getRequestProperty("Authorization"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testCloseRequestBody() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            OutputStream outputStream = connection.getOutputStream();
            TranscribeAudio.closeRequestBody(boundary, outputStream);
            assertEquals("\r\n--" + boundary + "--\r\n", outputStream.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testHandleResponseSuccess() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            String transcript = TranscribeAudio.handleResponse(HttpURLConnection.HTTP_OK, connection);
            assertEquals("", transcript);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testHandleResponseError() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            File file = new File("");
            String boundary = "Boundary-" + System.currentTimeMillis();
            OutputStream outputStream = connection.getOutputStream();
            writeFile.writeFileToOutputStream(outputStream, file, boundary);
            writeParameter.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
            String transcript = TranscribeAudio.handleResponse(HttpURLConnection.HTTP_BAD_GATEWAY, connection);
            assertEquals("", transcript);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testWriteFile() {
        try {
            File file = new File(FILE_PATH);
            String boundary = "Boundary-" + System.currentTimeMillis();
            String expectedOutputStream = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"" +
            file.getName() + "\"\r\nContent-Type: audio/mpeg\r\n\r\n";
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            OutputStream outputStream = connection.getOutputStream();
            writeFile.writeFileToOutputStream(outputStream, file, boundary);
            assertEquals(expectedOutputStream, outputStream.toString().substring(0, 120));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testWriteParameter() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection(API_ENDPOINT);
            String boundary = "Boundary-" + System.currentTimeMillis();
            String expectedOutputStream = "--" + boundary + 
            "\r\nContent-Disposition: form-data; name=\"model\"\r\n\r\n" + MODEL + "\r\n";
            OutputStream outputStream = connection.getOutputStream();
            writeParameter.writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
            assertEquals(expectedOutputStream, outputStream.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
