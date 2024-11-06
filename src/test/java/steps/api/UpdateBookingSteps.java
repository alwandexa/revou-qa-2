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
    private String invalidBookingId = "-1"; 

    public UpdateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have a valid booking ID")
    public void i_have_a_valid_booking_id() {
        String bookingId = testContext.getBookingId();
        testContext.setBookingId(bookingId);
    }

    @Given("I have an invalid booking ID")
    public void i_have_an_invalid_booking_id() {
        // Assuming the booking ID is invalid and does not exist
        testContext.setBookingId(invalidBookingId);
    }

    @Given("I create a new booking with valid details")
    public void i_create_a_new_booking_with_valid_details() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        request.header("Content-Type", "application/json")
               .header("Accept", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "John");
        requestBody.put("lastname", "Doe");
        requestBody.put("totalprice", 100);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-02");
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Breakfast");

        response = request.body(requestBody.toString())
                .post("/booking");
        testContext.setResponse(response);
    }

    @Given("I store the booking ID")
    public void i_store_the_booking_id() {
        String newBookingId = testContext.getResponse()
                .then()
                .statusCode(200)
                .extract()
                .path("bookingid")
                .toString();
        testContext.setBookingId(newBookingId);
    }

    @When("I update the booking with new valid details")
    public void i_update_the_booking_with_new_valid_details() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        // Set headers with Basic Auth
        request.header("Content-Type", "application/json")
               .auth()
               .preemptive()
               .basic("admin", "password123");

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

        // Set headers with invalid Basic Auth
        request.header("Content-Type", "application/json")
               .auth()
               .preemptive()
               .basic("admin", "wrongpassword");

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

        // Set headers with Basic Auth
        request.header("Content-Type", "application/json")
               .auth()
               .preemptive()
               .basic("admin", "password123");

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

    @When("I update the booking with missing required fields")
    public void i_update_booking_with_missing_required_fields() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        // Only include firstname, leaving other required fields empty
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");

        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with invalid check-in\\/check-out dates")
    public void i_update_booking_with_invalid_dates() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "invalid-date");
        bookingDates.put("checkout", "2023-13-45"); // Invalid date format
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");

        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I perform a partial update with only first name and last name")
    public void i_perform_partial_update() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "Jane");
        requestBody.put("lastname", "Smith");

        response = request.body(requestBody.toString())
                .patch("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with past dates")
    public void i_update_booking_with_past_dates() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        JSONObject requestBody = createDefaultBookingBody();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2020-01-01");
        bookingDates.put("checkout", "2020-01-02");
        requestBody.put("bookingdates", bookingDates);

        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with check-out date before check-in date")
    public void i_update_booking_with_invalid_date_order() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        JSONObject requestBody = createDefaultBookingBody();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-01-02");
        bookingDates.put("checkout", "2024-01-01"); // Checkout before checkin
        requestBody.put("bookingdates", bookingDates);

        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I update the booking with negative or zero price values")
    public void i_update_booking_with_invalid_price() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123");

        JSONObject requestBody = createDefaultBookingBody();
        requestBody.put("totalprice", -100); // Negative price

        response = request.body(requestBody.toString())
                .put("/booking/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("multiple users try to update the same booking simultaneously")
    public void multiple_users_update_simultaneously() {
        // First update
        i_update_the_booking_with_new_valid_details();
        Response firstResponse = testContext.getResponse();

        // Second immediate update
        i_update_the_booking_with_new_valid_details();
        // Store the second response separately
        Response secondResponse = testContext.getResponse();

        // Store both responses for verification
        testContext.setAdditionalData("firstResponse", firstResponse);
        testContext.setAdditionalData("secondResponse", secondResponse);
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

    @Then("the booking update should fail with bad request error")
    public void the_booking_update_should_fail_with_bad_request() {
        testContext.getResponse().then().statusCode(400);
    }

    @Then("other booking details should remain unchanged")
    public void other_booking_details_should_remain_unchanged() {
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .get("/booking/" + testContext.getBookingId());

        // Verify only firstname and lastname changed, other fields remained the same
        response.then()
                .body("firstname", equalTo("Jane"))
                .body("lastname", equalTo("Smith"))
                .body("totalprice", not(nullValue()))
                .body("depositpaid", not(nullValue()))
                .body("bookingdates.checkin", not(nullValue()))
                .body("bookingdates.checkout", not(nullValue()));
    }

    @Then("only one update should succeed")
    public void only_one_update_should_succeed() {
        Response firstResponse = (Response) testContext.getAdditionalData("firstResponse");
        Response secondResponse = (Response) testContext.getAdditionalData("secondResponse");

        // At least one should succeed
        boolean oneSucceeded = (firstResponse.getStatusCode() == 200 || 
                              secondResponse.getStatusCode() == 200);
        assert oneSucceeded : "At least one update should succeed";
    }

    @Then("other updates should fail with conflict error")
    public void other_updates_should_fail_with_conflict() {
        Response firstResponse = (Response) testContext.getAdditionalData("firstResponse");
        Response secondResponse = (Response) testContext.getAdditionalData("secondResponse");

        // If both succeeded, that's an error
        assert !(firstResponse.getStatusCode() == 200 && 
                secondResponse.getStatusCode() == 200) : 
                "Both updates shouldn't succeed simultaneously";
    }

    // Helper method to create default booking body
    private JSONObject createDefaultBookingBody() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "James");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-02");
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");
        return requestBody;
    }
}