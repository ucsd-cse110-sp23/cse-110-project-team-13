import java.util.HashMap;

public class MockAuthenticator {
    private HashMap<String,String> emailCredentials;

    public MockAuthenticator() {
        emailCredentials = new HashMap<String,String>();
    }
    public void addCredentials(String email, String password) {
        emailCredentials.put(email,password);
    }
    protected boolean PasswordAuthenticator(String fromEmail, String password) {
        if(emailCredentials.get(fromEmail) == password) {
            return true;
        }
        else {
            return false;
        }
    }
}
