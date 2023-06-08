import static org.junit.Assert.*;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;

public class MockVoiceCommands {
    static List<String> promptHistory = new ArrayList<String>();

    public static String voiceCommands(String filePath) throws IOException, InterruptedException{
        MockRecording recording = new MockRecording();
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        recording.closeMicrophone();

        String transcript = MockTranscribeAudio.transcribeAudio(filePath);

        if (transcript.length() >= 10 && transcript.substring(0, 8).toLowerCase().equals("question")) {
            newQuestion(transcript.substring(10));
            return "Question Asked: " + transcript.substring(10);
        }
        else if (transcript.toLowerCase().equals("delete prompt")) {
            remove();
            return "Prompt Deleted";
        }
        else if (transcript.toLowerCase().equals("clear all")) {
            clearAll();
            return "Prompts Cleared";
        }
        else{
            return "Sorry, I cannot understand you. Your message was:\n" + transcript;
        }
    }

    public static void newQuestion(String question) {
        promptHistory.add(question);
    }

    public static void remove() {
        promptHistory.remove(0);
    }

    public static void clearAll() {
        promptHistory.clear();
    }
}
