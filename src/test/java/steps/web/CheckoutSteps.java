package steps.web;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class CheckoutSteps {

    @Given("I have added a product to the cart")
    public void i_have_added_a_product_to_the_cart() {
        WebDriver driver = DriverManager.getDriver();
        WebElement firstProduct = driver.findElement(By.className("inventory_item")).findElement(By.className("btn_inventory"));
        firstProduct.click();
        String cartBadgeText = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals("1", cartBadgeText, "A single product should be added to the cart");
    }

    @Given("I am on the checkout information page")
    public void i_am_on_the_checkout_information_page() {
        WebDriver driver = DriverManager.getDriver();
        // Navigate to cart page
        driver.findElement(By.id("shopping_cart_container")).click();

        // Proceed to checkout if products are in the cart
        driver.findElement(By.id("checkout")).click();

        // Verify we are on the checkout information page
        WebElement checkoutInfoHeader = driver.findElement(By.className("checkout_info"));
        Assert.assertTrue(checkoutInfoHeader.isDisplayed(), "The checkout information page should be displayed");
    }


    @When("I navigate to the cart page without adding any items")
    public void i_navigate_to_the_cart_page_without_adding_any_items() {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.id("shopping_cart_container")).click();
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertTrue(cartItems.isEmpty(), "Cart should be empty");
    }

    @When("I attempt to proceed to checkout")
    public void i_attempt_to_proceed_to_checkout() {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.id("checkout")).click();
        WebElement errorMessage = driver.findElement(By.className("error_message"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message should appear when trying to checkout with an empty cart");
    }


    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        DriverManager.getDriver().findElement(By.className("shopping_cart_link")).click();
        DriverManager.getDriver().findElement(By.id("checkout")).click();
    }

    @Then("I should see the checkout information page")
    public void i_should_see_the_checkout_information_page() {
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_info_container")));
        Assert.assertTrue(DriverManager.getDriver().findElement(By.id("checkout_info_container")).isDisplayed(), "Checkout information page is not displayed.");
    }

    @When("I fill out my personal information and continue")
    public void i_fill_out_my_personal_information_and_continue() {
        DriverManager.getDriver().findElement(By.id("first-name")).sendKeys("John");
        DriverManager.getDriver().findElement(By.id("last-name")).sendKeys("Doe");
        DriverManager.getDriver().findElement(By.id("postal-code")).sendKeys("12345");
        DriverManager.getDriver().findElement(By.id("continue")).click();
    }

    @When("I complete the checkout process")
    public void i_complete_the_checkout_process() {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.id("finish")).click();
    }

    @Then("I should see the order confirmation page with a success message")
    public void i_should_see_the_order_confirmation_page_with_a_success_message() {
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("complete-header")).getText().contains("Thank you for your order!"), "Order confirmation page is not displayed.");
    }

    @Then("I should see an error message or be unable to proceed with an empty cart")
    public void i_should_see_an_error_message_or_be_unable_to_proceed_with_an_empty_cart() {
        DriverManager.getDriver().findElement(By.className("shopping_cart_link")).click();
        DriverManager.getDriver().findElement(By.id("checkout")).click();
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("cart_item")).isDisplayed(), "No error message or restriction when cart is empty.");
    }
}
