package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

public class DeleteBookingSteps {

    private TestContext testContext;
    private RequestSpecification request;
    private Response response;

    public DeleteBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have a previously deleted booking ID")
    public void i_have_a_previously_deleted_booking_id() {
        // Create booking request body using org.json
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-02");

        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", "Test");
        requestBody.put("lastname", "User");
        requestBody.put("totalprice", 100);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Breakfast");

        // Create a booking and get its ID
        Response createResponse = RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .contentType("application/json")
            .body(requestBody.toString())
            .post("/booking");
        
        String bookingId = createResponse.jsonPath().getString("bookingid");
        testContext.setBookingId(bookingId);

        // Delete the booking
        RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .auth().basic("admin", "password123")
            .delete("/booking/" + bookingId);
        
        // The bookingId is now stored in testContext and represents a deleted booking
    }

    @When("I send a DELETE request to the {string} endpoint")
    public void i_send_a_delete_request_to_the_endpoint(String endpoint) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
            .auth().basic("admin", "password123");

        response = request.delete(endpoint + "/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I send a DELETE request to the {string} endpoint with invalid basic auth credentials")
    public void i_send_a_delete_request_with_invalid_credentials(String endpoint) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given()
            .auth().basic("admin", "wrongpassword");

        response = request.delete(endpoint + "/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("I send a DELETE request to the {string} endpoint without basic auth")
    public void i_send_a_delete_request_without_auth(String endpoint) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        request = RestAssured.given();

        response = request.delete(endpoint + "/" + testContext.getBookingId());
        testContext.setResponse(response);
    }

    @Then("the booking should be deleted successfully")
    public void the_booking_should_be_deleted_successfully() {
        // Verify the booking no longer exists by sending a GET request
        Response verifyResponse = RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .get("/booking/" + testContext.getBookingId());
        
        Assert.assertEquals(404, verifyResponse.getStatusCode(),
            "Booking should not exist after deletion");
    }
}