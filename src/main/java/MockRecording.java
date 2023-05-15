package main.java;

import java.io.*;
import javax.sound.sampled.*;

public class MockRecording {
       
    private MockTargetDataLine targetDataLine;
    private MockAudioSystem audioSystem;
    private File audioFile;
    
    public AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = false;
    
        return new AudioFormat(
          sampleRate,
          sampleSizeInBits,
          channels,
          signed,
          bigEndian
        );
      }
    
    public void openMicrophone(AudioFormat audioFormat) {
        try {
          // the TargetDataLine used to capture audio data from the microphone
          targetDataLine = new MockTargetDataLine();
          targetDataLine.open(audioFormat);
          targetDataLine.start();

          // the AudioInputStream that will be used to write the audio data to a file
          MockAudioInputStream audioInputStream = new MockAudioInputStream(targetDataLine);

          // the file that will contain the audio data
          audioFile = new File("src/recording.wav");
          audioSystem = new MockAudioSystem();
          MockAudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);          
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }
    
    public void closeMicrophone() {
        targetDataLine.stop();
        targetDataLine.close();
        try {
            if (audioFile.exists()) {
                audioFile.delete();
            }
            audioFile.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public MockTargetDataLine getInput() {
        return targetDataLine;
    }
    
    public MockAudioSystem getAudioSystem() {
        return audioSystem;
    }
}
