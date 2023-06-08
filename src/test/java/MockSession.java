import java.util.Properties;

public class MockSession {
    private Properties properties;
    private MockAuthenticator authenticator;

    public MockSession(Properties properties, MockAuthenticator auth) {
        this.properties = properties;
        this.authenticator = auth;
    }

    public Properties getProperties() {
        return properties;
    }

    public MockAuthenticator getMockAuthenticator() {
        return authenticator;
    }
}