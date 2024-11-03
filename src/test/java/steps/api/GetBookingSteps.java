package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingSteps {
    private RequestSpecification request;
    private TestContext testContext;

    public GetBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have access to the booking service")
    public void i_have_access_to_the_booking_service() {
        request = RestAssured.given().baseUri("https://restful-booker.herokuapp.com");
        testContext.setRequest(request);
    }

    @When("I send a GET request to the {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        Response response = testContext.getRequest().when().get(endpoint);
        testContext.setResponse(response);  // Save response in TestContext
    }

    @When("I send a GET request to an incorrect endpoint {string}")
    public void i_send_a_get_request_to_an_incorrect_endpoint(String endpoint) {
        Response response = testContext.getRequest().when().get(endpoint);
        testContext.setResponse(response);
    }

    @Then("I should receive a list of bookings")
    public void i_should_receive_list_of_bookings() {
        testContext.getResponse()
                .then()
                .statusCode(200)  // Ensure status code is 200
                .body("bookingid", not(empty()))  // Ensure the list is not empty
                .body("bookingid", everyItem(greaterThan(0)));  // Ensure all booking IDs are positive integers
    }

    @Then("I should not receive any bookings")
    public void i_should_not_receive_any_bookings() {
        testContext.getResponse().then().statusCode(404);
    }
}
