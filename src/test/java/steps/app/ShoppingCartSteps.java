package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ShoppingCartSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I have added items to my shopping cart")
    public void i_have_added_items_to_my_shopping_cart() {
        WebElement product = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='store item']"));
        product.click();
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]")));
        addToCartButton.click();

        driver.navigate().back();
    }

    @Given("I am on the product detail screen for {string}")
    public void i_am_on_the_product_detail_screen_for(String productName) {
        // Scroll to the product and click to open its detail screen
        WebElement product = driver.findElement(By.xpath("//android.widget.TextView[@text='" + productName + "']"));
        product.click();
    }

    @When("I tap on the {string} button")
    public void i_tap_on_the_button(String buttonText) {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='" + buttonText + " button']")));
        button.click();
    }

    @Then("the cart badge should display the updated item count")
    public void the_cart_badge_should_display_the_updated_item_count() {
        // Locate the cart badge container first
        WebElement cartBadgeContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='cart badge']")));

        // Then, locate the TextView inside the cart badge container for the item count
        WebElement itemCountElement = cartBadgeContainer.findElement(By.xpath(".//android.widget.TextView"));
        String itemCount = itemCountElement.getText();

        // Ensure itemCount is not empty and is greater than zero
        Assert.assertFalse(itemCount.isEmpty(), "Cart badge item count should not be empty.");
        Assert.assertTrue(Integer.parseInt(itemCount) > 0, "Cart badge should show an updated item count.");
    }


    @When("I tap on the cart badge")
    public void i_tap_on_the_cart_badge() {
        WebElement cartBadge = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='cart badge']"));
        cartBadge.click();
    }

    @Then("the shopping cart screen should be displayed")
    public void the_shopping_cart_screen_should_be_displayed() {
        WebElement cartScreen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ScrollView[@content-desc=\"cart screen\"]")));
        Assert.assertTrue(cartScreen.isDisplayed(), "Shopping cart screen is not displayed");
    }

    @Then("each item in the cart should show a name, quantity, and price")
    public void each_item_in_the_cart_should_show_name_quantity_and_price() {
        List<WebElement> cartItems = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc='cart item']"));
        for (WebElement item : cartItems) {
            Assert.assertTrue(item.findElement(By.id("cart item name")).isDisplayed(), "Cart item name is missing");
            Assert.assertTrue(item.findElement(By.id("cart item quantity")).isDisplayed(), "Cart item quantity is missing");
            Assert.assertTrue(item.findElement(By.id("cart item price")).isDisplayed(), "Cart item price is missing");
        }
    }
}
