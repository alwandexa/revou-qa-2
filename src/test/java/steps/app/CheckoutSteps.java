package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

public class CheckoutSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @When("I enter valid shipping information")
    public void i_enter_valid_shipping_information(DataTable dataTable) {
        Map<String, String> shippingInfo = dataTable.asMaps().get(0);
        
        WebElement fullNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.EditText[@content-desc='Full Name* input field']")));
        fullNameInput.sendKeys(shippingInfo.get("fullName"));

        WebElement addressInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Address Line 1* input field']"));
        addressInput.sendKeys(shippingInfo.get("address"));

        WebElement cityInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='City* input field']"));
        cityInput.sendKeys(shippingInfo.get("city"));

        WebElement stateInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='State/Region input field']"));
        stateInput.sendKeys(shippingInfo.get("state"));

        WebElement zipInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Zip Code* input field']"));
        zipInput.sendKeys(shippingInfo.get("zipCode"));

        WebElement countryInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Country* input field']"));
        countryInput.sendKeys(shippingInfo.get("country"));
    }

    @When("I enter valid payment information")
    public void i_enter_valid_payment_information(DataTable dataTable) {
        Map<String, String> paymentInfo = dataTable.asMaps().get(0);
        
        // Implementation would depend on payment form fields in the app
        // Using placeholder selectors based on common patterns
        WebElement cardHolderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.EditText[@content-desc='Full Name* input field']")));
        cardHolderInput.sendKeys(paymentInfo.get("cardHolder"));

        WebElement cardNumberInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Card Number* input field']"));
        cardNumberInput.sendKeys(paymentInfo.get("cardNumber"));

        WebElement expiryInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Expiration Date* input field']"));
        expiryInput.sendKeys(paymentInfo.get("expiryDate"));

        WebElement securityCodeInput = driver.findElement(
            By.xpath("//android.widget.EditText[@content-desc='Security Code* input field']"));
        securityCodeInput.sendKeys(paymentInfo.get("securityCode"));
    }

    @Then("I should see the {string} screen")
    public void i_should_see_the_screen(String screenName) {
        WebElement screen = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='" + screenName.toLowerCase() + " screen']")));
        Assert.assertTrue(screen.isDisplayed(), screenName + " screen is not displayed");
    }

    @Then("I should see the message {string}")
    public void i_should_see_the_message(String message) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@text='" + message + "']")));
        Assert.assertTrue(messageElement.isDisplayed(), "Message '" + message + "' is not displayed");
    }

    @Then("I should see the error message {string}")
    public void i_should_see_the_error_message(String errorMessage) {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@text='" + errorMessage + "']")));
        Assert.assertTrue(errorElement.isDisplayed(), "Error message '" + errorMessage + "' is not displayed");
    }

    @When("I proceed to checkout review")
    public void i_proceed_to_checkout_review() {
        WebElement toPaymentButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='To Payment button']")));
        toPaymentButton.click();
    }

    @Then("I should see the total number of items")
    public void i_should_see_the_total_number_of_items() {
        WebElement totalItems = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@content-desc='total number']")));
        Assert.assertTrue(totalItems.isDisplayed(), "Total number of items is not displayed");
    }

    @Then("I should see the total price including shipping")
    public void i_should_see_the_total_price_including_shipping() {
        WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@content-desc='total price']")));
        Assert.assertTrue(totalPrice.isDisplayed(), "Total price is not displayed");
    }

    @Given("I have completed a checkout")
    public void i_have_completed_a_checkout() {
        // Implementation would include steps to complete checkout process
        // This could call other step definitions or include direct implementation
    }

    @Then("my shopping cart should be empty")
    public void my_shopping_cart_should_be_empty() {
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='cart badge']")));
        Assert.assertFalse(cartBadge.findElements(By.xpath(".//android.widget.TextView")).size() > 0,
            "Cart should be empty");
    }

    @When("I am on the shipping information screen")
    public void i_am_on_the_shipping_information_screen() {
        WebElement proceedToCheckoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='Proceed To Checkout button']")));
        proceedToCheckoutButton.click();
    }

    @When("I tap on the back button")
    public void i_tap_on_the_back_button() {
        driver.navigate().back();
    }

    @Then("I should return to the shopping cart")
    public void i_should_return_to_the_shopping_cart() {
        WebElement cartScreen = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.ScrollView[@content-desc='cart screen']")));
        Assert.assertTrue(cartScreen.isDisplayed(), "Shopping cart screen is not displayed");
    }

    @Then("my cart items should remain unchanged")
    public void my_cart_items_should_remain_unchanged() {
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='cart badge']//android.widget.TextView")));
        Assert.assertEquals(cartBadge.getText(), "1", "Cart items count has changed");
    }

    @When("I tap on {string} button")
    public void i_tap_on_button(String buttonText) {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='" + buttonText + " button']")));
        button.click();
    }

    @Then("I should be returned to the main product screen")
    public void i_should_be_returned_to_the_main_product_screen() {
        WebElement storeItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='store item']")));
        Assert.assertTrue(storeItem.isDisplayed(), "Main product screen is not displayed");
    }

    @When("I leave the full name field empty")
    public void i_leave_the_full_name_field_empty() {
        WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.EditText[@content-desc='Full Name* input field']")));
        fullNameField.clear();
    }

    @When("I leave the address field empty")
    public void i_leave_the_address_field_empty() {
        WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.EditText[@content-desc='Address Line 1* input field']")));
        addressField.clear();
    }

    @When("I leave the zip code empty")
    public void i_leave_the_zip_code_empty() {
        WebElement zipCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.EditText[@content-desc='Zip Code* input field']")));
        zipCodeField.clear();
    }

    @When("I enter shipping information")
    public void i_enter_shipping_information() {
        List<List<String>> rows = Arrays.asList(
            Arrays.asList("fullName", "address", "city", "state", "zipCode", "country"),
            Arrays.asList("Rebecca Winter", "Mandorley 112", "Truro", "Cornwall", "89750", "United Kingdom")
        );
        
        DataTable dataTable = DataTable.create(rows);
        i_enter_valid_shipping_information(dataTable);
    }

    @Then("the shipping address should display correctly")
    public void the_shipping_address_should_display_correctly() {
        WebElement shippingAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@content-desc='shipping address']")));
        Assert.assertTrue(shippingAddress.isDisplayed(), "Shipping address is not displayed correctly");
    }

    @Then("the payment method should display correctly")
    public void the_payment_method_should_display_correctly() {
        WebElement paymentMethod = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@content-desc='payment method']")));
        Assert.assertTrue(paymentMethod.isDisplayed(), "Payment method is not displayed correctly");
    }

    @Then("I should see the order summary with correct items")
    public void i_should_see_the_order_summary_with_correct_items() {
        WebElement orderSummary = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='order summary']")));
        Assert.assertTrue(orderSummary.isDisplayed(), "Order summary is not displayed");
    }
} 