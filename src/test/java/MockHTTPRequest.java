import org.json.JSONObject;

public class MockHTTPRequest {
    private String uri;
    private String method;
    private String contentType;
    private String authorization;
    private JSONObject requestBody;

    public MockHTTPRequest() {

    }

    public void setURI(String uri) {
        this.uri = uri;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setRequestBody(JSONObject requestBody) {
        this.requestBody = requestBody;
    }

    public String execute() {
        // Simulate the behavior of the HttpRequest by printing the details
        StringBuilder result = new StringBuilder();
        result.append("URI: ").append(uri).append("\n");
        result.append("Method: ").append(method).append("\n");
        result.append("Content-Type: ").append(contentType).append("\n");
        result.append("Authorization: ").append(authorization).append("\n");
        result.append("Request Body: ").append(requestBody).append("\n");
        return result.toString();
    }
}
