package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import static org.hamcrest.Matchers.*;

public class UpdateBookingSteps {

    private TestContext testContext;
    private RequestSpecification request;
    private Response response;
    private String bookingId = "1"; // Example booking ID
    private String invalidBookingId = "9999"; // Example invalid booking ID

    public UpdateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have a valid booking ID")
    public void i_have_a_valid_booking_id() {
        // Assuming the booking ID is valid and exists
        testContext.setBookingId(bookingId);
    }

    @Given("I have an invalid booking ID")
    public void i_have_an_invalid_booking_id() {
        // Assuming the booking ID is invalid and does not exist
        testContext.setBookingId(invalidBookingId);
    }

    @When("I update the booking with new valid details")
    public void i_update_the_booking_with_new_valid_details() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers
        request.header("Content-Type", "application/json");
        request.header("Cookie", "token=abc123");

        // Set body using org.json
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");

        // Send PUT request
        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with an invalid token")
    public void i_update_the_booking_with_an_invalid_token() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers
        request.header("Content-Type", "application/json");
        request.header("Cookie", "token=invalidtoken");

        // Set body using org.json
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");

        // Send PUT request
        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with new details")
    public void i_update_the_booking_with_new_details() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers
        request.header("Content-Type", "application/json");
        request.header("Cookie", "token=abc123");

        // Set body using org.json
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");

        // Send PUT request
        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @Then("the booking should be updated successfully")
    public void the_booking_should_be_updated_successfully() {
        // Verify the response
        testContext.getResponse().then().statusCode(200);
        testContext.getResponse().then().body("firstname", equalTo("James"));
        testContext.getResponse().then().body("lastname", equalTo("Brown"));
        testContext.getResponse().then().body("totalprice", equalTo(111));
        testContext.getResponse().then().body("depositpaid", equalTo(true));
        testContext.getResponse().then().body("bookingdates.checkin", equalTo("2023-01-01"));
        testContext.getResponse().then().body("bookingdates.checkout", equalTo("2023-01-02"));
        testContext.getResponse().then().body("additionalneeds", equalTo("Lunch"));
    }

    @Then("the booking update should fail with unauthorized error")
    public void the_booking_update_should_fail_with_unauthorized_error() {
        // Verify the response
        testContext.getResponse().then().statusCode(403);
    }

    @Then("the booking update should fail with not found error")
    public void the_booking_update_should_fail_with_not_found_error() {
        // Verify the response
        testContext.getResponse().then().statusCode(404);
    }
}