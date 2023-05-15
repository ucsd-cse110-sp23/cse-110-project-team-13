import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.Type;

public class MockRecorderTest {
    private MockRecording recording;
    private boolean running;
    private boolean active;
    
    @Before
    public void setUp() {
        recording = new MockRecording();
    }
    
    @Test
    public void MockTargetDataLineOpen() {
        AudioFormat audioFormat = recording.getAudioFormat();
        MockTargetDataLine input = new MockTargetDataLine();
        input.open(audioFormat);
        running = input.isRunning();
        active = input.isActive();
        assertTrue(running);
        assertFalse(active);
        input.start();
        running = input.isRunning();
        active = input.isActive();
        assertTrue(running);
        assertTrue(active); 
    }

    @Test
    public void MockTargetDataLineStart() {
        AudioFormat audioFormat = recording.getAudioFormat();
        MockTargetDataLine input = new MockTargetDataLine();
        input.open(audioFormat);
        input.start();
        running = input.isRunning();
        active = input.isActive();
        assertTrue(running);
        assertTrue(active);
    }
    
    @Test
    public void MockTargetDataLineClose() {
        AudioFormat audioFormat = recording.getAudioFormat();
        MockTargetDataLine input = new MockTargetDataLine();
        input.open(audioFormat);
        input.start();
        input.stop();
        running = input.isRunning();
        active = input.isActive();
        assertTrue(running);
        assertFalse(active);
    }
    
    @Test
    public void MockTargetDataLineStop() {
        AudioFormat audioFormat = recording.getAudioFormat();
        MockTargetDataLine input = new MockTargetDataLine();
        input.open(audioFormat);
        input.start();
        input.stop();
        input.close();
        running = input.isRunning();
        active = input.isActive();
        assertFalse(running);
        assertFalse(active);
    }
    
    @Test
    public void MockAudioInputStreamTest() {
        MockTargetDataLine input = new MockTargetDataLine();
        MockAudioInputStream stream = new MockAudioInputStream(input);
        MockTargetDataLine fetched = MockAudioInputStream.getMockTargetDataLine();
        assertNotNull(stream);
        assertNotNull(fetched);
        assertEquals(input,fetched);
    }
    
    @Test
    public void MockAudioSystem() {
        MockTargetDataLine input = new MockTargetDataLine();
        MockAudioInputStream stream = new MockAudioInputStream(input);
        MockTargetDataLine fetched = MockAudioInputStream.getMockTargetDataLine();
        File audioFile = new File("recording.wav");
        MockAudioSystem system = new MockAudioSystem();
        MockAudioSystem.write(stream, AudioFileFormat.Type.WAVE, audioFile);
        
        MockAudioInputStream systemStream = MockAudioSystem.getStream();
        AudioFileFormat.Type systemFileType = MockAudioSystem.getFileType();
        File systemFile = MockAudioSystem.getFile();

        assertNotNull(systemStream);
        assertNotNull(systemFileType);
        assertNotNull(systemFile);
        assertEquals(stream,systemStream);
        assertEquals(AudioFileFormat.Type.WAVE,systemFileType);
        assertEquals(audioFile,systemFile);
    }
    
    @Test
    public void testOpenMicrophone() {
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        MockTargetDataLine input = recording.getInput();
        running = input.isRunning();
        active = input.isActive();
        assertTrue(running);
        assertTrue(active);
        
        MockAudioSystem system = recording.getAudioSystem();
        String filePath = MockAudioSystem.getFile().getAbsolutePath();
        assertNotNull(system);
        assertNotNull(filePath);
        recording.closeMicrophone();
        
    }
    
    @Test
    public void testCloseMicrophone() {
        AudioFormat audioFormat = recording.getAudioFormat();
        recording.openMicrophone(audioFormat);
        MockTargetDataLine input = recording.getInput();
        recording.closeMicrophone();
        running = input.isRunning();
        active = input.isActive();
        assertFalse(running);
        assertFalse(active);
        assertNotNull(input);
    }   
}