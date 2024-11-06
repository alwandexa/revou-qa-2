package steps.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private Response response;
    private RequestSpecification request;

    private String bookingId;

    private Map<String, Object> additionalData = new HashMap<>();

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setAdditionalData(String key, Object value) {
        additionalData.put(key, value);
    }

    public Object getAdditionalData(String key) {
        return additionalData.get(key);
    }
}
