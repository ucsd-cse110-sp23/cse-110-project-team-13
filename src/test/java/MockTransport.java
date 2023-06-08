public class MockTransport {
    MockMimeMessage msg;

    public void send(MockMimeMessage msg) {
        this.msg = msg;
    }

    public MockMimeMessage getMessage() {
        return this.msg;
    }
}
