package test.java;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import main.java.Recording;

import javax.sound.sampled.*;

public class RecorderTest {
    private Recording recording;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private int timeRecord;
    private File audioFile;
    private String path;
    
    public void testOpenMicrophone(AudioFormat audioFormat) {
        try {
          Timer timer = new Timer();
          // the format of the TargetDataLine
          DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);

          // the TargetDataLine used to capture audio data from the microphone
          targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
          targetDataLine.open(audioFormat);
          targetDataLine.start();

          // the AudioInputStream that will be used to write the audio data to a file
          AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

          // Timer function, used to test if input is written to file
          if(timeRecord > 0) {
              timer.schedule(new TimerTask() {
                  public void run() {
                      try {
                          audioFile = new File("recording.wav");
                          AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                          path = audioFile.getAbsolutePath();
                      } catch (Exception ex) {
                          ex.printStackTrace();
                      }
                  }
              }, 0, timeRecord);   
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }
    
    public void closeMicrophone() {
        targetDataLine.stop();
        targetDataLine.close();
      }
    
    @Before
    public void setUp() {
        recording = new Recording();
    }

    @Test
    public void record0Seconds() {
        timeRecord = -1;
        audioFormat = recording.getAudioFormat();
        testOpenMicrophone(audioFormat);
        closeMicrophone();
        assertNull(path);
    }
    
    @Test
    public void record5Seconds() {
        timeRecord = 5000;
        audioFormat = recording.getAudioFormat();
        testOpenMicrophone(audioFormat);
        closeMicrophone();
        assertNotNull(path);
    }
    
    @Test
    public void record60Seconds() {
        timeRecord = 60000;
        audioFormat = recording.getAudioFormat();
        testOpenMicrophone(audioFormat);
        closeMicrophone();
        assertNotNull(path);
    }
    
    @Test
    public void testCloseMicrophone() {
        audioFormat = recording.getAudioFormat();
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        closeMicrophone();
        assertFalse(targetDataLine.isOpen());
    }
}