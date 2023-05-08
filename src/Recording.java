import java.io.*;

import javax.sound.sampled.*;

public class Recording {
    private TargetDataLine targetDataLine;

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
    
    public String openMicrophone(AudioFormat audioFormat) {
        try {
          // the format of the TargetDataLine
          DataLine.Info dataLineInfo = new DataLine.Info(
            TargetDataLine.class,
            audioFormat
          );
          // the TargetDataLine used to capture audio data from the microphone
          targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
          targetDataLine.open(audioFormat);
          targetDataLine.start();

          // the AudioInputStream that will be used to write the audio data to a file
          AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

          // the file that will contain the audio data
          File audioFile = new File("recording.wav");
          AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
          
          String path = audioFile.getAbsolutePath();
          return path;
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        return null;  
      }
      
      public void closeMicrophone() {
        targetDataLine.stop();
        targetDataLine.close();
      }
} 


