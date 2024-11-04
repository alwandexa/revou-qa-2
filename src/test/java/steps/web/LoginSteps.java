package steps.web;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LoginSteps {

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_sauce_demo_login_page() {
        DriverManager.initializeDriver();
        DriverManager.getDriver().get("https://www.saucedemo.com/");
    }

    @When("I enter a valid username and password")
    public void i_enter_a_valid_username_and_password() {
        DriverManager.getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        DriverManager.getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        DriverManager.getDriver().findElement(By.id("login-button")).click();
    }

    @Then("I should see the products page")
    public void i_should_see_the_products_page() {
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("inventory_list")).isDisplayed(), "Products page is not displayed.");
    }

    @Given("I am logged into SauceDemo")
    public void i_am_logged_into_sauce_demo() {
        i_am_on_the_sauce_demo_login_page();
        i_enter_a_valid_username_and_password();
        i_click_the_login_button();
    }

    @When("I logout from the application")
    public void i_logout_from_the_application() {
        DriverManager.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        DriverManager.getDriver().findElement(By.id("logout_sidebar_link")).click();
    }

    @Then("I should see the login page")
    public void i_should_see_the_login_page() {
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assert.assertTrue(DriverManager.getDriver().findElement(By.id("login-button")).isDisplayed(), "Login page is not displayed.");
    }
}
