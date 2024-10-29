package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestfulBookerSteps {
    private RequestSpecification request;
    private Response response;

    @Given("I have valid user credentials")
    public void i_have_valid_user_credentials() {
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }");
    }

    @Given("I have invalid user credentials")
    public void i_have_invalid_user_credentials() {
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"invalidUser\", \"password\": \"wrongPassword\" }");
    }

    @Given("I have valid username but missing password")
    public void i_have_valid_username_but_missing_password() {
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\" }");
    }

    @Given("I have valid password but missing username")
    public void i_have_valid_password_but_missing_username() {
        request = given()
                .contentType("application/json")
                .body("{ \"password\": \"password123\" }");
    }

    @When("I send a POST request to the {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = request.post("https://restful-booker.herokuapp.com" + endpoint);
    }

    @Then("I should receive a token in the response")
    public void i_should_receive_token_in_response() {
        response.then().statusCode(200).body("token", notNullValue());
    }

    @Then("I should not receive a token in the response")
    public void i_should_not_receive_token_in_response() {
        response.then().body("token", nullValue());
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        response.then().statusCode(statusCode);
    }
}
