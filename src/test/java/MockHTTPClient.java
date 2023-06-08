
public class MockHTTPClient {
    public MockHTTPClient() {}
    
    public MockHTTPResponse send(String method, String typeResponse) {
        return new MockHTTPResponse();
    }
}
