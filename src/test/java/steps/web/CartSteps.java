package steps.web;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class CartSteps {

    @When("I add a product to the cart")
    public void i_add_a_product_to_the_cart() {
        DriverManager.getDriver().findElement(By.cssSelector(".inventory_item button")).click();
    }

    @Then("the product should be in the cart")
    public void the_product_should_be_in_the_cart() {
        DriverManager.getDriver().findElement(By.className("shopping_cart_link")).click();
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("cart_item")).isDisplayed(), "Product was not added to the cart.");
    }

    @When("I remove the product from the cart")
    public void i_remove_the_product_from_the_cart() {
        DriverManager.getDriver().findElement(By.className("shopping_cart_link")).click();
        WebElement removeButton = DriverManager.getDriver().findElement(By.cssSelector(".cart_button"));
        DriverManager.getWait().until(ExpectedConditions.visibilityOf(removeButton));
        removeButton.click();
    }

    @When("I add multiple products to the cart")
    public void i_add_multiple_products_to_the_cart() {
        // Locate multiple product elements on the page (for example, by class name or unique identifier)
        List<WebElement> products = DriverManager.getDriver().findElements(By.className("inventory_item"));

        // Loop through a subset or specific number of products to add them to the cart
        for (int i = 0; i < 3; i++) { // Adding first 3 products as an example
            products.get(i).findElement(By.className("btn_inventory")).click();
        }
    }

    @Then("all selected products should be in the cart")
    public void all_selected_products_should_be_in_the_cart() {
        // Navigate to the cart
        DriverManager.getDriver().findElement(By.id("shopping_cart_container")).click();

        // Verify the presence of each added product
        List<WebElement> cartItems = DriverManager.getDriver().findElements(By.className("cart_item"));
        Assert.assertEquals(3, cartItems.size(), "All selected products should be in the cart");
    }


    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assert.assertTrue(DriverManager.getDriver().findElements(By.className("cart_item")).isEmpty(), "Cart is not empty.");
    }
}
