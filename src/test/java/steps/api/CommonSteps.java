package steps.api;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class CommonSteps {
    private TestContext testContext;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        Response response = testContext.getResponse();  // Get the response from TestContext
        response.then().statusCode(statusCode);
    }
}
