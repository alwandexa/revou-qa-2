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

public class ProductDetailsSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I am on the main product screen")
    public void i_am_on_the_main_product_screen() {
        boolean isOnMainScreen = driver.findElement(By.id("com.saucelabs.mydemoapp.rn:id/action_bar_root")).isDisplayed();
        Assert.assertTrue(isOnMainScreen, "User is not on the main product screen");
    }

    @When("I tap on {string}")
    public void i_tap_on_product(String productName) {
        WebElement product = driver.findElement(By.xpath("//android.widget.TextView[@text='" + productName + "']"));
        product.click();
    }

    @Then("the product detail screen for {string} should be displayed")
    public void the_product_detail_screen_should_be_displayed(String productName) {
        WebElement productDetail=  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + productName + "']")));
        Assert.assertTrue(productDetail.isDisplayed(), "Product detail screen is not displayed");
    }

    @Then("the product name {string} should be displayed at the top")
    public void the_product_name_should_be_displayed(String productName) {
        WebElement productNameElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + productName + "']"));
        Assert.assertTrue(productNameElement.isDisplayed(), "Product name is not displayed");
    }

    @Then("the product price {string} should be displayed under the product name")
    public void the_product_price_should_be_displayed(String price) {
        WebElement priceElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + price + "']"));
        Assert.assertTrue(priceElement.isDisplayed(), "Product price is not displayed");
    }

    @Then("five review stars should be visible below the product price")
    public void five_review_stars_should_be_visible() {
        List<WebElement> stars = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'review star')]"));
        Assert.assertEquals(stars.size(), 5, "Five review stars are not visible");
    }
}
