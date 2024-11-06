package steps.web;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
        Assert.assertEquals(cartItems.size(), 3, "All selected products should be in the cart");
    }


    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assert.assertTrue(DriverManager.getDriver().findElements(By.className("cart_item")).isEmpty(), "Cart is not empty.");
    }

    @Then("the cart badge should show {string}")
    public void verify_cart_badge(String expectedCount) {
        WebElement cartBadge = DriverManager.getDriver().findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), expectedCount);
    }

    @Then("the cart badge should not be visible")
    public void verify_cart_badge_not_visible() {
        List<WebElement> cartBadge = DriverManager.getDriver().findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(cartBadge.isEmpty());
    }

    @When("I add {string} to the cart")
    public void add_specific_product_to_cart(String productName) {
        WebElement product = DriverManager.getDriver().findElement(
            By.xpath(String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']", productName))
        );
        product.findElement(By.className("btn_inventory")).click();
    }

    @Then("the product {string} should be in the cart")
    public void verify_specific_product_in_cart(String productName) {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Wait for cart items to be visible
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        
        // Find all cart items
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        
        // Check if the specific product exists in the cart
        boolean productFound = false;
        for (WebElement item : cartItems) {
            if (item.getText().equals(productName)) {
                productFound = true;
                break;
            }
        }
        
        Assert.assertTrue(productFound, 
            String.format("Product '%s' was not found in the cart", productName));
    }

    @When("I update the quantity to {string}")
    public void i_update_the_quantity_to(String quantity) {
        WebDriver driver = DriverManager.getDriver();
        WebElement quantityInput = driver.findElement(By.className("cart_quantity"));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
        quantityInput.sendKeys(Keys.ENTER);
    }

    @Then("the cart total should be updated accordingly")
    public void the_cart_total_should_be_updated_accordingly() {
        WebDriver driver = DriverManager.getDriver();
        WebElement cartItem = driver.findElement(By.className("cart_item"));
        
        // Get the price per item
        String priceText = cartItem.findElement(By.className("inventory_item_price"))
            .getText().replace("$", "");
        double price = Double.parseDouble(priceText);
        
        // Get the quantity
        String quantityText = cartItem.findElement(By.className("cart_quantity"))
            .getAttribute("value");
        int quantity = Integer.parseInt(quantityText);
        
        // Calculate expected total
        double expectedTotal = price * quantity;
        
        // Get actual total from the page
        String actualTotalText = driver.findElement(By.className("summary_subtotal_label"))
            .getText().replaceAll("[^0-9.]", "");
        double actualTotal = Double.parseDouble(actualTotalText);
        
        Assert.assertEquals(actualTotal, expectedTotal, 0.01, 
            "Cart total does not match expected value");
    }

    @Then("the price should match {string}")
    public void the_price_should_match(String expectedPrice) {
        WebDriver driver = DriverManager.getDriver();
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        
        // Check if any price matches the expected price
        boolean priceFound = false;
        for (WebElement priceElement : priceElements) {
            String actualPrice = priceElement.getText();
            if (actualPrice.equals(expectedPrice)) {
                priceFound = true;
                break;
            }
        }
        
        Assert.assertTrue(priceFound, 
            String.format("No product with price '%s' was found in the cart", expectedPrice));
    }

    @Then("the cart should retain the previously added items")
    public void the_cart_should_retain_the_previously_added_items() {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Wait for cart items to be visible
        DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        
        // Verify cart items are present
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty after logging back in");
        Assert.assertEquals(cartItems.size(), 1, "Cart should contain the previously added item");
    }
}
