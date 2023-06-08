
public class MockHTTPResponse {
    public MockHTTPResponse() {}
    
    public String getBody(String prompt) {
        StringBuilder reversed = new StringBuilder();
        
        for (int i = prompt.length() - 1; i >= 0; i--) {
            reversed.append(prompt.charAt(i));
        }
        
        return reversed.toString();
    }
}
