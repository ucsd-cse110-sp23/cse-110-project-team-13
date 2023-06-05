import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import org.junit.Before;
import org.junit.Test;

public class VoiceCommandTests {
    MockRecording recording;
    List<String> promptHistory;
    
    @Before
    public void setUp() {
        recording = new MockRecording();
        promptHistory = new ArrayList<String>();
    }

    @Test
    public void newQuestion() {
        //Mock the recording
        String transcript = null;
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        
        //Mock the transcription
        filePath = "src/test/java/TestFiles/MockQuestion.txt";
        try {
            transcript = MockTranscribeAudio.transcribeAudio(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        //Mock the Voice Command
        String command = transcript.substring(0,9);
        String query = transcript.substring(9).strip();
        assertEquals("question.", command);
        assertEquals("what color is the sky?",query);
        promptHistory.add(query);
        boolean addedToHistory = promptHistory.contains("what color is the sky?");
        assertTrue(addedToHistory);
    }
    
    @Test
    public void deletePrompt() {
        //Mock the recording
        String commandTranscript = null;
        String questionTranscript = null;
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        
        //Mock the transcription, first the question then the voice command
        String questionPath = "src/test/java/TestFiles/MockQuestion.txt";
        String commandPath = "src/test/java/TestFiles/MockDeletePrompt.txt";
        try {
            questionTranscript = MockTranscribeAudio.transcribeAudio(questionPath);
            commandTranscript = MockTranscribeAudio.transcribeAudio(commandPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        //Mock Delete Command, first populate prompt history
        assertEquals("question. what color is the sky?", questionTranscript);
        assertEquals("delete prompt", commandTranscript);
        String query = questionTranscript.substring(9).strip();
        promptHistory.add(query);
        boolean removed = promptHistory.remove(query);
        assertTrue(removed);
    }
    
    @Test
    public void clearAll() {
        //Mock the recording
        String commandTranscript = null;
        String questionTranscript = null;
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        
        //Mock the transcription, first the question then the voice command
        String questionPath = "src/test/java/TestFiles/MockQuestion.txt";
        String commandPath = "src/test/java/TestFiles/MockClear.txt";
        try {
            questionTranscript = MockTranscribeAudio.transcribeAudio(questionPath);
            commandTranscript = MockTranscribeAudio.transcribeAudio(commandPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        //Mock Clear All Command, first populate prompt history
        assertEquals("question. what color is the sky?", questionTranscript);
        assertEquals("clear all", commandTranscript);
        String query = questionTranscript.substring(9).strip();
        promptHistory.add(query);
        promptHistory.add(query);
        promptHistory.add(query);
        assertEquals(promptHistory.size(),3);
        promptHistory.clear();
        
        boolean empty = promptHistory.isEmpty();
        assertTrue(empty);
    }
}