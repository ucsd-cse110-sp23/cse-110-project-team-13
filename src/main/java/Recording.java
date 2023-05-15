import java.io.*;

import javax.sound.sampled.*;

public class Recording {
    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat;

    public Recording(){
      audioFormat = getAudioFormat();
      DataLine.Info dataLineInfo = new DataLine.Info(
        TargetDataLine.class,
        audioFormat
      );
      try {
        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
      }
      catch (Exception ex){
        ex.printStackTrace();
      }
    }

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
    
    public void openMicrophone() {
      Thread t = new Thread(
      new Runnable() {
        @Override
        public void run() {
          try {
            // the format of the TargetDataLine
            // the TargetDataLine used to capture audio data from the microphone
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            // the AudioInputStream that will be used to write the audio data to a file
            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

            // the file that will contain the audio data
            File audioFile = new File("recording.wav");
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
            
            String path = audioFile.getAbsolutePath();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        });
        t.start();
      }
      
      public void closeMicrophone() {
        targetDataLine.stop();
        targetDataLine.close();
      }
} 


