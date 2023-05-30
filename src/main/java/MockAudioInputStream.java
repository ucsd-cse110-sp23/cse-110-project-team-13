import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.sound.sampled.TargetDataLine;

public class MockAudioInputStream {
    private static MockTargetDataLine input;
    private static TargetDataLine targetDataLine;
    
    public MockAudioInputStream(MockTargetDataLine mockTargetDataLine) {
        input = mockTargetDataLine;
    }
    public static MockTargetDataLine getMockTargetDataLine() {
        return input;
    }
}
