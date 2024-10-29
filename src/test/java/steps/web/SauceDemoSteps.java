package steps.web;

import io.cucumber.java.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;

import java.time.Duration;

public class SauceDemoSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_sauce_demo_login_page() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the SauceDemo website
        driver.get("https://www.saucedemo.com/");
    }

    @When("I enter a valid username and password")
    public void i_enter_a_valid_username_and_password() {
        // Locate and enter username
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        // Locate and enter password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        // Click the login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    @Then("I should see the products page")
    public void i_should_see_the_products_page() {
        // Wait until the products page is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        // Assert that the products page is displayed
        Assert.assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed(),
                "Products page is not displayed.");
    }

    @Given("I am logged into SauceDemo")
    public void i_am_logged_into_sauce_demo() {
        // Reuse login steps to ensure user is logged in
        i_am_on_the_sauce_demo_login_page();
        i_enter_a_valid_username_and_password();
        i_click_the_login_button();
    }

    @When("I add a product to the cart")
    public void i_add_a_product_to_the_cart() {
        // Add the first product to the cart
        WebElement firstProductAddButton = driver.findElement(By.cssSelector(".inventory_item button"));
        firstProductAddButton.click();
    }

    @Then("the product should be in the cart")
    public void the_product_should_be_in_the_cart() {
        // Navigate to the cart
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        // Wait until the cart page is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        // Assert that the product is in the cart
        Assert.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed(),
                "Product was not added to the cart.");
    }

    @When("I logout from the application")
    public void i_logout_from_the_application() {
        // Open the menu
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        // Wait for the logout link to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));

        // Click the logout link
        WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
        logoutLink.click();
    }

    @Then("I should see the login page")
    public void i_should_see_the_login_page() {
        // Wait until the login page is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));

        // Assert that the login page is displayed
        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(),
                "Login page is not displayed.");

        // Close the browser
        driver.quit();
    }
}
