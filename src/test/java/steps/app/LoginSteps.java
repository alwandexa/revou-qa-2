package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LoginSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I am on the login screen")
    public void i_am_on_the_login_screen() {
        try {
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.ViewGroup[@content-desc='open menu']/android.widget.ImageView")));
            menuButton.click();
            WebElement loginMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.ViewGroup[@content-desc='menu item log in']")));
            loginMenuItem.click();
            WebElement loginScreen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='login screen']")));
            Assert.assertTrue(loginScreen.isDisplayed(), "User is not on the login screen");
        } catch (StaleElementReferenceException e) {
            // Retry accessing the login screen if a StaleElementReferenceException occurs
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.ViewGroup[@content-desc='open menu']/android.widget.ImageView")));
            menuButton.click();
            WebElement loginMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.ViewGroup[@content-desc='menu item log in']")));
            loginMenuItem.click();
        }
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@content-desc='Username input field']")));
        usernameField.clear(); // Clear field before entering text to prevent any residual input issues
        usernameField.sendKeys(username);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@content-desc='Password input field']")));
        passwordField.clear(); // Clear field before entering text
        passwordField.sendKeys(password);
    }

    @When("I tap on the login button")
    public void i_tap_on_the_login_button() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.ViewGroup[@content-desc='Login button']")));
        loginButton.click();
    }

    @Then("I should be successfully logged in")
    public void i_should_be_successfully_logged_in() {
        WebElement mainScreen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.rn:id/action_bar_root")));
        Assert.assertTrue(mainScreen.isDisplayed(), "User was not logged in");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='generic-error-message']")));
        String actualMessage = errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Error message did not match");
    }

    @Then("I should see a username error message {string}")
    public void i_should_see_a_username_error_message(String expectedMessage) {
        WebElement usernameErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='Username-error-message']")));
        String actualMessage = usernameErrorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Username error message did not match");
    }

    @Then("I should see a password error message {string}")
    public void i_should_see_a_password_error_message(String expectedMessage) {
        WebElement passwordErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='Password-error-message']")));
        String actualMessage = passwordErrorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Password error message did not match");
    }
}
