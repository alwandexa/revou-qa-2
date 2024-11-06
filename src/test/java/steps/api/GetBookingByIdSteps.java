package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingByIdSteps {
    private TestContext testContext;

    // PicoContainer will inject TestContext to share data between steps
    public GetBookingByIdSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have access to the booking service")
    public void i_have_access_to_the_booking_service() {
        RequestSpecification request = RestAssured.given();
        testContext.setRequest(request);
    }

    @Given("I am authenticated with valid basic auth credentials")
    public void i_am_authenticated_with_valid_basic_auth_credentials() {
        RequestSpecification request = testContext.getRequest()
            .auth()
            .basic("admin", "password123"); // Replace with actual credentials
        testContext.setRequest(request);
    }

    @Given("I am authenticated with invalid basic auth credentials")
    public void i_am_authenticated_with_invalid_basic_auth_credentials() {
        RequestSpecification request = testContext.getRequest()
            .auth()
            .basic("invalid", "wrongpassword");
        testContext.setRequest(request);
    }

    @Given("I do not provide any authentication credentials")
    public void i_do_not_provide_any_authentication_credentials() {
        // No authentication needed - using the default request specification
        RequestSpecification request = RestAssured.given();
        testContext.setRequest(request);
    }

    @When("I send a GET request to the {string} endpoint with booking ID {int}")
    public void i_send_get_request_to_endpoint_with_booking_id(String endpoint, int id) {
        // Send GET request to the specified endpoint with the booking ID
        Response response = testContext.getRequest().when().get(endpoint + "/" + id);
        testContext.setResponse(response); // Store the response in TestContext
    }

    @When("I send a GET request to the {string} endpoint with non-existing booking ID {int}")
    public void i_send_get_request_to_endpoint_with_non_existing_booking_id(String endpoint, int id) {
        // Send GET request to the specified endpoint with the booking ID
        Response response = testContext.getRequest().when().get(endpoint + "/" + id);
        testContext.setResponse(response); // Store the response in TestContext
    }

    @Then("I should receive the booking details for booking ID {int}")
    public void i_should_receive_the_booking_details(int bookingId) {
        // Validate the response contains booking details (e.g., firstname and lastname)
        testContext.getResponse().then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("lastname", notNullValue());
    }

    @Then("I should not receive any booking details")
    public void i_should_not_receive_any_booking_details() {
        // Validate the response has a 404 status for invalid booking ID
        testContext.getResponse().then()
                .statusCode(404)
                .body(equalTo("Not Found")); // Assuming API returns "Not Found" for invalid ID
    }
}
