import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.*;
public class TranscriptionTests {

    @Test
    public void testTranscription() {
        try {
            String transcription = TranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/TestFiles/Test.m4a");
            assertEquals("This is a test.", transcription);
        } catch (IOException exception) {
        }
    }

    @Test
    public void testTranscriptionSilence() {
        try {
            String transcription = TranscribeAudio.transcribeAudio(
            "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/TestFiles/Silence.m4a");
            assertEquals("", transcription);
        } catch (IOException exception) {
        }
    }

    @Test
    public void testSetupConnection() {
        try {
            HttpURLConnection connection = TranscribeAudio.setupConnection("https://api.openai.com/v1/audio/transcriptions");
            assertNotNull(connection);
        } catch (IOException exception) {
        }
    }
}
