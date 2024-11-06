package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthSteps {
    private RequestSpecification request;
    private TestContext testContext;

    // PicoContainer will inject the TestContext object
    public AuthSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have credentials with SQL injection patterns")
    public void i_have_credentials_with_sql_injection_patterns() {
        request = given().contentType("application/json").body("{ \"username\": \"admin' OR '1'='1\", \"password\": \"password123\" }");
        testContext.setRequest(request);
    }
    
    @Given("I have credentials with only whitespace")
    public void i_have_credentials_with_only_whitespace() {
        request = given().contentType("application/json").body("{ \"username\": \" \", \"password\": \" \", }");
        testContext.setRequest(request);
    }

    @Given("I have missing username and password")
    public void i_have_missing_username_and_password() {
        request = given().contentType("application/json").body("{}");
        testContext.setRequest(request);
    }

    @Given("I have valid user credentials")
    public void i_have_valid_user_credentials() {
        // Set up the request with valid user credentials
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }");
        testContext.setRequest(request);
    }

    @Given("I have invalid user credentials")
    public void i_have_invalid_user_credentials() {
        // Set up the request with invalid user credentials
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"invalidUser\", \"password\": \"wrongPassword\" }");
        testContext.setRequest(request);
    }

    @Given("I have valid username but missing password")
    public void i_have_valid_username_but_missing_password() {
        // Set up the request with valid username but no password
        request = given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\" }");
        testContext.setRequest(request);
    }

    @Given("I have valid password but missing username")
    public void i_have_valid_password_but_missing_username() {
        // Set up the request with valid password but no username
        request = given()
                .contentType("application/json")
                .body("{ \"password\": \"password123\" }");
        testContext.setRequest(request);
    }

    @When("I send a POST request to the {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        // Send the POST request to the specified endpoint
        Response response = testContext.getRequest().post("https://restful-booker.herokuapp.com" + endpoint);
        testContext.setResponse(response); // Store the response in TestContext
    }

    @Then("I should receive a token in the response")
    public void i_should_receive_token_in_response() {
        // Verify that the response contains a non-null token
        testContext.getResponse()
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Then("I should not receive a token in the response")
    public void i_should_not_receive_token_in_response() {
        // Verify that the response does not contain a token
        testContext.getResponse()
                .then()
                .body("token", nullValue());
    }

}
