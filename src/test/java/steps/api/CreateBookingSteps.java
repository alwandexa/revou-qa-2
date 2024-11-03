package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookingSteps {
    private TestContext testContext;

    private RequestSpecification request;
    private Response response;
    private JSONObject bookingDetails;

    public CreateBookingSteps(TestContext testContext) {
        // Ensure testContext is initialized
        this.testContext = testContext;
    }

    @Given("I have the booking details")
    public void i_have_the_booking_details() {
        bookingDetails = new JSONObject();
        bookingDetails.put("firstname", "James");
        bookingDetails.put("lastname", "Brown");
        bookingDetails.put("totalprice", 111);
        bookingDetails.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");

        bookingDetails.put("bookingdates", bookingDates);
        bookingDetails.put("additionalneeds", "Lunch");

        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }

    @Given("I have the booking details with missing fields")
    public void i_have_the_booking_details_with_missing_fields() {
        bookingDetails = new JSONObject();
        bookingDetails.put("firstname", "James");
        // Missing lastname
        bookingDetails.put("totalprice", 111);
        bookingDetails.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");

        bookingDetails.put("bookingdates", bookingDates);
        bookingDetails.put("additionalneeds", "Lunch");

        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }

    @Given("I have the booking details with invalid data types")
    public void i_have_the_booking_details_with_invalid_data_types() {
        bookingDetails = new JSONObject();
        bookingDetails.put("firstname", "James");
        bookingDetails.put("lastname", "Brown");
        bookingDetails.put("totalprice", "one hundred eleven"); // Invalid data type
        bookingDetails.put("depositpaid", "true"); // Invalid data type

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-01-01");
        bookingDates.put("checkout", "2023-01-02");

        bookingDetails.put("bookingdates", bookingDates);
        bookingDetails.put("additionalneeds", "Lunch");

        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }

    @Then("the response body should contain the booking details")
    public void the_response_body_should_contain_the_booking_details() {
        testContext.getResponse().then().body("bookingid", notNullValue());
    }

    @Then("the response body should contain an error message")
    public void the_response_body_should_contain_an_error_message() {
        testContext.getResponse().then().body("error", containsString("Bad Request"));
    }
}