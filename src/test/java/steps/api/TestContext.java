package steps.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
    private Response response;
    private RequestSpecification request;

    private String bookingId;

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
}
