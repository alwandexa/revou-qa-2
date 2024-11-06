package steps.web;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class CheckoutSteps {

    @Given("I have added a product to the cart")
    public void i_have_added_a_product_to_the_cart() {
        WebDriver driver = DriverManager.getDriver();
        
        // Reset cart first by removing any existing items
        if (driver.findElements(By.className("shopping_cart_badge")).size() > 0) {
            driver.findElement(By.className("shopping_cart_link")).click();
            List<WebElement> removeButtons = driver.findElements(By.cssSelector(".cart_item .cart_button"));
            for (WebElement removeButton : removeButtons) {
                removeButton.click();
            }
            // Return to products page
            driver.findElement(By.id("continue-shopping")).click();
        }
        
        // Add a single product to cart
        WebElement firstProduct = driver.findElement(By.className("inventory_item"))
            .findElement(By.className("btn_inventory"));
        firstProduct.click();
        
        // Verify cart badge shows 1 item
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

    @When("I enter checkout information:")
    public void enter_checkout_information(DataTable dataTable) {
        Map<String, String> checkoutInfo = dataTable.asMaps().get(0);
        WebDriver driver = DriverManager.getDriver();
        
        String firstName = checkoutInfo.get("firstName");
        String lastName = checkoutInfo.get("lastName");
        String postalCode = checkoutInfo.get("postalCode");
        
        if (firstName != null && !firstName.isEmpty())
            driver.findElement(By.id("first-name")).sendKeys(firstName);
        if (lastName != null && !lastName.isEmpty())
            driver.findElement(By.id("last-name")).sendKeys(lastName);
        if (postalCode != null && !postalCode.isEmpty())
            driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        
        driver.findElement(By.id("continue")).click();
    }

    @When("I click the cancel button")
    public void click_cancel_button() {
        DriverManager.getDriver().findElement(By.id("cancel")).click();
    }

    @Then("I should be returned to the cart page")
    public void verify_return_to_cart() {
        WebElement cartList = DriverManager.getDriver().findElement(By.className("cart_list"));
        Assert.assertTrue(cartList.isDisplayed());
    }

    @Given("I have added multiple products to the cart")
    public void i_have_added_multiple_products_to_the_cart() {
        WebDriver driver = DriverManager.getDriver();
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        
        // Add first 3 products to cart
        for (int i = 0; i < 3; i++) {
            products.get(i).findElement(By.className("btn_inventory")).click();
        }
        
        // Verify cart badge shows correct number
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "3", "Cart should contain 3 items");
    }

    @Then("I should see the correct:")
    public void i_should_see_the_correct(DataTable dataTable) {
        WebDriver driver = DriverManager.getDriver();
        List<String> expectedFields = dataTable.asList();
        
        for (String field : expectedFields) {
            switch (field.trim()) {
                case "Item Total":
                    WebElement subtotal = driver.findElement(By.className("summary_subtotal_label"));
                    Assert.assertTrue(subtotal.isDisplayed(), "Item total is not displayed");
                    Assert.assertTrue(subtotal.getText().contains("$"), "Item total should contain price");
                    break;
                    
                case "Tax":
                    WebElement tax = driver.findElement(By.className("summary_tax_label"));
                    Assert.assertTrue(tax.isDisplayed(), "Tax amount is not displayed");
                    Assert.assertTrue(tax.getText().contains("$"), "Tax should contain price");
                    break;
                    
                case "Total Amount":
                    WebElement total = driver.findElement(By.className("summary_total_label"));
                    Assert.assertTrue(total.isDisplayed(), "Total amount is not displayed");
                    Assert.assertTrue(total.getText().contains("$"), "Total should contain price");
                    
                    // Verify total equals subtotal plus tax
                    double subtotalAmount = Double.parseDouble(driver.findElement(By.className("summary_subtotal_label"))
                        .getText().replaceAll("[^0-9.]", ""));
                    double taxAmount = Double.parseDouble(driver.findElement(By.className("summary_tax_label"))
                        .getText().replaceAll("[^0-9.]", ""));
                    double totalAmount = Double.parseDouble(total.getText().replaceAll("[^0-9.]", ""));
                    
                    Assert.assertEquals(totalAmount, subtotalAmount + taxAmount, 0.01, 
                        "Total amount should equal subtotal plus tax");
                    break;
            }
        }
    }
}
