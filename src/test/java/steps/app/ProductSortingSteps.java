package steps.app;

import com.google.common.collect.Ordering;
import io.appium.java_client.android.AndroidDriver;
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
import java.util.stream.Collectors;


public class ProductSortingSteps {

    AndroidDriver driver = Hooks.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @When("I tap on the {string}")
    public void i_tap_on_the_sort_button(String button) {
        WebElement sortButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='" + button + "']"));
        sortButton.click();
    }

    @When("I select {string} from the sorting options")
    public void i_select_sorting_option(String option) {
//        WebElement sortingOption = driver.findElement(By.xpath("//android.widget.TextView[@text='" + option + "']"));
        WebElement sortingOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + option + "']")));
        sortingOption.click();
    }

    @Then("the product list should be sorted by price in ascending order")
    public void the_product_list_should_be_sorted_by_price_in_ascending_order() {
        List<WebElement> prices = driver.findElements(By.id("store item price"));
        List<Double> priceValues = prices.stream()
                .map(price -> Double.parseDouble(price.getText().replace("$", "")))
                .collect(Collectors.toList());
        Assert.assertTrue(Ordering.natural().isOrdered(priceValues), "Prices are not sorted in ascending order");
    }

    @Then("the product list should be sorted by price in descending order")
    public void the_product_list_should_be_sorted_by_price_in_descending_order() {
        List<WebElement> prices = driver.findElements(By.id("store item price"));
        List<Double> priceValues = prices.stream()
                .map(price -> Double.parseDouble(price.getText().replace("$", "")))
                .collect(Collectors.toList());
        Assert.assertTrue(Ordering.natural().reverse().isOrdered(priceValues), "Prices are not sorted in descending order");
    }
}
