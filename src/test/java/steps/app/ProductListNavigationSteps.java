package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductListNavigationSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @When("I scroll down the product list")
    public void i_scroll_down_the_product_list() {
        WebElement listElement = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='store item']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listElement);
    }

    @Then("a list of products should be displayed")
    public void a_list_of_products_should_be_displayed() {
        List<WebElement> productList = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc='store item']"));
        Assert.assertTrue(productList.size() > 0, "Product list is not displayed");
    }

    @Then("each product should show a name, price, and review stars")
    public void each_product_should_show_name_price_and_review_stars() {
        List<WebElement> products = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc='store item']"));
        for (WebElement product : products) {
            Assert.assertTrue(product.findElement(By.id("store item text")).isDisplayed(), "Product name is not displayed");
            Assert.assertTrue(product.findElement(By.id("store item price")).isDisplayed(), "Product price is not displayed");
            Assert.assertTrue(product.findElements(By.xpath(".//android.view.ViewGroup[contains(@content-desc, 'review star')]")).size() > 0, "Review stars are not displayed");
        }
    }
}