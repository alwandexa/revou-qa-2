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
