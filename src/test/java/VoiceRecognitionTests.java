package test.java;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

import main.java.*;
public class VoiceRecognitionTests {
    MockRecording recording;

    @Before
    public void setUp() {
        recording = new MockRecording();
    }

    @Test
    public void testVoiceRecognition() {
        String transcript = null;
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        filePath = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/TestMock.txt";
        try {
            transcript = MockTranscribeAudio.transcribeAudio(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("This is a test.", transcript);
    }

    @Test
    public void testVoiceRecognitionSilence() {
        String transcript = null;
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        filePath = "/Users/Daniel/Documents/CSE 110/cse-110-project-team-13/src/test/java/TestFiles/SilenceMock.txt";
        try {
            transcript = MockTranscribeAudio.transcribeAudio(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertEquals("", transcript);
    }
}
