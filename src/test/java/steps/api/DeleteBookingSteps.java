package steps.api;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteBookingSteps {

    private TestContext testContext;
    private RequestSpecification request;
    private Response response;

    public DeleteBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I send a DELETE request to the {string} endpoint")
    public void i_send_a_delete_request_to_the_booking_id_endpoint(String endpoint) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers
        request.header("Content-Type", "application/json");
        request.header("Cookie", "token=abc123");

        // Send DELETE request
        response = request.delete(endpoint + "/"+ testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I send a DELETE request to the {string} endpoint with an invalid token")
    public void i_send_a_delete_request_to_the_booking_id_endpoint_with_an_invalid_token(String endpoint) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers
        request.header("Content-Type", "application/json");
        request.header("Cookie", "token=invalidtoken");

        // Send DELETE request
        response = request.delete(endpoint + "/"+ testContext.getBookingId());
        testContext.setResponse(response);
    }

    @Then("the booking should be deleted successfully")
    public void the_booking_should_be_deleted_successfully() {
        // Verify the response
        testContext.getResponse().then().statusCode(201);
    }
}