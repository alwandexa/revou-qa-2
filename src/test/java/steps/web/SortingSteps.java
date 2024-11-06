package steps.web;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortingSteps {

    @When("I select the {string} sorting option")
    public void i_select_the_sorting_option(String sortingOption) {
        WebElement sortDropdown = DriverManager.getDriver().findElement(By.className("product_sort_container"));
        sortDropdown.click();
        sortDropdown.findElement(By.xpath("//option[. = '" + sortingOption + "']")).click();
    }

    @Then("the products should be displayed in ascending order of price")
    public void the_products_should_be_displayed_in_ascending_order_of_price() {
        List<WebElement> prices = DriverManager.getDriver().findElements(By.className("inventory_item_price"));
        List<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        Assert.assertTrue(isSortedAscending(priceList), "Products are not sorted in ascending order of price.");
    }

    @Then("the products should be displayed in descending order of price")
    public void the_products_should_be_displayed_in_descending_order_of_price() {
        List<WebElement> prices = DriverManager.getDriver().findElements(By.className("inventory_item_price"));
        List<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        Assert.assertTrue(isSortedDescending(priceList), "Products are not sorted in descending order of price.");
    }

    @Then("the products should be displayed in alphabetical order")
    public void the_products_should_be_displayed_in_alphabetical_order() {
        List<WebElement> productNames = DriverManager.getDriver().findElements(By.className("inventory_item_name"));
        List<String> nameList = new ArrayList<>();
        for (WebElement name : productNames) {
            nameList.add(name.getText());
        }
        Assert.assertTrue(isSortedAscending(nameList), "Products are not sorted in alphabetical order.");
    }

    @Then("the products should be displayed in reverse alphabetical order")
    public void the_products_should_be_displayed_in_reverse_alphabetical_order() {
        List<WebElement> productNames = DriverManager.getDriver().findElements(By.className("inventory_item_name"));
        List<String> nameList = new ArrayList<>();
        for (WebElement name : productNames) {
            nameList.add(name.getText());
        }
        Assert.assertTrue(isSortedDescending(nameList), "Products are not sorted in reverse alphabetical order.");
    }

    @Then("the products should be sorted by {string} in {string} order")
    public void verify_products_sorting(String sortType, String sortOrder) {
        List<WebElement> elements;
        List<String> actualList = new ArrayList<>();
        
        if (sortType.equals("name")) {
            elements = DriverManager.getDriver().findElements(By.className("inventory_item_name"));
            for (WebElement element : elements) {
                actualList.add(element.getText());
            }
        } else {
            elements = DriverManager.getDriver().findElements(By.className("inventory_item_price"));
            for (WebElement element : elements) {
                actualList.add(element.getText().replace("$", ""));
            }
            // Convert strings to doubles for price comparison
            List<Double> priceList = actualList.stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
            
            if (sortOrder.equals("ascending")) {
                Assert.assertTrue(isSortedAscendingNumeric(priceList));
            } else {
                System.out.println("Actual list price: " + priceList);
                Assert.assertTrue(isSortedDescendingNumeric(priceList));
            }
            return;
        }
        
        // String comparison for names
        if (sortOrder.equals("ascending")) {
            Assert.assertTrue(isSortedAscending(actualList));
        } else {
            Assert.assertTrue(isSortedDescending(actualList));
        }
    }

    @Then("the first product should be {string}")
    public void verify_first_product(String expectedProduct) {
        String firstProduct = DriverManager.getDriver()
            .findElement(By.className("inventory_item_name"))
            .getText();
        Assert.assertEquals(expectedProduct, firstProduct);
    }

    @Then("the price should be {string}")
    public void verify_price(String expectedPrice) {
        String actualPrice = DriverManager.getDriver()
            .findElement(By.className("inventory_item_price"))
            .getText();
        Assert.assertEquals(expectedPrice, actualPrice);
    }

    private <T extends Comparable<T>> boolean isSortedAscending(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private <T extends Comparable<T>> boolean isSortedDescending(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedAscendingNumeric(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedDescendingNumeric(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
