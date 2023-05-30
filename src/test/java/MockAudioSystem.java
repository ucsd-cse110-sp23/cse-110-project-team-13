import javax.sound.sampled.AudioFileFormat;
import java.io.File;

public class MockAudioSystem {
    private static MockAudioInputStream myStream;
    private static AudioFileFormat.Type myFileType;
    private static File myFile;
    
    public static void write(MockAudioInputStream stream, AudioFileFormat.Type fileType, File file) {
        myStream = stream;
        myFileType = fileType;
        myFile = file;
    }
    
    public static MockAudioInputStream getStream() {
        return myStream;
    }
    
    public static AudioFileFormat.Type getFileType() {
        return myFileType;
    }
    
    public static File getFile() {
        return myFile;
    }
}
