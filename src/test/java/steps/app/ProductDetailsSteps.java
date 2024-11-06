package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductDetailsSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Given("I am on the main product screen")
    public void i_am_on_the_main_product_screen() {
        boolean isOnMainScreen = driver.findElement(By.id("com.saucelabs.mydemoapp.rn:id/action_bar_root")).isDisplayed();
        Assert.assertTrue(isOnMainScreen, "User is not on the main product screen");
    }

    @When("I tap on {string}")
    public void i_tap_on_product(String productName) {
        WebElement product = null;
        try {
            product = driver.findElement(By.xpath("//android.widget.TextView[@text='" + productName + "']"));
        } catch (Exception e) {
            String uiSelector = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                + "new UiSelector().text(\"" + productName + "\"))";
            product = driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
        }
        product.click();
    }

    @Then("the product detail screen for {string} should be displayed")
    public void the_product_detail_screen_should_be_displayed(String productName) {
        WebElement productDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@text='" + productName + "']")));
        Assert.assertTrue(productDetail.isDisplayed(), "Product detail screen is not displayed");
    }

    @Then("the product name {string} should be displayed at the top")
    public void the_product_name_should_be_displayed(String productName) {
        WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@text='" + productName + "']")));
        Assert.assertTrue(productNameElement.isDisplayed(), "Product name is not displayed");
    }

    @Then("the product price {string} should be displayed under the product name")
    public void the_product_price_should_be_displayed(String price) {
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//android.widget.TextView[@text='" + price + "']")));
        Assert.assertTrue(priceElement.isDisplayed(), "Product price is not displayed");
    }

    @Then("five review stars should be visible below the product price")
    public void five_review_stars_should_be_visible() {
        List<WebElement> stars = driver.findElements(
            By.xpath("//android.view.ViewGroup[contains(@content-desc, 'review star')]"));
        Assert.assertEquals(stars.size(), 5, "Five review stars are not visible");
    }

    @When("I tap on the {string} color option")
    public void i_tap_on_the_color_option(String colorOption) {
        WebElement color = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//android.view.ViewGroup[@content-desc='" + colorOption + "']")));
        color.click();
    }

    @Then("the {string} should be selected")
    public void the_should_be_selected(String colorOption) {
        // Note: You might need to adjust this based on how your app indicates selection
        // This assumes selected colors have a different content-desc or some attribute
        WebElement selectedColor = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='" + colorOption + "']")));
        // You might need to verify selection through a specific attribute or state
        Assert.assertTrue(selectedColor.isDisplayed(), "Color " + colorOption + " is not selected");
    }

    @When("I tap the {string} button {int} times")
    public void i_tap_the_button_times(String button, Integer times) {
        WebElement quantityButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//android.view.ViewGroup[@content-desc='counter " + button + " button']")));
        
        for (int i = 0; i < times; i++) {
            quantityButton.click();
            // Add a small delay between clicks if needed
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Then("the quantity should be {int}")
    public void the_quantity_should_be(Integer expectedQuantity) {
        WebElement quantityElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.view.ViewGroup[@content-desc='counter amount']//android.widget.TextView")));
        
        String actualQuantity = quantityElement.getText();
        Assert.assertEquals(actualQuantity, String.valueOf(expectedQuantity), 
            "Quantity does not match expected value");
    }

    @Then("the product description should contain {string}")
    public void the_product_description_should_contain(String expectedText) {
        WebElement description = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[@content-desc='product description']")));
        
        String actualDescription = description.getText();
        Assert.assertTrue(actualDescription.contains(expectedText), 
            "Product description does not contain expected text: " + expectedText);
    }

    @Then("the product highlights section should be visible")
    public void the_product_highlights_section_should_be_visible() {
        WebElement highlightsSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Product Highlights')]")));
        
        Assert.assertTrue(highlightsSection.isDisplayed(), 
            "Product highlights section is not visible");
    }
}
