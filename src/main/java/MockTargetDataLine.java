package main.java;

import javax.sound.sampled.AudioFormat;

public class MockTargetDataLine  {
    private boolean running;
    private boolean active;
    private AudioFormat audioFormat;
    
    public MockTargetDataLine() {
        running = false;
        active = false;
    }
    
    public void open(AudioFormat format) {
        running = true;
        audioFormat = format;
    }

    public void close() {
        running = false;
    }

    public void start() {
        active = true;
    }

    public void stop() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }
    
    public boolean isRunning() {
        return running;
    }
}
