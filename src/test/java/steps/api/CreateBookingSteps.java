package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;

public class CreateBookingSteps {
    private TestContext testContext;
    private RequestSpecification request;
    private Response response;
    private JSONObject bookingDetails;

    public CreateBookingSteps(TestContext testContext) {
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

    @Given("I have booking details with future check-in and check-out dates")
    public void i_have_booking_details_with_future_dates() {
        LocalDate futureCheckin = LocalDate.now().plusDays(30);
        LocalDate futureCheckout = LocalDate.now().plusDays(35);
        
        bookingDetails = createBaseBookingDetails();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", futureCheckin.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", futureCheckout.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with past dates")
    public void i_have_booking_details_with_past_dates() {
        LocalDate pastCheckin = LocalDate.now().minusDays(10);
        LocalDate pastCheckout = LocalDate.now().minusDays(5);
        
        bookingDetails = createBaseBookingDetails();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", pastCheckin.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", pastCheckout.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have booking details where check-out is before check-in")
    public void i_have_booking_details_with_invalid_date_range() {
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = LocalDate.now().plusDays(3);
        
        bookingDetails = createBaseBookingDetails();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", checkout.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with same day check-in and check-out")
    public void i_have_booking_details_with_same_day() {
        LocalDate sameDay = LocalDate.now().plusDays(1);
        
        bookingDetails = createBaseBookingDetails();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", sameDay.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", sameDay.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with maximum allowed stay duration")
    public void i_have_booking_details_with_maximum_stay() {
        LocalDate checkin = LocalDate.now().plusDays(1);
        LocalDate checkout = LocalDate.now().plusDays(31); // Assuming 30 days is max
        
        bookingDetails = createBaseBookingDetails();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", checkout.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with special requests")
    public void i_have_booking_details_with_special_requests() {
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("additionalneeds", "Late check-in, Extra pillows, Airport shuttle");
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with invalid room type")
    public void i_have_booking_details_with_invalid_room_type() {
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("roomtype", "NonExistentRoomType");
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with maximum allowed guests")
    public void i_have_booking_details_with_maximum_guests() {
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("numofguests", 4); // Assuming 4 is maximum
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with excessive number of guests")
    public void i_have_booking_details_with_excessive_guests() {
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("numofguests", 10); // More than maximum allowed
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with special characters in guest name")
    public void i_have_booking_details_with_special_characters() {
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("firstname", "Jöhn-Påul");
        bookingDetails.put("lastname", "O'Connor-Müller");
        setRequestWithBookingDetails();
    }

    @Given("I have booking details with multiple nights stay")
    public void i_have_booking_details_with_multiple_nights() {
        LocalDate checkin = LocalDate.now().plusDays(1);
        LocalDate checkout = LocalDate.now().plusDays(5);
        
        bookingDetails = createBaseBookingDetails();
        bookingDetails.put("totalprice", 500);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin.format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", checkout.format(DateTimeFormatter.ISO_DATE));
        bookingDetails.put("bookingdates", bookingDates);
        
        setRequestWithBookingDetails();
    }

    @Given("I have valid booking details but no auth token")
    public void i_have_booking_details_without_auth_token() {
        bookingDetails = createBaseBookingDetails();
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        // Intentionally not setting auth token
        testContext.setRequest(request);
    }

    @Then("the response should include the special requests")
    public void the_response_should_include_special_requests() {
        testContext.getResponse().then()
                .body("booking.additionalneeds", containsString("Late check-in"))
                .body("booking.additionalneeds", containsString("Extra pillows"))
                .body("booking.additionalneeds", containsString("Airport shuttle"));
    }

    @Then("the response body should contain the correct total price")
    public void the_response_should_contain_correct_total_price() {
        testContext.getResponse().then()
                .body("booking.totalprice", equalTo(500));
    }

    @Given("I have valid booking details but no Basic Auth credentials")
    public void i_have_booking_details_without_basic_auth() {
        bookingDetails = createBaseBookingDetails();
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        // Intentionally not setting Basic Auth
        testContext.setRequest(request);
    }

    @Given("I have invalid Basic Auth credentials")
    public void i_have_invalid_basic_auth_credentials() {
        bookingDetails = createBaseBookingDetails();
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .basic("wrongusername", "wrongpassword")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }

    @Given("I have malformed Basic Auth header")
    public void i_have_malformed_basic_auth_header() {
        bookingDetails = createBaseBookingDetails();
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic InvalidBase64String")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }

    @Then("the response body should contain an authentication error message")
    public void the_response_should_contain_auth_error() {
        testContext.getResponse().then()
                .body("reason", containsString("Bad credentials"))
                .body("$", hasKey("message"));
    }

    // Helper methods
    private JSONObject createBaseBookingDetails() {
        JSONObject details = new JSONObject();
        details.put("firstname", "James");
        details.put("lastname", "Brown");
        details.put("totalprice", 111);
        details.put("depositpaid", true);
        
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE));
        bookingDates.put("checkout", LocalDate.now().plusDays(3).format(DateTimeFormatter.ISO_DATE));
        details.put("bookingdates", bookingDates);
        
        return details;
    }

    private void setRequestWithBookingDetails() {
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bookingDetails.toString());
        testContext.setRequest(request);
    }
}